package com.dsamastery.api.dto;

import com.dsamastery.common.enums.Difficulty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemChapterDto {
    private UUID id;
    private String slug;
    private String title;
    private String description;
    private String externalUrl;
    private Difficulty difficulty;
    private List<PatternSummaryDto> patterns;
    private List<AlgorithmSummaryDto> algorithms;
    private List<DataStructureSummaryDto> dataStructures;
    private List<TemplateSummaryDto> templates;
    private List<ProblemVariationDto> variations;
    private List<SolutionDto> solutions;
    private List<TestCaseDto> testCases;
    private List<LearningObjectiveDto> learningObjectives;
    private List<ReferenceDto> references;
    private List<ProblemReferenceDto> relatedProblems;
    private List<ConceptSummaryDto> prerequisites;
    private MasteryDto mastery;
}