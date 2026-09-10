package com.dsamastery.mastery.service;

import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.repository.MasteryRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * Scheduled tasks for spaced repetition maintenance.
 * Runs periodically to update review statuses and send notifications.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SpacedRepetitionScheduler {

    private final MasteryRecordRepository masteryRecordRepository;
    private final SpacedRepetitionService spacedRepetitionService;

    /**
     * Run daily at 6 AM to log review statistics.
     */
    @Scheduled(cron = "0 0 6 * * *")
    @Transactional(readOnly = true)
    public void logDailyReviewStats() {
        var stats = spacedRepetitionService.getReviewStats();
        log.info("Daily review stats: due={}, overdue={}, learning={}, mastered={}", 
            stats.dueCount(), stats.overdueCount(), stats.learningCount(), stats.masteredCount());
    }

    /**
     * Run hourly to check for items becoming overdue and log.
     */
    @Scheduled(cron = "0 0 * * * *")
    @Transactional(readOnly = true)
    public void checkOverdueItems() {
        List<MasteryRecord> overdue = masteryRecordRepository.findAll().stream()
            .filter(spacedRepetitionService::isDueForReview)
            .filter(r -> spacedRepetitionService.calculateNextReview(r)
                .isBefore(Instant.now().minus(1, java.time.temporal.ChronoUnit.DAYS)))
            .toList();

        if (!overdue.isEmpty()) {
            log.info("Found {} overdue items for review", overdue.size());
        }
    }

    /**
     * Run every 30 minutes to refresh any cached review data.
     * This is a lightweight task that just ensures the review status is current.
     */
    @Scheduled(fixedRate = 1800000) // 30 minutes
    @Transactional(readOnly = true)
    public void refreshReviewStatus() {
        // Just trigger a check - the actual status is computed on-demand
        // This can be extended to update a materialized view or cache
        long dueCount = masteryRecordRepository.findAll().stream()
            .filter(spacedRepetitionService::isDueForReview)
            .count();
        
        if (dueCount > 0) {
            log.debug("{} items currently due for review", dueCount);
        }
    }
}