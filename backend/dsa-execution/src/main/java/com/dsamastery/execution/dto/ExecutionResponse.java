package com.dsamastery.execution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResponse {

    private UUID id;
    private UUID problemId;
    private String language;
    private String status;
    private Integer passedTests;
    private Integer totalTests;
    private Long executionTimeMs;
    private Long memoryBytes;
    private String output;
    private String error;
    private Instant attemptedAt;
    private Instant completedAt;
    private List<TestResult> testResults;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestResult {
        private String name;
        private Boolean passed;
        private Object input;
        private Object expected;
        private String error;
        private Long durationMs;
    }
}