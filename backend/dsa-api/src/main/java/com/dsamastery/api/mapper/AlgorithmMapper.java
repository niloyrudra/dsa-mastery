package com.dsamastery.api.mapper;

import com.dsamastery.api.dto.AlgorithmDetailDto;
import com.dsamastery.api.dto.ComplexityDto;
import com.dsamastery.api.dto.DataStructureSummaryDto;
import com.dsamastery.api.dto.PatternSummaryDto;
import com.dsamastery.api.dto.ProblemReferenceDto;
import com.dsamastery.api.dto.ReferenceDto;
import com.dsamastery.api.dto.TemplateSummaryDto;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Reference;
import com.dsamastery.content.entity.Template;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AlgorithmMapper {
    
    AlgorithmMapper INSTANCE = Mappers.getMapper(AlgorithmMapper.class);
    
    @Mapping(target = "patterns", source = "patterns", qualifiedByName = "toPatternSummary")
    @Mapping(target = "dataStructures", source = "dataStructures", qualifiedByName = "toDataStructureSummary")
    @Mapping(target = "templates", source = "templates", qualifiedByName = "toTemplateSummary")
    @Mapping(target = "problems", source = "problems", qualifiedByName = "toProblemSummary")
    @Mapping(target = "references", source = "references", qualifiedByName = "toReferenceDto")
    @Mapping(target = "complexity", source = "complexity", qualifiedByName = "toComplexityDto")
    AlgorithmDetailDto toDetailDto(Algorithm algorithm);
    
    @Named("toPatternSummary")
    default List<PatternSummaryDto> toPatternSummary(List<Pattern> patterns) {
        return patterns.stream()
            .map(p -> PatternSummaryDto.builder()
                .id(p.getId())
                .slug(p.getSlug())
                .name(p.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toDataStructureSummary")
    default List<DataStructureSummaryDto> toDataStructureSummary(List<DataStructure> dss) {
        return dss.stream()
            .map(d -> DataStructureSummaryDto.builder()
                .id(d.getId())
                .slug(d.getSlug())
                .name(d.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toTemplateSummary")
    default List<TemplateSummaryDto> toTemplateSummary(List<Template> templates) {
        return templates.stream()
            .map(t -> TemplateSummaryDto.builder()
                .id(t.getId())
                .slug(t.getSlug())
                .name(t.getName())
                .language(t.getLanguage().name())
                .build())
            .collect(Collectors.toList());
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
    
    @Named("toReferenceDto")
    default List<ReferenceDto> toReferenceDto(List<Reference> references) {
        return references.stream()
            .map(r -> ReferenceDto.builder()
                .id(r.getId())
                .title(r.getTitle())
                .url(r.getUrl())
                .type(r.getType().name())
                .relevantConcept(r.getRelevantConcept())
                .verifies(r.getVerifies())
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