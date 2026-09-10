package com.dsamastery.ai.dto;

import com.dsamastery.common.enums.ModelRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIConversationRequest {

    private UUID problemId;
    private ModelRole modelRole;
}