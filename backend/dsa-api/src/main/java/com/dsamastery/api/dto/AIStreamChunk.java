package com.dsamastery.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIStreamChunk {
    private String type;
    private String content;
    private UUID messageId;
}