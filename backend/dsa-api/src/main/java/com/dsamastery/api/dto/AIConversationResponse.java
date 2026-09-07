package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ModelRole;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIConversationResponse {
    private UUID conversationId;
    private String modelUsed;
    private ModelRole modelRole;
}