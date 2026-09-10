package com.dsamastery.ai.provider;

import com.dsamastery.common.enums.ModelRole;

/**
 * Abstraction over an LLM backend (Ollama, OpenAI-compatible, etc.).
 * Kept intentionally small & transport-agnostic: a complete() call that
 * returns generated text, plus health check.
 */
public interface AIProvider {

    String getName();

    boolean isHealthy();

    /**
     * Generate a completion.
     *
     * @param model        model identifier (e.g. "llama3.1:8b")
     * @param systemPrompt  system-level instructions
     * @param userPrompt    the user turn payload
     * @return generated text (never null)
     */
    String complete(ModelRole role, String model, String systemPrompt, String userPrompt);
}