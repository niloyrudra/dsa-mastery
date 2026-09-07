package com.dsamastery.api.controller;

import com.dsamastery.api.dto.CurriculumNodeDto;
import com.dsamastery.api.mapper.CurriculumMapper;
import com.dsamastery.curriculum.entity.CurriculumNode;
import com.dsamastery.curriculum.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/curriculum")
@RequiredArgsConstructor
public class CurriculumController {
    
    private final CurriculumService curriculumService;
    private final CurriculumMapper curriculumMapper;
    
    @GetMapping
    public ResponseEntity<CurriculumNodeDto> getCurriculum() {
        Optional<CurriculumNode> root = curriculumService.getRoot();
        if (root.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(curriculumMapper.toDto(root.get()));
    }
    
    @GetMapping("/path/{problemSlug}")
    public ResponseEntity<LearningPathDto> getLearningPath(@PathVariable String problemSlug) {
        return curriculumService.getLearningPath(problemSlug)
            .map(lp -> new LearningPathDto(
                lp.targetProblem(),
                lp.prerequisites().stream()
                    .map(p -> new PrerequisiteDto(p.slug(), p.name(), p.type(), p.masteryStatus()))
                    .toList(),
                lp.nextProblems().stream()
                    .map(n -> new NextProblemDto(n.slug(), n.title(), n.difficulty()))
                    .toList()))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    record LearningPathDto(
        String targetProblem,
        List<PrerequisiteDto> prerequisites,
        List<NextProblemDto> nextProblems
    ) {}
    
    record PrerequisiteDto(
        String slug,
        String name,
        String type,
        String masteryStatus
    ) {}
    
    record NextProblemDto(
        String slug,
        String title,
        String difficulty
    ) {}
}