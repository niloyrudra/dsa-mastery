package com.dsamastery.ai.controller;

import com.dsamastery.ai.dto.AIConversationRequest;
import com.dsamastery.ai.dto.AIConversationResponse;
import com.dsamastery.ai.dto.AIMessageDto;
import com.dsamastery.ai.dto.AIMessageRequest;
import com.dsamastery.ai.entity.AIConversation;
import com.dsamastery.ai.entity.AIMessage;
import com.dsamastery.ai.service.TutorService;
import com.dsamastery.common.enums.MessageType;
import com.dsamastery.common.enums.ModelRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final TutorService tutorService;

    @PostMapping("/conversations")
    public ResponseEntity<AIConversationResponse> startConversation(@RequestBody AIConversationRequest request) {
        ModelRole modelRole = request.getModelRole() != null ? request.getModelRole() : ModelRole.CODING;
        AIConversation conversation = tutorService.startConversation(request.getProblemId(), modelRole);
        return ResponseEntity.ok(toResponse(conversation));
    }

    @GetMapping("/conversations/{id}")
    public ResponseEntity<AIConversationResponse> getConversation(@PathVariable UUID id) {
        return tutorService.getConversation(id)
            .map(c -> ResponseEntity.ok(toResponse(c)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/conversations")
    public ResponseEntity<List<AIConversationResponse>> getConversationsForProblem(
            @RequestParam UUID problemId) {
        List<AIConversationResponse> conversations = tutorService.getConversationsForProblem(problemId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(conversations);
    }

    @PostMapping("/conversations/{id}/messages")
    public ResponseEntity<AIMessageDto> sendMessage(
            @PathVariable UUID id,
            @RequestBody AIMessageRequest request) {
        
        MessageType type = request.getType() != null ? request.getType() : MessageType.EXPLANATION;
        AIMessage message = tutorService.sendMessage(id, request.getContent(), type);
        return ResponseEntity.ok(toDto(message));
    }

    @GetMapping(value = "/conversations/{id}/messages/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamMessages(@PathVariable UUID id) {
        // For now, return the existing messages as a stream
        // Full SSE implementation would require WebFlux integration with the tutor service
        List<AIMessage> messages = tutorService.getMessages(id);
        return Flux.fromIterable(messages)
            .map(this::toDto)
            .map(dto -> "data: " + dto.toString() + "\n\n");
    }

    @GetMapping("/conversations/{id}/messages")
    public ResponseEntity<List<AIMessageDto>> getMessages(@PathVariable UUID id) {
        List<AIMessageDto> messages = tutorService.getMessages(id)
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    private AIConversationResponse toResponse(AIConversation conversation) {
        return AIConversationResponse.builder()
            .conversationId(conversation.getId())
            .problemId(conversation.getProblemId())
            .modelUsed(conversation.getModelUsed())
            .modelRole(conversation.getModelRole())
            .startedAt(conversation.getStartedAt())
            .lastMessageAt(conversation.getLastMessageAt())
            .build();
    }

    private AIMessageDto toDto(AIMessage message) {
        return AIMessageDto.builder()
            .id(message.getId())
            .conversationId(message.getConversation().getId())
            .role(message.getRole())
            .content(message.getContent())
            .type(message.getType())
            .sequence(message.getSequence())
            .createdAt(message.getCreatedAt())
            .build();
    }
}