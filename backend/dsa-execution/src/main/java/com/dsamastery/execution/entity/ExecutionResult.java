package com.dsamastery.execution.entity;

import com.dsamastery.common.enums.ExecutionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResult {
    private ExecutionStatus status;
    private int passedTests;
    private int totalTests;
    private long executionTimeMs;
    private Long memoryBytes;
    private String output;
    private String error;
    private List<TestResult> testResults;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestResult {
        private String name;
        private boolean passed;
        private Object input;
        private Object expected;
        private String error;
        private long durationMs;
    }
}