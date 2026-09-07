package com.dsamastery.api.mapper;

import com.dsamastery.api.dto.AlgorithmSummaryDto;
import com.dsamastery.api.dto.DataStructureDetailDto;
import com.dsamastery.api.dto.PatternSummaryDto;
import com.dsamastery.api.dto.ProblemReferenceDto;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Problem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DataStructureMapper {
    
    DataStructureMapper INSTANCE = Mappers.getMapper(DataStructureMapper.class);
    
    @Mapping(target = "problems", source = "problems", qualifiedByName = "toProblemSummary")
    @Mapping(target = "patterns", source = "patterns", qualifiedByName = "toPatternSummary")
    @Mapping(target = "algorithms", source = "algorithms", qualifiedByName = "toAlgorithmSummary")
    DataStructureDetailDto toDetailDto(DataStructure ds);
    
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
    
    @Named("toAlgorithmSummary")
    default List<AlgorithmSummaryDto> toAlgorithmSummary(List<Algorithm> algorithms) {
        return algorithms.stream()
            .map(a -> AlgorithmSummaryDto.builder()
                .id(a.getId())
                .slug(a.getSlug())
                .name(a.getName())
                .build())
            .collect(Collectors.toList());
    }
}