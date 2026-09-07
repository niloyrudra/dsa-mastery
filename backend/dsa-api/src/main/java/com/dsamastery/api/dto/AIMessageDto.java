package com.dsamastery.api.dto;

import com.dsamastery.common.enums.MessageRole;
import com.dsamastery.common.enums.MessageType;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIMessageDto {
    private UUID id;
    private MessageRole role;
    private String content;
    private MessageType type;
    private Integer sequence;
    private String createdAt;
}