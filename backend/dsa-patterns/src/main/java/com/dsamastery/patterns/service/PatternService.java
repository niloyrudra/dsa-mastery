package com.dsamastery.patterns.service;

import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.repository.PatternRepository;
import com.dsamastery.common.enums.ContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Pattern-specific business logic: recognition, relationships, learning progression.
 * Complements the basic CRUD in dsa-content PatternService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatternService {

    private final PatternRepository patternRepository;

    /**
     * Find patterns that match the given recognition signals.
     * Used for auto-suggesting patterns when a learner is stuck.
     */
    public List<Pattern> findByRecognitionSignals(Collection<String> signals) {
        if (signals == null || signals.isEmpty()) {
            return List.of();
        }
        List<Pattern> allPublished = patternRepository.findByStatus(ContentStatus.PUBLISHED);
        return allPublished.stream()
            .filter(p -> p.getRecognitionSignals() != null && 
                !Collections.disjoint(p.getRecognitionSignals(), signals))
            .collect(Collectors.toList());
    }

    /**
     * Get the full pattern hierarchy (pattern + sub-patterns + parent patterns).
     */
    public PatternHierarchy getPatternHierarchy(String patternSlug) {
        return patternRepository.findBySlug(patternSlug)
            .map(this::buildHierarchy)
            .orElse(null);
    }

    /**
     * Get patterns that are commonly used together (co-occurrence in problems).
     */
    public List<Pattern> getRelatedPatterns(String patternSlug, int limit) {
        return patternRepository.findBySlug(patternSlug)
            .map(p -> p.getProblems().stream()
                .flatMap(prob -> prob.getPatterns().stream())
                .filter(other -> !other.getSlug().equals(patternSlug))
                .distinct()
                .limit(limit)
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    /**
     * Get suggested next patterns to learn based on already mastered patterns.
     * Uses sub-pattern relationships as prerequisites.
     */
    public List<Pattern> getSuggestedNextPatterns(Collection<String> masteredSlugs) {
        if (masteredSlugs == null || masteredSlugs.isEmpty()) {
            // Return foundational patterns
            return patternRepository.findByCategoryAndStatusOrderByNameAsc("ARRAYS_STRINGS", ContentStatus.PUBLISHED)
                .stream().limit(5).collect(Collectors.toList());
        }

        Set<String> mastered = new HashSet<>(masteredSlugs);
        Set<String> suggested = new HashSet<>();

        // Find patterns that have mastered patterns as parent (prerequisite) patterns
        List<Pattern> allPublished = patternRepository.findByStatus(ContentStatus.PUBLISHED);
        for (Pattern p : allPublished) {
            if (!mastered.contains(p.getSlug())) {
                // Check if all parent patterns are mastered
                boolean prereqsMet = p.getParentPatterns() == null || p.getParentPatterns().isEmpty() ||
                    p.getParentPatterns().stream().allMatch(parent -> mastered.contains(parent.getSlug()));
                if (prereqsMet) {
                    suggested.add(p.getSlug());
                }
            }
        }

        return allPublished.stream()
            .filter(p -> suggested.contains(p.getSlug()))
            .limit(10)
            .collect(Collectors.toList());
    }

    /**
     * Get pattern difficulty progression grouped by category.
     */
    public Map<String, List<Pattern>> getPatternsByCategory() {
        List<Pattern> allPublished = patternRepository.findByStatus(ContentStatus.PUBLISHED);
        return allPublished.stream()
            .filter(p -> p.getCategory() != null)
            .collect(Collectors.groupingBy(
                Pattern::getCategory,
                Collectors.collectingAndThen(
                    Collectors.toList(),
                    list -> list.stream()
                        .sorted(Comparator.comparing(Pattern::getName))
                        .collect(Collectors.toList())
                )
            ));
    }

    /**
     * Get pattern statistics: how many problems use each pattern.
     */
    public List<PatternStats> getPatternStatistics() {
        return patternRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .map(p -> new PatternStats(
                p.getSlug(),
                p.getName(),
                p.getCategory(),
                p.getProblems().size(),
                p.getTemplates().size(),
                p.getAlgorithms().size(),
                p.getDataStructures().size()
            ))
            .sorted(Comparator.comparing(PatternStats::problemCount).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Find the "bridge" patterns that connect two different pattern categories.
     */
    public List<Pattern> getBridgePatterns(String category1, String category2) {
        List<Pattern> cat1 = patternRepository.findByCategoryAndStatus(category1, ContentStatus.PUBLISHED);
        List<Pattern> cat2 = patternRepository.findByCategoryAndStatus(category2, ContentStatus.PUBLISHED);
        
        Set<String> cat1Slugs = cat1.stream().map(Pattern::getSlug).collect(Collectors.toSet());
        Set<String> cat2Slugs = cat2.stream().map(Pattern::getSlug).collect(Collectors.toSet());

        return patternRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .filter(p -> cat1Slugs.contains(p.getSlug()) || cat2Slugs.contains(p.getSlug()))
            .filter(p -> p.getSubPatterns() != null && p.getParentPatterns() != null)
            .filter(p -> p.getSubPatterns().stream()
                .anyMatch(sp -> cat1Slugs.contains(sp.getSlug())) &&
                    p.getParentPatterns().stream()
                        .anyMatch(pp -> cat2Slugs.contains(pp.getSlug())))
            .collect(Collectors.toList());
    }

    private PatternHierarchy buildHierarchy(Pattern pattern) {
        List<PatternHierarchy> subPatterns = pattern.getSubPatterns() != null ?
            pattern.getSubPatterns().stream()
                .map(this::buildHierarchy)
                .collect(Collectors.toList()) : List.of();

        List<PatternHierarchy> parentPatterns = pattern.getParentPatterns() != null ?
            pattern.getParentPatterns().stream()
                .map(this::buildHierarchy)
                .collect(Collectors.toList()) : List.of();

        return new PatternHierarchy(
            pattern.getSlug(),
            pattern.getName(),
            pattern.getCategory(),
            subPatterns,
            parentPatterns
        );
    }

    // DTOs
    public record PatternHierarchy(
        String slug,
        String name,
        String category,
        List<PatternHierarchy> subPatterns,
        List<PatternHierarchy> parentPatterns
    ) {}

    public record PatternStats(
        String slug,
        String name,
        String category,
        int problemCount,
        int templateCount,
        int algorithmCount,
        int dataStructureCount
    ) {}
}