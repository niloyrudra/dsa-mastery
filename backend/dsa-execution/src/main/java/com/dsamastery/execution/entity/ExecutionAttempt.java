package com.dsamastery.execution.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.ExecutionStatus;
import com.dsamastery.common.enums.Language;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "execution_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionAttempt extends BaseEntity {
    
    @Column(name = "problem_id", nullable = false)
    private UUID problemId;
    
    @Column(name = "user_id", nullable = false)
    @Builder.Default
    private UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false, length = 20)
    private Language language;
    
    @Column(name = "code", columnDefinition = "TEXT", nullable = false)
    private String code;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ExecutionStatus status = ExecutionStatus.QUEUED;
    
    @Column(name = "passed_tests")
    @Builder.Default
    private Integer passedTests = 0;
    
    @Column(name = "total_tests")
    @Builder.Default
    private Integer totalTests = 0;
    
    @Column(name = "execution_time_ms")
    private Long executionTimeMs;
    
    @Column(name = "memory_bytes")
    private Long memoryBytes;
    
    @Column(name = "output", columnDefinition = "TEXT")
    private String output;
    
    @Column(name = "error", columnDefinition = "TEXT")
    private String error;
    
    @Column(name = "test_results", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<TestResult> testResults;
    
    @Column(name = "attempted_at", nullable = false)
    @Builder.Default
    private Instant attemptedAt = Instant.now();
    
    @Column(name = "completed_at")
    private Instant completedAt;
    
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