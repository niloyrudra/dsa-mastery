package com.dsamastery.ai.service;

import com.dsamastery.ai.provider.AIProvider;
import com.dsamastery.common.enums.ModelRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Routes a ModelRole to the appropriate AI provider. Currently only the
 * Ollama provider is wired, but the indirection matches docs/ai-architecture.md
 * and allows cloud providers to be added with a @Profile later.
 */
@Service
public class ModelRouter {

    private final ObjectProvider<AIProvider> providers;

    public ModelRouter(ObjectProvider<AIProvider> providers) {
        this.providers = providers;
    }

    public AIProvider getProvider(ModelRole role) {
        AIProvider provider = providers.getIfAvailable();
        if (provider == null) {
            throw new IllegalStateException("No AIProvider bean is available");
        }
        return provider;
    }

    public String complete(ModelRole role, String model, String systemPrompt, String userPrompt) {
        return getProvider(role).complete(role, model, systemPrompt, userPrompt);
    }

    public Flux<String> streamComplete(ModelRole role, String model, String systemPrompt, String userPrompt) {
        return getProvider(role).streamComplete(role, model, systemPrompt, userPrompt);
    }

    public boolean isHealthy(ModelRole role) {
        return getProvider(role).isHealthy();
    }
}