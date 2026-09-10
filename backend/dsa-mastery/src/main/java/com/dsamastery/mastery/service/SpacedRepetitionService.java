package com.dsamastery.mastery.service;

import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.repository.MasteryRecordRepository;
import com.dsamastery.common.enums.MasteryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spaced repetition scheduler using SM-2 algorithm (simplified).
 * Determines when items should be reviewed based on mastery level and history.
 */
@Service
@RequiredArgsConstructor
public class SpacedRepetitionService {

    private final MasteryRecordRepository masteryRecordRepository;

    // SM-2 intervals (in days)
    private static final int[] INTERVALS = {1, 3, 7, 14, 30, 90, 180, 365};

    /**
     * Calculate the next review date for a mastery record using SM-2 algorithm.
     */
    public Instant calculateNextReview(MasteryRecord record) {
        if (record.getStatus() == MasteryStatus.NOT_STARTED) {
            return Instant.now().plus(1, ChronoUnit.DAYS);
        }

        int repetitionCount = getRepetitionCount(record);
        int intervalDays = getIntervalDays(repetitionCount, record.getMasteryScore());
        
        Instant lastReviewed = record.getLastPracticed() != null 
            ? record.getLastPracticed() 
            : Instant.now();
        
        return lastReviewed.plus(intervalDays, ChronoUnit.DAYS);
    }

    /**
     * Get items due for review (next review date <= now).
     */
    @Transactional(readOnly = true)
    public List<MasteryRecord> getDueForReview() {
        Instant now = Instant.now();
        return masteryRecordRepository.findAll().stream()
            .filter(this::isDueForReview)
            .toList();
    }

    /**
     * Check if a record is due for review.
     */
    public boolean isDueForReview(MasteryRecord record) {
        Instant nextReview = calculateNextReview(record);
        return !nextReview.isAfter(Instant.now());
    }

    /**
     * Get the number of successful repetitions (based on mastery score progression).
     */
    private int getRepetitionCount(MasteryRecord record) {
        if (record.getMasteryScore() >= 90) return 7;
        if (record.getMasteryScore() >= 75) return 5;
        if (record.getMasteryScore() >= 60) return 4;
        if (record.getMasteryScore() >= 45) return 3;
        if (record.getMasteryScore() >= 30) return 2;
        if (record.getMasteryScore() >= 15) return 1;
        return 0;
    }

    /**
     * Get interval days based on repetition count and mastery score.
     * Higher mastery scores get longer intervals.
     */
    private int getIntervalDays(int repetitionCount, int masteryScore) {
        int baseIndex = Math.min(repetitionCount, INTERVALS.length - 1);
        int baseInterval = INTERVALS[baseIndex];
        
        // Adjust based on mastery score
        double masteryFactor = masteryScore / 100.0;
        if (masteryFactor > 0.8) {
            return (int) (baseInterval * 1.5);
        } else if (masteryFactor > 0.6) {
            return (int) (baseInterval * 1.2);
        } else if (masteryFactor < 0.4) {
            return Math.max(1, baseInterval / 2);
        }
        return baseInterval;
    }

    /**
     * Record a review outcome and update next review date.
     * Called when user practices an item.
     */
    @Transactional
    public MasteryRecord recordReview(UUID entityId, String entityType, ReviewQuality quality) {
        MasteryRecord record = masteryRecordRepository.findByEntityTypeAndEntityId(entityType, entityId)
            .orElseGet(() -> MasteryRecord.builder()
                .entityType(entityType)
                .entityId(entityId)
                .build());

        // Update based on review quality (SM-2 style)
        switch (quality) {
            case AGAIN -> {
                // Complete failure - reset to first interval
                record.setMasteryScore(Math.max(0, record.getMasteryScore() - 20));
                record.setStatus(MasteryStatus.LEARNING);
            }
            case HARD -> {
                // Difficult but correct - small progress
                record.setMasteryScore(Math.min(100, record.getMasteryScore() + 5));
                if (record.getStatus() == MasteryStatus.NOT_STARTED) {
                    record.setStatus(MasteryStatus.LEARNING);
                }
            }
            case GOOD -> {
                // Correct with some effort - normal progress
                record.setMasteryScore(Math.min(100, record.getMasteryScore() + 10));
                if (record.getStatus() == MasteryStatus.LEARNING) {
                    record.setStatus(MasteryStatus.PRACTICING);
                }
            }
            case EASY -> {
                // Easy recall - faster progress
                record.setMasteryScore(Math.min(100, record.getMasteryScore() + 15));
                if (record.getStatus() == MasteryStatus.PRACTICING && record.getMasteryScore() >= 75) {
                    record.setStatus(MasteryStatus.SOLVED);
                }
            }
        }

        record.setLastPracticed(Instant.now());
        
        // Check for mastery
        if (record.getMasteryScore() >= 90 && record.getPassedTests() && record.getOptimalUnderstood()) {
            record.setStatus(MasteryStatus.MASTERED);
            if (record.getMasteredAt() == null) {
                record.setMasteredAt(Instant.now());
            }
        }

        return masteryRecordRepository.save(record);
    }

    /**
     * Get review statistics for the user dashboard.
     */
    @Transactional(readOnly = true)
    public ReviewStats getReviewStats() {
        List<MasteryRecord> all = masteryRecordRepository.findAll();
        Instant now = Instant.now();
        
        long due = all.stream().filter(this::isDueForReview).count();
        long overdue = all.stream()
            .filter(r -> r.getLastPracticed() != null)
            .filter(r -> calculateNextReview(r).isBefore(now.minus(7, ChronoUnit.DAYS)))
            .count();
        long learning = all.stream()
            .filter(r -> r.getStatus() == MasteryStatus.LEARNING || r.getStatus() == MasteryStatus.PRACTICING)
            .count();
        long mastered = all.stream()
            .filter(r -> r.getStatus() == MasteryStatus.MASTERED)
            .count();

        // Next 7 days forecast
        long[] dailyDue = new long[7];
        for (MasteryRecord r : all) {
            Instant nextReview = calculateNextReview(r);
            for (int i = 0; i < 7; i++) {
                Instant dayStart = now.truncatedTo(ChronoUnit.DAYS).plus(i, ChronoUnit.DAYS);
                Instant dayEnd = dayStart.plus(1, ChronoUnit.DAYS);
                if (!nextReview.isBefore(dayStart) && nextReview.isBefore(dayEnd)) {
                    dailyDue[i]++;
                }
            }
        }

        return new ReviewStats(
            (int) due,
            (int) overdue,
            (int) learning,
            (int) mastered,
            dailyDue
        );
    }

    /**
     * Get recommended items to practice now (due + highest priority).
     */
    @Transactional(readOnly = true)
    public List<MasteryRecord> getRecommendedReviews(int limit) {
        return getDueForReview().stream()
            .sorted((a, b) -> {
                // Priority: overdue first, then lower mastery score
                boolean aOverdue = isOverdue(a);
                boolean bOverdue = isOverdue(b);
                if (aOverdue != bOverdue) return aOverdue ? -1 : 1;
                return Integer.compare(a.getMasteryScore(), b.getMasteryScore());
            })
            .limit(limit)
            .toList();
    }

    private boolean isOverdue(MasteryRecord record) {
        Instant nextReview = calculateNextReview(record);
        return nextReview.isBefore(Instant.now().minus(1, ChronoUnit.DAYS));
    }

    /**
     * Review quality levels (SM-2 inspired).
     */
    public enum ReviewQuality {
        AGAIN,  // Forgot completely
        HARD,   // Recalled with difficulty
        GOOD,   // Recalled correctly
        EASY    // Recalled effortlessly
    }

    public record ReviewStats(
        int dueCount,
        int overdueCount,
        int learningCount,
        int masteredCount,
        long[] dailyDueNext7Days
    ) {}
}