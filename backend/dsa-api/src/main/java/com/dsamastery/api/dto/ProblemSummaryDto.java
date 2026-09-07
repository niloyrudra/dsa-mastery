package com.dsamastery.api.dto;

import com.dsamastery.common.enums.Difficulty;
import com.dsamastery.common.enums.MasteryStatus;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemSummaryDto {
    private UUID id;
    private String slug;
    private String title;
    private Difficulty difficulty;
    private List<String> patterns;
    private List<String> algorithms;
    private List<String> dataStructures;
    private Integer orderIndex;
    private MasteryStatus masteryStatus;
}