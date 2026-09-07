package com.dsamastery.api.dto;

import com.dsamastery.common.enums.MessageType;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIMessageRequest {
    private String content;
    private MessageType type;
}