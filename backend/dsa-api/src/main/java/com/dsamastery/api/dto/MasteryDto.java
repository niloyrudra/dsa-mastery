package com.dsamastery.api.dto;

import com.dsamastery.common.enums.MasteryStatus;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasteryDto {
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
}