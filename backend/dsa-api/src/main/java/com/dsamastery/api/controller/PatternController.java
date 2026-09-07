package com.dsamastery.api.controller;

import com.dsamastery.api.dto.ComplexityDto;
import com.dsamastery.api.dto.PatternDetailDto;
import com.dsamastery.api.mapper.PatternMapper;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.service.PatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patterns")
@RequiredArgsConstructor
public class PatternController {
    
    private final PatternService patternService;
    private final PatternMapper patternMapper;
    
    @GetMapping
    public ResponseEntity<List<PatternSummaryDto>> getPatterns() {
        List<Pattern> patterns = patternService.getPublishedPatterns();
        return ResponseEntity.ok(patterns.stream()
            .map(this::toSummary)
            .toList());
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<PatternDetailDto> getPattern(@PathVariable String slug) {
        Optional<Pattern> patternOpt = patternService.getPatternBySlug(slug);
        if (patternOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patternMapper.toDetailDto(patternOpt.get()));
    }
    
    private PatternSummaryDto toSummary(Pattern p) {
        return new PatternSummaryDto(
            p.getId(),
            p.getSlug(),
            p.getName(),
            p.getDefinition(),
            p.getWhenToUse(),
            p.getRecognitionSignals(),
            toComplexityDto(p.getComplexity()),
            p.getTemplates().size(),
            p.getProblems().size()
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
    
    record PatternSummaryDto(
        UUID id,
        String slug,
        String name,
        String definition,
        String whenToUse,
        List<String> recognitionSignals,
        ComplexityDto complexity,
        int templateCount,
        int problemCount
    ) {}
}