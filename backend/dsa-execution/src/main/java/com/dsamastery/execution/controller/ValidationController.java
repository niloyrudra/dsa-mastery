package com.dsamastery.execution.controller;

import com.dsamastery.execution.service.ContentValidationService;
import com.dsamastery.content.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/validation")
@RequiredArgsConstructor
public class ValidationController {

    private final ContentValidationService validationService;
    private final ProblemRepository problemRepository;

    @PostMapping("/all")
    public ResponseEntity<ContentValidationService.ValidationReport> validateAll() {
        ContentValidationService.ValidationReport report = validationService.validateAllSolutions();
        return ResponseEntity.ok(report);
    }

    @PostMapping("/problem/{slug}")
    public ResponseEntity<ContentValidationService.ProblemValidationResult> validateProblem(@PathVariable String slug) {
        ContentValidationService.ProblemValidationResult result = validationService.validateProblemBySlug(slug);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/problem/id/{id}")
    public ResponseEntity<ContentValidationService.ProblemValidationResult> validateProblemById(@PathVariable UUID id) {
        return problemRepository.findById(id)
            .map(p -> {
                ContentValidationService.ProblemValidationResult result = validationService.validateProblem(p);
                return ResponseEntity.ok(result);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}