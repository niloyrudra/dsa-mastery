package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ContentStatus;
import com.dsamastery.common.enums.Language;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDetailDto {
    private UUID id;
    private String slug;
    private String name;
    private Language language;
    private String purpose;
    private String whenToReachForIt;
    private String prerequisites;
    private String mentalModel;
    private String code;
    private String explanation;
    private String invariant;
    private String commonModifications;
    private String commonMistakes;
    private ComplexityDto complexity;
    private ContentStatus status;
    private PatternSummaryDto pattern;
    private List<ProblemReferenceDto> exampleProblems;
    private List<TemplateMappingDto> mappings;
}