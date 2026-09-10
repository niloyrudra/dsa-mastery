package com.dsamastery.ai.dto;

import com.dsamastery.common.enums.MessageRole;
import com.dsamastery.common.enums.MessageType;
import com.dsamastery.common.enums.ModelRole;
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
public class AIConversationResponse {

    private UUID conversationId;
    private UUID problemId;
    private String modelUsed;
    private ModelRole modelRole;
    private Instant startedAt;
    private Instant lastMessageAt;
}