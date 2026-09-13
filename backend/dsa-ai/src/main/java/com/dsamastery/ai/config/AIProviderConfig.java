package com.dsamastery.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Provides the WebClient instance used by AI providers (e.g. OllamaProvider).
 * Spring WebFlux auto-configures a WebClient.Builder, not a WebClient bean.
 */
@Configuration
public class AIProviderConfig {

    @Bean
    public WebClient aiWebClient(WebClient.Builder builder) {
        return builder.build();
    }
}