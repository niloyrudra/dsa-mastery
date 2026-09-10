package com.dsamastery.mastery.dto;

import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.service.SpacedRepetitionService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private UUID entityId;
    private String entityType;
    private Instant nextReviewDate;
    private boolean due;
    private boolean overdue;
    private int masteryScore;
    private String status;
    private Instant lastPracticed;

    public static ReviewDto from(MasteryRecord record, SpacedRepetitionService srs) {
        Instant nextReview = srs.calculateNextReview(record);
        boolean due = srs.isDueForReview(record);
        boolean overdue = nextReview.isBefore(Instant.now().minus(1, java.time.temporal.ChronoUnit.DAYS));
        
        return ReviewDto.builder()
            .entityId(record.getEntityId())
            .entityType(record.getEntityType())
            .nextReviewDate(nextReview)
            .due(due)
            .overdue(overdue)
            .masteryScore(record.getMasteryScore())
            .status(record.getStatus().name())
            .lastPracticed(record.getLastPracticed())
            .build();
    }
}