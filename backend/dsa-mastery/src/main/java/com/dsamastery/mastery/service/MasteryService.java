package com.dsamastery.mastery.service;

import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.repository.MasteryRecordRepository;
import com.dsamastery.common.enums.MasteryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MasteryService {
    
    private final MasteryRecordRepository masteryRecordRepository;
    
    @Transactional(readOnly = true)
    public Optional<MasteryRecord> getMastery(String entityType, UUID entityId) {
        return masteryRecordRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }
    
    @Transactional(readOnly = true)
    public List<MasteryRecord> getAllMastery() {
        return masteryRecordRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<MasteryRecord> getMasteryByType(String entityType) {
        return masteryRecordRepository.findByEntityType(entityType);
    }
    
    @Transactional
    public MasteryRecord updateMastery(String entityType, UUID entityId, MasteryUpdateRequest request) {
        MasteryRecord record = masteryRecordRepository.findByEntityTypeAndEntityId(entityType, entityId)
            .orElseGet(() -> MasteryRecord.builder()
                .entityType(entityType)
                .entityId(entityId)
                .build());
        
        if (request.viewed() != null) record.setViewed(request.viewed());
        if (request.explanationViewed() != null) record.setExplanationViewed(request.explanationViewed());
        if (request.templateViewed() != null) record.setTemplateViewed(request.templateViewed());
        if (request.attempts() != null) record.setAttempts(request.attempts());
        if (request.passedTests() != null) record.setPassedTests(request.passedTests());
        if (request.solutionRevealed() != null) record.setSolutionRevealed(request.solutionRevealed());
        if (request.optimalUnderstood() != null) record.setOptimalUnderstood(request.optimalUnderstood());
        if (request.variationsAttempted() != null) record.setVariationsAttempted(request.variationsAttempted());
        
        if (request.passedTests() != null && request.passedTests()) {
            record.setLastPracticed(Instant.now());
            if (record.getStatus() == MasteryStatus.NOT_STARTED || record.getStatus() == MasteryStatus.LEARNING) {
                record.setStatus(MasteryStatus.PRACTICING);
            }
        }
        
        // Calculate mastery score
        record.setMasteryScore(calculateScore(record));
        
        // Update status based on score
        updateStatus(record);
        
        return masteryRecordRepository.save(record);
    }
    
    private int calculateScore(MasteryRecord r) {
        int score = 0;
        if (r.getViewed()) score += 10;
        if (r.getExplanationViewed()) score += 15;
        if (r.getTemplateViewed()) score += 15;
        if (r.getAttempts() > 0) score += Math.min(r.getAttempts() * 5, 20);
        if (r.getPassedTests()) score += 25;
        if (r.getOptimalUnderstood()) score += 20;
        if (r.getVariationsAttempted()) score += 15;
        return Math.min(score, 100);
    }
    
    private void updateStatus(MasteryRecord r) {
        if (r.getMasteryScore() >= 90 && r.getPassedTests() && r.getOptimalUnderstood()) {
            r.setStatus(MasteryStatus.MASTERED);
            if (r.getMasteredAt() == null) {
                r.setMasteredAt(Instant.now());
            }
        } else if (r.getPassedTests() && r.getOptimalUnderstood()) {
            r.setStatus(MasteryStatus.SOLVED);
        } else if (r.getAttempts() > 0) {
            r.setStatus(MasteryStatus.PRACTICING);
        } else if (r.getViewed() || r.getExplanationViewed() || r.getTemplateViewed()) {
            r.setStatus(MasteryStatus.LEARNING);
        }
    }
    
    @Transactional(readOnly = true)
    public MasteryStats getStats() {
        List<MasteryRecord> all = masteryRecordRepository.findAll();
        long total = all.size();
        long mastered = all.stream().filter(r -> r.getStatus() == MasteryStatus.MASTERED).count();
        long learning = all.stream().filter(r -> r.getStatus() == MasteryStatus.LEARNING || r.getStatus() == MasteryStatus.PRACTICING).count();
        long notStarted = all.stream().filter(r -> r.getStatus() == MasteryStatus.NOT_STARTED).count();
        
        // Pattern stats
        var patternStats = all.stream()
            .filter(r -> "PATTERN".equals(r.getEntityType()))
            .collect(java.util.stream.Collectors.toMap(
                r -> r.getEntityId().toString(),
                r -> r.getMasteryScore()
            ));
        
        return new MasteryStats(
            new ProblemStats((int) total, (int) mastered, (int) learning, (int) notStarted),
            patternStats,
            total > 0 ? (int) ((mastered * 100) / total) : 0
        );
    }
    
    public record MasteryUpdateRequest(
        Boolean viewed,
        Boolean explanationViewed,
        Boolean templateViewed,
        Integer attempts,
        Boolean passedTests,
        Boolean solutionRevealed,
        Boolean optimalUnderstood,
        Boolean variationsAttempted
    ) {}
    
    public record MasteryStats(
        ProblemStats problems,
        java.util.Map<String, Integer> patterns,
        int overallProgress
    ) {}
    
    public record ProblemStats(
        int total,
        int mastered,
        int learning,
        int notStarted
    ) {}
}