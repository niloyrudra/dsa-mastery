package com.dsamastery.ai.dto;

import com.dsamastery.common.enums.MessageRole;
import com.dsamastery.common.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIMessageDto {

    private UUID id;
    private UUID conversationId;
    private MessageRole role;
    private String content;
    private MessageType type;
    private Integer sequence;
    private Instant createdAt;
}