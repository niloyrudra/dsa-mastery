package com.dsamastery.ai.dto;

import com.dsamastery.common.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIMessageRequest {

    private String content;
    private MessageType type;
}