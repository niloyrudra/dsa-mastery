package com.dsamastery.api.mapper;

import com.dsamastery.api.dto.CurriculumNodeDto;
import com.dsamastery.curriculum.entity.CurriculumNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CurriculumMapper {
    
    CurriculumMapper INSTANCE = Mappers.getMapper(CurriculumMapper.class);
    
    @Mapping(target = "children", source = "children", qualifiedByName = "toDtoList")
    CurriculumNodeDto toDto(CurriculumNode node);
    
    @Named("toDtoList")
    default List<CurriculumNodeDto> toDtoList(List<CurriculumNode> nodes) {
        return nodes.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}