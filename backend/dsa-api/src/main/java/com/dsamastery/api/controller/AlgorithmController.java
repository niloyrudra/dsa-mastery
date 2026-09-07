package com.dsamastery.api.controller;

import com.dsamastery.api.dto.AlgorithmDetailDto;
import com.dsamastery.api.dto.ComplexityDto;
import com.dsamastery.api.mapper.AlgorithmMapper;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.service.AlgorithmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/algorithms")
@RequiredArgsConstructor
public class AlgorithmController {
    
    private final AlgorithmService algorithmService;
    private final AlgorithmMapper algorithmMapper;
    
    @GetMapping
    public ResponseEntity<List<AlgorithmSummaryDto>> getAlgorithms() {
        List<Algorithm> algorithms = algorithmService.getPublishedAlgorithms();
        return ResponseEntity.ok(algorithms.stream()
            .map(this::toSummary)
            .toList());
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<AlgorithmDetailDto> getAlgorithm(@PathVariable String slug) {
        Optional<Algorithm> algorithmOpt = algorithmService.getAlgorithmBySlug(slug);
        if (algorithmOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(algorithmMapper.toDetailDto(algorithmOpt.get()));
    }
    
    private AlgorithmSummaryDto toSummary(Algorithm a) {
        return new AlgorithmSummaryDto(
            a.getId(),
            a.getSlug(),
            a.getName(),
            a.getDescription(),
            toComplexityDto(a.getComplexity()),
            a.getProblems().size()
        );
    }
    
    private ComplexityDto toComplexityDto(com.dsamastery.common.entity.Complexity c) {
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
    
    record AlgorithmSummaryDto(
        UUID id,
        String slug,
        String name,
        String description,
        ComplexityDto complexity,
        int problemCount
    ) {}
}