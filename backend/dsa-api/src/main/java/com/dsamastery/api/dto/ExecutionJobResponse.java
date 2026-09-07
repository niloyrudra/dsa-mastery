package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ExecutionStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionJobResponse {
    private UUID jobId;
    private ExecutionStatus status;
    private String submittedAt;
}