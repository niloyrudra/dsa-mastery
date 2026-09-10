package com.dsamastery.ai.provider;

import com.dsamastery.common.enums.ModelRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

/**
 * Ollama-backed provider. Talks to the local Ollama HTTP API
 * (POST /api/generate with {"model": ..., "prompt": ..., "stream": false}).
 * Falls back gracefully: complete() throws {@link ProviderUnavailableException}
 * when Ollama is not reachable so the TutorService can use static fallbacks.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class OllamaProvider implements AIProvider {

    private final WebClient webClient;

    @Value("${ai.providers.ollama.base-url:http://localhost:11434}")
    private String baseUrl;

    @Value("${ai.providers.ollama.models.fast:llama3.1:8b}")
    private String fastModel;

    @Value("${ai.providers.ollama.models.coding:codellama:13b}")
    private String codingModel;

    @Value("${ai.providers.ollama.models.reasoning:deepseek-coder:33b}")
    private String reasoningModel;

    @Override
    public String getName() {
        return "ollama";
    }

    @Override
    public boolean isHealthy() {
        try {
            String body = webClient.get()
                .uri(baseUrl + "/api/tags")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(3))
                .block();
            return body != null;
        } catch (Exception e) {
            log.warn("Ollama health check failed: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String complete(ModelRole role, String model, String systemPrompt, String userPrompt) {
        String resolvedModel = (model == null || model.isBlank()) ? resolveModel(role) : model;
        String prompt = appendSystem(systemPrompt, userPrompt);

        try {
            Mono<String> responseMono = webClient.post()
                .uri(baseUrl + "/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of(
                    "model", resolvedModel,
                    "prompt", prompt,
                    "stream", false,
                    "options", Map.of("temperature", 0.4, "num_predict", 1024)
                ))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(30));

            String raw = responseMono.block();
            return extractResponse(raw);
        } catch (Exception e) {
            log.warn("Ollama completion failed (model={}): {}", resolvedModel, e.getMessage());
            throw new ProviderUnavailableException("Ollama unavailable: " + e.getMessage(), e);
        }
    }

    private String extractResponse(String raw) {
        if (raw == null) return "";
        // {"model":"...","response":"...","done":true,...}
        int idx = raw.indexOf("\"response\"");
        if (idx < 0) return raw.trim();
        int start = raw.indexOf(':', idx) + 1;
        if (start <= 0) return raw.trim();
        // Skip whitespace + quote
        while (start < raw.length() && (raw.charAt(start) == ' ' || raw.charAt(start) == '"')) start++;
        int end = raw.indexOf('"', start);
        if (end < 0) end = raw.length();
        String text = raw.substring(start, end)
            .replace("\\n", "\n")
            .replace("\\\"", "\"")
            .replace("\\\\", "\\");
        return text.trim();
    }

    private String resolveModel(ModelRole role) {
        return switch (role) {
            case CODING -> codingModel;
            case REASONING -> reasoningModel;
            default -> fastModel;
        };
    }

    private String appendSystem(String systemPrompt, String userPrompt) {
        StringBuilder sb = new StringBuilder();
        if (systemPrompt != null && !systemPrompt.isBlank()) {
            sb.append("<|system|>\n").append(systemPrompt).append("\n\n");
        }
        sb.append("<|user|>\n").append(userPrompt).append('\n');
        sb.append("<|assistant|>\n");
        return sb.toString();
    }

    /** Thrown when the provider cannot be reached; triggers static fallback. */
    public static class ProviderUnavailableException extends RuntimeException {
        public ProviderUnavailableException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}