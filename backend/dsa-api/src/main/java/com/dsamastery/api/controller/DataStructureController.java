package com.dsamastery.api.controller;

import com.dsamastery.api.dto.DataStructureDetailDto;
import com.dsamastery.api.mapper.DataStructureMapper;
import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.service.DataStructureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/data-structures")
@RequiredArgsConstructor
public class DataStructureController {
    
    private final DataStructureService dataStructureService;
    private final DataStructureMapper dataStructureMapper;
    
    @GetMapping
    public ResponseEntity<List<DataStructureSummaryDto>> getDataStructures() {
        List<DataStructure> dss = dataStructureService.getPublishedDataStructures();
        return ResponseEntity.ok(dss.stream()
            .map(this::toSummary)
            .toList());
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<DataStructureDetailDto> getDataStructure(@PathVariable String slug) {
        Optional<DataStructure> dsOpt = dataStructureService.getDataStructureBySlug(slug);
        if (dsOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dataStructureMapper.toDetailDto(dsOpt.get()));
    }
    
    private DataStructureSummaryDto toSummary(DataStructure ds) {
        return new DataStructureSummaryDto(
            ds.getId(),
            ds.getSlug(),
            ds.getName(),
            ds.getDescription(),
            ds.getProblems().size()
        );
    }
    
    record DataStructureSummaryDto(
        UUID id,
        String slug,
        String name,
        String description,
        int problemCount
    ) {}
}