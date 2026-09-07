package com.dsamastery.mastery.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.MasteryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "mastery_records", uniqueConstraints = @UniqueConstraint(columnNames = {"entity_type", "entity_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MasteryRecord extends BaseEntity {
    
    @Column(name = "entity_type", nullable = false, length = 30)
    private String entityType;
    
    @Column(name = "entity_id", nullable = false)
    private UUID entityId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private MasteryStatus status = MasteryStatus.NOT_STARTED;
    
    @Column(name = "mastery_score", nullable = false)
    @Builder.Default
    private Integer masteryScore = 0;
    
    // Problem-specific
    @Column(name = "viewed")
    @Builder.Default
    private Boolean viewed = false;
    
    @Column(name = "explanation_viewed")
    @Builder.Default
    private Boolean explanationViewed = false;
    
    @Column(name = "template_viewed")
    @Builder.Default
    private Boolean templateViewed = false;
    
    @Column(name = "attempts")
    @Builder.Default
    private Integer attempts = 0;
    
    @Column(name = "passed_tests")
    @Builder.Default
    private Boolean passedTests = false;
    
    @Column(name = "solution_revealed")
    @Builder.Default
    private Boolean solutionRevealed = false;
    
    @Column(name = "optimal_understood")
    @Builder.Default
    private Boolean optimalUnderstood = false;
    
    @Column(name = "variations_attempted")
    @Builder.Default
    private Boolean variationsAttempted = false;
    
    @Column(name = "last_practiced")
    private Instant lastPracticed;
    
    @Column(name = "mastered_at")
    private Instant masteredAt;
    
    // Pattern/Concept-specific
    @Column(name = "problems_solved")
    @Builder.Default
    private Integer problemsSolved = 0;
    
    @Column(name = "problems_attempted")
    @Builder.Default
    private Integer problemsAttempted = 0;
    
    @Column(name = "concept_proficiency")
    @Builder.Default
    private Double conceptProficiency = 0.0;
}