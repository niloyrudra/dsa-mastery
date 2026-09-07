package com.dsamastery.api.controller;

import com.dsamastery.api.dto.MasteryRecordDto;
import com.dsamastery.api.dto.MasteryStatsDto;
import com.dsamastery.api.dto.ProblemStats;
import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.service.MasteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/mastery")
@RequiredArgsConstructor
public class MasteryController {
    
    private final MasteryService masteryService;
    
    @GetMapping
    public ResponseEntity<List<MasteryRecordDto>> getAllMastery(
            @RequestParam(required = false) String entityType,
            @RequestParam(required = false) String status) {
        
        List<MasteryRecord> records;
        if (entityType != null) {
            records = masteryService.getMasteryByType(entityType);
        } else {
            records = masteryService.getAllMastery();
        }
        
        return ResponseEntity.ok(records.stream()
            .map(this::toDto)
            .collect(Collectors.toList()));
    }
    
    @GetMapping("/stats")
    public ResponseEntity<MasteryStatsDto> getStats() {
        MasteryService.MasteryStats stats = masteryService.getStats();
        return ResponseEntity.ok(MasteryStatsDto.builder()
            .problems(ProblemStats.builder()
                .total(stats.problems().total())
                .mastered(stats.problems().mastered())
                .learning(stats.problems().learning())
                .notStarted(stats.problems().notStarted())
                .build())
            .patterns(stats.patterns())
            .overallProgress(stats.overallProgress())
            .build());
    }
    
    @PatchMapping("/{entityType}/{entityId}")
    public ResponseEntity<MasteryRecordDto> updateMastery(
            @PathVariable String entityType,
            @PathVariable UUID entityId,
            @RequestBody MasteryService.MasteryUpdateRequest request) {
        
        MasteryRecord record = masteryService.updateMastery(entityType, entityId, request);
        return ResponseEntity.ok(toDto(record));
    }
    
    private MasteryRecordDto toDto(MasteryRecord r) {
        return MasteryRecordDto.builder()
            .entityId(r.getEntityId())
            .entityType(r.getEntityType())
            .status(r.getStatus())
            .score(r.getMasteryScore())
            .viewed(r.getViewed())
            .explanationViewed(r.getExplanationViewed())
            .templateViewed(r.getTemplateViewed())
            .attempts(r.getAttempts())
            .passedTests(r.getPassedTests())
            .solutionRevealed(r.getSolutionRevealed())
            .optimalUnderstood(r.getOptimalUnderstood())
            .variationsAttempted(r.getVariationsAttempted())
            .lastPracticed(r.getLastPracticed() != null ? r.getLastPracticed().toString() : null)
            .masteredAt(r.getMasteredAt() != null ? r.getMasteredAt().toString() : null)
            .problemsSolved(r.getProblemsSolved())
            .problemsAttempted(r.getProblemsAttempted())
            .conceptProficiency(r.getConceptProficiency())
            .build();
    }
}