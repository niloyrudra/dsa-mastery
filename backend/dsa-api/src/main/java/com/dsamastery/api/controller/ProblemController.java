package com.dsamastery.api.controller;

import com.dsamastery.api.dto.ComplexityDto;
import com.dsamastery.api.dto.MasteryDto;
import com.dsamastery.api.dto.ProblemChapterDto;
import com.dsamastery.api.dto.ProblemListResponse;
import com.dsamastery.api.dto.ProblemSummaryDto;
import com.dsamastery.api.dto.ProblemVariationDto;
import com.dsamastery.api.dto.SolutionDto;
import com.dsamastery.api.dto.TestCaseDto;
import com.dsamastery.api.mapper.ProblemMapper;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.service.ProblemService;
import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.service.MasteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
public class ProblemController {
    
    private final ProblemService problemService;
    private final MasteryService masteryService;
    private final ProblemMapper problemMapper;
    
    @GetMapping
    public ResponseEntity<ProblemListResponse> getProblems(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(required = false) String pattern,
            @RequestParam(required = false) String algorithm,
            @RequestParam(required = false) String dataStructure,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "order") String sort) {
        
        List<Problem> problems = problemService.getPublishedProblems();
        
        List<ProblemSummaryDto> dtos = problems.stream()
            .map(p -> {
                MasteryRecord mastery = masteryService.getMastery("PROBLEM", p.getId()).orElse(null);
                return problemMapper.toSummaryDto(p, mastery);
            })
            .toList();
        
        return ResponseEntity.ok(ProblemListResponse.builder()
                .data(dtos)
                .nextCursor(null)
                .hasMore(false)
                .total(dtos.size())
                .build());
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<ProblemChapterDto> getProblem(@PathVariable String slug) {
        Optional<Problem> problemOpt = problemService.getProblemBySlug(slug);
        
        if (problemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        Problem problem = problemOpt.get();
        MasteryRecord mastery = masteryService.getMastery("PROBLEM", problem.getId()).orElse(null);
        
        ProblemChapterDto dto = problemMapper.toChapterDto(problem, mastery);
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/{slug}/variations")
    public ResponseEntity<List<ProblemVariationDto>> getVariations(@PathVariable String slug) {
        Optional<Problem> problemOpt = problemService.getProblemBySlug(slug);
        if (problemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Problem problem = problemOpt.get();
        return ResponseEntity.ok(problem.getVariations().stream()
            .map(v -> ProblemVariationDto.builder()
                .id(v.getId())
                .name(v.getName())
                .description(v.getDescription())
                .build())
            .toList());
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
    
    @GetMapping("/{slug}/solutions")
    public ResponseEntity<List<SolutionDto>> getSolutions(@PathVariable String slug) {
        Optional<Problem> problemOpt = problemService.getProblemBySlug(slug);
        if (problemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Problem problem = problemOpt.get();
        return ResponseEntity.ok(problem.getSolutions().stream()
            .map(s -> SolutionDto.builder()
                .id(s.getId())
                .name(s.getName())
                .approach(s.getApproach())
                .explanation(s.getExplanation())
                .whyItWorks(s.getWhyItWorks())
                .whyNotIdeal(s.getWhyNotIdeal())
                .bottleneckRemaining(s.getBottleneckRemaining())
                .insightToNext(s.getInsightToNext())
                .complexity(toComplexityDto(s.getComplexity()))
                .language(s.getLanguage())
                .code(s.getCode())
                .build())
            .toList());
    }
    
    @GetMapping("/{slug}/test-cases")
    public ResponseEntity<List<TestCaseDto>> getTestCases(@PathVariable String slug) {
        Optional<Problem> problemOpt = problemService.getProblemBySlug(slug);
        if (problemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Problem problem = problemOpt.get();
        return ResponseEntity.ok(problem.getTestCases().stream()
            .map(t -> TestCaseDto.builder()
                .id(t.getId())
                .name(t.getName())
                .input(t.getInput())
                .expectedOutput(t.getExpectedOutput())
                .description(t.getDescription())
                .type(t.getType())
                .isHidden(t.getIsHidden())
                .build())
            .toList());
    }
    
    @GetMapping("/{slug}/mastery")
    public ResponseEntity<MasteryDto> getMastery(@PathVariable String slug) {
        Optional<Problem> problemOpt = problemService.getProblemBySlug(slug);
        if (problemOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Problem problem = problemOpt.get();
        MasteryRecord mastery = masteryService.getMastery("PROBLEM", problem.getId()).orElse(null);
        return ResponseEntity.ok(problemMapper.toMasteryDto(mastery));
    }
    
}