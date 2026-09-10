package com.dsamastery.mastery.controller;

import com.dsamastery.mastery.dto.ReviewDto;
import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.service.MasteryService;
import com.dsamastery.mastery.service.SpacedRepetitionService;
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
    private final SpacedRepetitionService spacedRepetitionService;

    @GetMapping("/review/stats")
    public ResponseEntity<SpacedRepetitionService.ReviewStats> getReviewStats() {
        return ResponseEntity.ok(spacedRepetitionService.getReviewStats());
    }

    @GetMapping("/review/due")
    public ResponseEntity<List<ReviewDto>> getDueReviews(@RequestParam(defaultValue = "20") int limit) {
        List<ReviewDto> reviews = spacedRepetitionService.getRecommendedReviews(limit)
            .stream()
            .map(r -> ReviewDto.from(r, spacedRepetitionService))
            .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/review/all")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<ReviewDto> reviews = masteryService.getAllMastery().stream()
            .map(r -> ReviewDto.from(r, spacedRepetitionService))
            .collect(Collectors.toList());
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/review/{entityType}/{entityId}")
    public ResponseEntity<ReviewDto> recordReview(
            @PathVariable String entityType,
            @PathVariable UUID entityId,
            @RequestBody ReviewRequest request) {
        
        SpacedRepetitionService.ReviewQuality quality = request.quality();
        MasteryRecord record = spacedRepetitionService.recordReview(entityId, entityType, quality);
        return ResponseEntity.ok(ReviewDto.from(record, spacedRepetitionService));
    }

    @GetMapping("/stats")
    public ResponseEntity<MasteryService.MasteryStats> getMasteryStats() {
        return ResponseEntity.ok(masteryService.getStats());
    }

    public record ReviewRequest(SpacedRepetitionService.ReviewQuality quality) {}
}