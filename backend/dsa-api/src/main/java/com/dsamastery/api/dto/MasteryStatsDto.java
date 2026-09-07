package com.dsamastery.api.dto;

import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasteryStatsDto {
    private ProblemStats problems;
    private Map<String, Integer> patterns;
    private Integer overallProgress;
}