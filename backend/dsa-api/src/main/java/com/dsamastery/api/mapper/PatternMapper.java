package com.dsamastery.api.mapper;

import com.dsamastery.api.dto.AlgorithmSummaryDto;
import com.dsamastery.api.dto.ComplexityDto;
import com.dsamastery.api.dto.DataStructureSummaryDto;
import com.dsamastery.api.dto.PatternDetailDto;
import com.dsamastery.api.dto.PatternSummaryDto;
import com.dsamastery.api.dto.ProblemReferenceDto;
import com.dsamastery.api.dto.TemplateSummaryDto;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PatternMapper {
    
    PatternMapper INSTANCE = Mappers.getMapper(PatternMapper.class);
    
    @Mapping(target = "templates", source = "templates", qualifiedByName = "toTemplateSummary")
    @Mapping(target = "algorithms", source = "algorithms", qualifiedByName = "toAlgorithmSummary")
    @Mapping(target = "dataStructures", source = "dataStructures", qualifiedByName = "toDataStructureSummary")
    @Mapping(target = "problems", source = "problems", qualifiedByName = "toProblemSummary")
    @Mapping(target = "subPatterns", source = "subPatterns", qualifiedByName = "toPatternSummary")
    @Mapping(target = "relatedPatterns", source = "parentPatterns", qualifiedByName = "toPatternSummary")
    @Mapping(target = "complexity", source = "complexity", qualifiedByName = "toComplexityDto")
    PatternDetailDto toDetailDto(Pattern pattern);
    
    @Named("toTemplateSummary")
    default List<TemplateSummaryDto> toTemplateSummary(Set<Template> templates) {
        if (templates == null) return List.of();
        return templates.stream()
            .map(t -> TemplateSummaryDto.builder()
                .id(t.getId())
                .slug(t.getSlug())
                .name(t.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toAlgorithmSummary")
    default List<AlgorithmSummaryDto> toAlgorithmSummary(Set<Algorithm> algorithms) {
        if (algorithms == null) return List.of();
        return algorithms.stream()
            .map(a -> AlgorithmSummaryDto.builder()
                .id(a.getId())
                .slug(a.getSlug())
                .name(a.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toDataStructureSummary")
    default List<DataStructureSummaryDto> toDataStructureSummary(Set<DataStructure> dss) {
        if (dss == null) return List.of();
        return dss.stream()
            .map(d -> DataStructureSummaryDto.builder()
                .id(d.getId())
                .slug(d.getSlug())
                .name(d.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toProblemSummary")
    default List<ProblemReferenceDto> toProblemSummary(Set<Problem> problems) {
        if (problems == null) return List.of();
        return problems.stream()
            .map(p -> ProblemReferenceDto.builder()
                .id(p.getId())
                .slug(p.getSlug())
                .title(p.getTitle())
                .difficulty(p.getDifficulty().name())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toPatternSummary")
    default List<PatternSummaryDto> toPatternSummary(Set<Pattern> patterns) {
        if (patterns == null) return List.of();
        return patterns.stream()
            .map(p -> PatternSummaryDto.builder()
                .id(p.getId())
                .slug(p.getSlug())
                .name(p.getName())
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