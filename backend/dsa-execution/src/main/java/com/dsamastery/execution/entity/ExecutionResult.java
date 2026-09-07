package com.dsamastery.execution.entity;

import com.dsamastery.common.enums.ExecutionStatus;

public record ExecutionResult(
    ExecutionStatus status,
    int passedTests,
    int totalTests,
    long executionTimeMs,
    Long memoryBytes,
    String output,
    String error
) {}