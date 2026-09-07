package com.dsamastery.execution.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.ExecutionStatus;
import com.dsamastery.common.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
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
    
    @Column(name = "attempted_at", nullable = false)
    @Builder.Default
    private Instant attemptedAt = Instant.now();
    
    @Column(name = "completed_at")
    private Instant completedAt;
}