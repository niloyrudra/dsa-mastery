package com.dsamastery.ai.service;

import com.dsamastery.ai.entity.AIConversation;
import com.dsamastery.ai.entity.AIMessage;
import com.dsamastery.ai.repository.AIConversationRepository;
import com.dsamastery.ai.repository.AIMessageRepository;
import com.dsamastery.common.enums.MessageRole;
import com.dsamastery.common.enums.MessageType;
import com.dsamastery.common.enums.ModelRole;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorService {

    private final AIConversationRepository conversationRepository;
    private final AIMessageRepository messageRepository;
    private final ProblemService problemService;
    private final ModelRouter modelRouter;
    private final ProblemContextBuilder contextBuilder;
    private final StaticHintFallback staticHintFallback;

    @Transactional
    public AIConversation startConversation(UUID problemId, ModelRole modelRole) {
        Problem problem = problemService.getProblemById(problemId)
            .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + problemId));

        AIConversation conversation = AIConversation.builder()
            .problemId(problemId)
            .modelUsed(resolveModel(modelRole))
            .modelRole(modelRole)
            .startedAt(Instant.now())
            .lastMessageAt(Instant.now())
            .build();

        conversation = conversationRepository.save(conversation);

        // Add system message with context
        String context = contextBuilder.buildContext(problemId).orElse("");
        String systemPrompt = buildSystemPrompt(problem, context);

        AIMessage systemMessage = AIMessage.builder()
            .conversation(conversation)
            .role(MessageRole.SYSTEM)
            .content(systemPrompt)
            .type(MessageType.EXPLANATION)
            .sequence(0)
            .createdAt(Instant.now())
            .build();

        messageRepository.save(systemMessage);

        log.info("Started AI conversation {} for problem {}", conversation.getId(), problemId);
        return conversation;
    }

    @Transactional
    public AIMessage sendMessage(UUID conversationId, String userMessage, MessageType type) {
        AIConversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + conversationId));

        int nextSequence = messageRepository.findMaxSequenceByConversationId(conversationId)
            .map(s -> s + 1)
            .orElse(1);

        // Save user message
        AIMessage userMsg = AIMessage.builder()
            .conversation(conversation)
            .role(MessageRole.USER)
            .content(userMessage)
            .type(type)
            .sequence(nextSequence++)
            .createdAt(Instant.now())
            .build();
        messageRepository.save(userMsg);

        // Get problem context
        Problem problem = problemService.getProblemById(conversation.getProblemId()).orElse(null);
        String context = problem != null ? contextBuilder.buildContext(conversation.getProblemId()).orElse("") : "";

        // Try AI provider, fall back to static
        String assistantResponse;
        try {
            if (modelRouter.isHealthy(conversation.getModelRole())) {
                assistantResponse = modelRouter.complete(
                    conversation.getModelRole(),
                    conversation.getModelUsed(),
                    buildSystemPrompt(problem, context),
                    userMessage
                );
            } else {
                throw new RuntimeException("Provider unhealthy");
            }
        } catch (Exception e) {
            log.warn("AI provider unavailable, using static fallback: {}", e.getMessage());
            int attemptCount = (int) messageRepository.countByConversationIdAndRole(conversationId, MessageRole.USER);
            assistantResponse = staticHintFallback.generate(problem, userMessage, type, attemptCount);
        }

        // Save assistant message
        AIMessage assistantMsg = AIMessage.builder()
            .conversation(conversation)
            .role(MessageRole.ASSISTANT)
            .content(assistantResponse)
            .type(type)
            .sequence(nextSequence)
            .createdAt(Instant.now())
            .build();
        messageRepository.save(assistantMsg);

        // Update conversation timestamp
        conversation.setLastMessageAt(Instant.now());
        conversationRepository.save(conversation);

        return assistantMsg;
    }

    /**
     * Send a message and stream the response back as Flux of chunks.
     * Note: This method is not @Transactional because we need to stream before committing.
     * The message is saved after streaming completes.
     */
    public Flux<String> streamMessage(UUID conversationId, String userMessage, MessageType type) {
        AIConversation conversation = conversationRepository.findById(conversationId)
            .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + conversationId));

        int nextSequence = messageRepository.findMaxSequenceByConversationId(conversationId)
            .map(s -> s + 1)
            .orElse(1);

        final int assistantSequence = nextSequence;

        // Save user message first
        AIMessage userMsg = AIMessage.builder()
            .conversation(conversation)
            .role(MessageRole.USER)
            .content(userMessage)
            .type(type)
            .sequence(nextSequence)
            .createdAt(Instant.now())
            .build();
        messageRepository.save(userMsg);

        // Get problem context
        Problem problem = problemService.getProblemById(conversation.getProblemId()).orElse(null);
        String context = problem != null ? contextBuilder.buildContext(conversation.getProblemId()).orElse("") : "";
        String systemPrompt = buildSystemPrompt(problem, context);

        // Try AI provider streaming, fall back to static
        if (!modelRouter.isHealthy(conversation.getModelRole())) {
            log.warn("AI provider unhealthy, using static fallback for streaming");
            int attemptCount = (int) messageRepository.countByConversationIdAndRole(conversationId, MessageRole.USER);
            String staticResponse = staticHintFallback.generate(problem, userMessage, type, attemptCount);
            
            // Save the static response
            saveAssistantMessage(conversation, assistantSequence, staticResponse, type, Instant.now());
            
            // Return as single chunk flux
            return Flux.just(staticResponse);
        }

        // Stream from provider and accumulate
        StringBuilder fullResponse = new StringBuilder();
        return modelRouter.streamComplete(
            conversation.getModelRole(),
            conversation.getModelUsed(),
            systemPrompt,
            userMessage
        )
            .doOnNext(chunk -> fullResponse.append(chunk))
            .doOnComplete(() -> {
                // Save the complete response after streaming finishes
                String completeResponse = fullResponse.toString();
                saveAssistantMessage(conversation, assistantSequence, completeResponse, type, Instant.now());
                
                // Update conversation timestamp
                conversation.setLastMessageAt(Instant.now());
                conversationRepository.save(conversation);
            })
            .onErrorResume(e -> {
                log.warn("AI streaming failed, using static fallback: {}", e.getMessage());
                int attemptCount = (int) messageRepository.countByConversationIdAndRole(conversationId, MessageRole.USER);
                String staticResponse = staticHintFallback.generate(problem, userMessage, type, attemptCount);
                
                saveAssistantMessage(conversation, assistantSequence, staticResponse, type, Instant.now());
                conversation.setLastMessageAt(Instant.now());
                conversationRepository.save(conversation);
                
                return Flux.just(staticResponse);
            });
    }

    private void saveAssistantMessage(AIConversation conversation, int sequence, String content, MessageType type, Instant createdAt) {
        AIMessage assistantMsg = AIMessage.builder()
            .conversation(conversation)
            .role(MessageRole.ASSISTANT)
            .content(content)
            .type(type)
            .sequence(sequence)
            .createdAt(createdAt)
            .build();
        messageRepository.save(assistantMsg);
    }

    @Transactional(readOnly = true)
    public Optional<AIConversation> getConversation(UUID conversationId) {
        return conversationRepository.findById(conversationId);
    }

    @Transactional(readOnly = true)
    public List<AIMessage> getMessages(UUID conversationId) {
        return messageRepository.findByConversationIdOrderBySequenceAsc(conversationId);
    }

    @Transactional(readOnly = true)
    public List<AIConversation> getConversationsForProblem(UUID problemId) {
        return conversationRepository.findByProblemIdOrderByLastMessageAtDesc(problemId);
    }

    private String resolveModel(ModelRole role) {
        return switch (role) {
            case CODING -> "codellama:13b";
            case REASONING -> "deepseek-coder:33b";
            default -> "llama3.1:8b";
        };
    }

    private String buildSystemPrompt(Problem problem, String context) {
        StringBuilder sb = new StringBuilder();
        sb.append("You are an expert DSA tutor. Guide the learner using Socratic questioning. ");
        sb.append("Never give away the solution directly. Instead, ask leading questions, ");
        sb.append("provide progressive hints, and help them discover the pattern.\n\n");

        if (problem != null) {
            sb.append("Current Problem: ").append(problem.getTitle()).append(" (").append(problem.getDifficulty()).append(")\n");
            sb.append(problem.getDescription()).append("\n\n");
        }

        if (context != null && !context.isBlank()) {
            sb.append("Relevant Context:\n").append(context).append("\n\n");
        }

        sb.append("Your role: ").append(getRoleDescription());
        return sb.toString();
    }

    private String getRoleDescription() {
        return "Help the learner recognize patterns, understand invariants, and develop problem-solving intuition. ";
    }
}