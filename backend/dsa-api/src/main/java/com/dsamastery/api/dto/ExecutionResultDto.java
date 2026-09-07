package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ExecutionStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResultDto {
    private UUID jobId;
    private ExecutionStatus status;
    private Integer passedTests;
    private Integer totalTests;
    private Long executionTimeMs;
    private Long memoryBytes;
    private String output;
    private String error;
    private List<TestResultDto> testResults;
    private String completedAt;
}