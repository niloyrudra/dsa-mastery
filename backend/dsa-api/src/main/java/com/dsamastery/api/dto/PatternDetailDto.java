package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ContentStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternDetailDto {
    private UUID id;
    private String slug;
    private String name;
    private String category;
    private String definition;
    private String whenToUse;
    private String whyItWorks;
    private String intuition;
    private String invariant;
    private String strengths;
    private String weaknesses;
    private List<String> recognitionSignals;
    private String commonFailureModes;
    private String generalizations;
    private ComplexityDto complexity;
    private ContentStatus status;
    private List<TemplateSummaryDto> templates;
    private List<AlgorithmSummaryDto> algorithms;
    private List<DataStructureSummaryDto> dataStructures;
    private List<ProblemReferenceDto> problems;
    private List<PatternSummaryDto> subPatterns;
    private List<PatternSummaryDto> relatedPatterns;
}