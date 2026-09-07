package com.dsamastery.api.dto;

import com.dsamastery.common.enums.MasteryStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasteryRecordDto {
    private UUID entityId;
    private String entityType;
    private MasteryStatus status;
    private Integer score;
    private Boolean viewed;
    private Boolean explanationViewed;
    private Boolean templateViewed;
    private Integer attempts;
    private Boolean passedTests;
    private Boolean solutionRevealed;
    private Boolean optimalUnderstood;
    private Boolean variationsAttempted;
    private String lastPracticed;
    private String masteredAt;
    private Integer problemsSolved;
    private Integer problemsAttempted;
    private Double conceptProficiency;
}