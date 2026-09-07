package com.dsamastery.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComplexityDto {
    private String timeComplexity;
    private String spaceComplexity;
    private String timeReasoning;
    private String spaceReasoning;
    private String bestCaseTime;
    private String worstCaseTime;
    private String averageCaseTime;
}