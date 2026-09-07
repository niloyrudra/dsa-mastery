package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ContentStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlgorithmDetailDto {
    private UUID id;
    private String slug;
    private String name;
    private String description;
    private String stepByStepReasoning;
    private String complexityAnalysis;
    private ComplexityDto complexity;
    private ContentStatus status;
    private List<PatternSummaryDto> patterns;
    private List<DataStructureSummaryDto> dataStructures;
    private List<TemplateSummaryDto> templates;
    private List<ProblemReferenceDto> problems;
    private List<ReferenceDto> references;
}