package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ModelRole;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIConversationRequest {
    private UUID problemId;
    private ModelRole modelRole;
}