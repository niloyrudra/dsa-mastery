package com.dsamastery.api.mapper;

import com.dsamastery.api.dto.TemplateDetailDto;
import com.dsamastery.api.dto.ComplexityDto;
import com.dsamastery.api.dto.PatternSummaryDto;
import com.dsamastery.api.dto.ProblemReferenceDto;
import com.dsamastery.api.dto.TemplateMappingDto;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Template;
import com.dsamastery.content.entity.TemplateMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    
    TemplateMapper INSTANCE = Mappers.getMapper(TemplateMapper.class);
    
    @Mapping(target = "pattern", source = "pattern", qualifiedByName = "toPatternSummary")
    @Mapping(target = "exampleProblems", source = "problems", qualifiedByName = "toProblemSummary")
    @Mapping(target = "mappings", source = "mappings", qualifiedByName = "toMappingDto")
    @Mapping(target = "complexity", source = "complexity", qualifiedByName = "toComplexityDto")
    TemplateDetailDto toDetailDto(Template template);
    
    @Named("toPatternSummary")
    default PatternSummaryDto toPatternSummary(Pattern pattern) {
        if (pattern == null) return null;
        return PatternSummaryDto.builder()
            .id(pattern.getId())
            .slug(pattern.getSlug())
            .name(pattern.getName())
            .build();
    }
    
    @Named("toProblemSummary")
    default List<ProblemReferenceDto> toProblemSummary(List<Problem> problems) {
        return problems.stream()
            .map(p -> ProblemReferenceDto.builder()
                .id(p.getId())
                .slug(p.getSlug())
                .title(p.getTitle())
                .difficulty(p.getDifficulty().name())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toMappingDto")
    default List<TemplateMappingDto> toMappingDto(List<TemplateMapping> mappings) {
        return mappings.stream()
            .map(m -> TemplateMappingDto.builder()
                .templateMarker(m.getTemplateMarker())
                .solutionCodeSnippet(m.getSolutionCodeSnippet())
                .explanation(m.getExplanation())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toComplexityDto")
    default ComplexityDto toComplexityDto(com.dsamastery.common.entity.Complexity c) {
        if (c == null) return null;
        return ComplexityDto.builder()
            .timeComplexity(c.getTimeComplexity())
            .spaceComplexity(c.getSpaceComplexity())
            .timeReasoning(c.getTimeReasoning())
            .spaceReasoning(c.getSpaceReasoning())
            .bestCaseTime(c.getBestCaseTime())
            .worstCaseTime(c.getWorstCaseTime())
            .averageCaseTime(c.getAverageCaseTime())
            .build();
    }
}