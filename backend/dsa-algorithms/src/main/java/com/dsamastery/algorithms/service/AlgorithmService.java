package com.dsamastery.algorithms.service;

import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.repository.AlgorithmRepository;
import com.dsamastery.common.enums.ContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Algorithm-specific business logic: variants, comparisons, learning progression.
 * Complements the basic CRUD in dsa-content AlgorithmService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlgorithmService {

    private final AlgorithmRepository algorithmRepository;

    /**
     * Get all published algorithms.
     */
    public List<Algorithm> getPublishedAlgorithms() {
        return algorithmRepository.findByStatusOrderByNameAsc(ContentStatus.PUBLISHED);
    }

    /**
     * Get algorithm by slug with relations.
     */
    public Optional<Algorithm> getAlgorithmBySlug(String slug) {
        return algorithmRepository.findBySlug(slug);
    }

    /**
     * Get algorithms that share data structures with the given algorithm.
     */
    public List<Algorithm> getAlgorithmsWithSharedDataStructures(String algorithmSlug) {
        return algorithmRepository.findBySlug(algorithmSlug)
            .map(algo -> algo.getDataStructures().stream()
                .flatMap(ds -> ds.getAlgorithms().stream())
                .filter(a -> !a.getSlug().equals(algorithmSlug))
                .distinct()
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    /**
     * Get algorithms that share templates with the given algorithm.
     */
    public List<Algorithm> getAlgorithmsWithSharedTemplates(String algorithmSlug) {
        return algorithmRepository.findBySlug(algorithmSlug)
            .map(algo -> algo.getTemplates().stream()
                .flatMap(t -> t.getProblems().stream())
                .flatMap(p -> p.getAlgorithms().stream())
                .filter(a -> !a.getSlug().equals(algorithmSlug))
                .distinct()
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    /**
     * Get related algorithms via shared patterns.
     */
    public List<Algorithm> getRelatedAlgorithms(String algorithmSlug, int limit) {
        return algorithmRepository.findBySlug(algorithmSlug)
            .map(algo -> algo.getPatterns().stream()
                .flatMap(p -> p.getAlgorithms().stream())
                .filter(a -> !a.getSlug().equals(algorithmSlug))
                .distinct()
                .limit(limit)
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    /**
     * Get algorithm variants (algorithms solving similar problems).
     * Based on shared problem domains and data structures.
     */
    public List<AlgorithmVariant> getVariants(String algorithmSlug) {
        return algorithmRepository.findBySlug(algorithmSlug)
            .map(algo -> {
                Set<String> problemSlugs = algo.getProblems().stream()
                    .map(p -> p.getSlug())
                    .collect(Collectors.toSet());

                List<Algorithm> related = algorithmRepository.findByStatus(ContentStatus.PUBLISHED).stream()
                    .filter(a -> !a.getSlug().equals(algorithmSlug))
                    .filter(a -> a.getProblems().stream()
                        .anyMatch(p -> problemSlugs.contains(p.getSlug())))
                    .collect(Collectors.toList());

                return related.stream()
                    .map(a -> new AlgorithmVariant(
                        a.getSlug(),
                        a.getName(),
                        calculateSimilarity(algo, a),
                        getSharedDataStructures(algo, a),
                        getSharedTemplates(algo, a)
                    ))
                    .sorted(Comparator.comparing(AlgorithmVariant::similarity).reversed())
                    .collect(Collectors.toList());
            })
            .orElse(List.of());
    }

    /**
     * Get algorithm learning progression (prerequisites -> this -> next).
     */
    public AlgorithmProgression getLearningProgression(String algorithmSlug) {
        return algorithmRepository.findBySlug(algorithmSlug)
            .map(algo -> {
                // Prerequisites: algorithms that share simpler data structures
                List<Algorithm> prereqs = algorithmRepository.findByStatus(ContentStatus.PUBLISHED).stream()
                    .filter(a -> !a.getSlug().equals(algorithmSlug))
                    .filter(a -> isPrerequisiteFor(a, algo))
                    .collect(Collectors.toList());

                // Next steps: algorithms that build on this one
                List<Algorithm> nextSteps = algorithmRepository.findByStatus(ContentStatus.PUBLISHED).stream()
                    .filter(a -> !a.getSlug().equals(algorithmSlug))
                    .filter(a -> isPrerequisiteFor(algo, a))
                    .collect(Collectors.toList());

                return new AlgorithmProgression(
                    algo.getSlug(),
                    algo.getName(),
                    prereqs.stream().map(a -> new PrerequisiteStep(a.getSlug(), a.getName(), "Foundational")).collect(Collectors.toList()),
                    nextSteps.stream().map(a -> new NextStep(a.getSlug(), a.getName(), "Advanced")).collect(Collectors.toList())
                );
            })
            .orElse(null);
    }

    /**
     * Compare algorithms by complexity and use cases.
     */
    public AlgorithmComparison compareAlgorithms(Collection<String> algorithmSlugs) {
        List<Algorithm> algos = algorithmRepository.findAllById(
            algorithmRepository.findByStatus(ContentStatus.PUBLISHED).stream()
                .filter(a -> algorithmSlugs.contains(a.getSlug()))
                .map(Algorithm::getId)
                .collect(Collectors.toList())
        );

        return new AlgorithmComparison(
            algos.stream().map(this::toComparisonEntry).collect(Collectors.toList())
        );
    }

    /**
     * Get algorithm statistics.
     */
    public List<AlgorithmStats> getAlgorithmStatistics() {
        return algorithmRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .map(a -> new AlgorithmStats(
                a.getSlug(),
                a.getName(),
                a.getProblems().size(),
                a.getPatterns().size(),
                a.getTemplates().size(),
                a.getDataStructures().size(),
                a.getComplexity() != null ? a.getComplexity().getTimeComplexity() : "N/A",
                a.getComplexity() != null ? a.getComplexity().getSpaceComplexity() : "N/A"
            ))
            .sorted(Comparator.comparing(AlgorithmStats::problemCount).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Find algorithms by complexity class.
     */
    public List<Algorithm> findByComplexityClass(String timeComplexity) {
        return algorithmRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .filter(a -> a.getComplexity() != null && 
                timeComplexity.equalsIgnoreCase(a.getComplexity().getTimeComplexity()))
            .collect(Collectors.toList());
    }

    private boolean isPrerequisiteFor(Algorithm prereq, Algorithm target) {
        // Simple heuristic: prerequisite if it uses subset of data structures
        // and has "simpler" complexity
        if (prereq.getDataStructures() == null || target.getDataStructures() == null) return false;
        
        Set<String> prereqDS = prereq.getDataStructures().stream()
            .map(ds -> ds.getSlug())
            .collect(Collectors.toSet());
        Set<String> targetDS = target.getDataStructures().stream()
            .map(ds -> ds.getSlug())
            .collect(Collectors.toSet());
        
        // Prerequisite uses subset of data structures
        return targetDS.containsAll(prereqDS) && prereqDS.size() < targetDS.size();
    }

    private double calculateSimilarity(Algorithm a1, Algorithm a2) {
        Set<String> ds1 = a1.getDataStructures().stream().map(ds -> ds.getSlug()).collect(Collectors.toSet());
        Set<String> ds2 = a2.getDataStructures().stream().map(ds -> ds.getSlug()).collect(Collectors.toSet());
        Set<String> templates1 = a1.getTemplates().stream().map(t -> t.getSlug()).collect(Collectors.toSet());
        Set<String> templates2 = a2.getTemplates().stream().map(t -> t.getSlug()).collect(Collectors.toSet());
        Set<String> patterns1 = a1.getPatterns().stream().map(p -> p.getSlug()).collect(Collectors.toSet());
        Set<String> patterns2 = a2.getPatterns().stream().map(p -> p.getSlug()).collect(Collectors.toSet());

        double dsSim = jaccardSimilarity(ds1, ds2);
        double tmplSim = jaccardSimilarity(templates1, templates2);
        double patSim = jaccardSimilarity(patterns1, patterns2);

        return (dsSim + tmplSim + patSim) / 3.0;
    }

    private double jaccardSimilarity(Set<String> a, Set<String> b) {
        if (a.isEmpty() && b.isEmpty()) return 1.0;
        if (a.isEmpty() || b.isEmpty()) return 0.0;
        
        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        Set<String> union = new HashSet<>(a);
        union.addAll(b);
        
        return (double) intersection.size() / union.size();
    }

    private List<String> getSharedDataStructures(Algorithm a1, Algorithm a2) {
        Set<String> ds1 = a1.getDataStructures().stream().map(ds -> ds.getSlug()).collect(Collectors.toSet());
        Set<String> ds2 = a2.getDataStructures().stream().map(ds -> ds.getSlug()).collect(Collectors.toSet());
        ds1.retainAll(ds2);
        return new ArrayList<>(ds1);
    }

    private List<String> getSharedTemplates(Algorithm a1, Algorithm a2) {
        Set<String> t1 = a1.getTemplates().stream().map(t -> t.getSlug()).collect(Collectors.toSet());
        Set<String> t2 = a2.getTemplates().stream().map(t -> t.getSlug()).collect(Collectors.toSet());
        t1.retainAll(t2);
        return new ArrayList<>(t1);
    }

    private AlgorithmComparison.Entry toComparisonEntry(Algorithm a) {
        return new AlgorithmComparison.Entry(
            a.getSlug(),
            a.getName(),
            a.getComplexity() != null ? a.getComplexity().getTimeComplexity() : "N/A",
            a.getComplexity() != null ? a.getComplexity().getSpaceComplexity() : "N/A",
            a.getDataStructures().stream().map(ds -> ds.getName()).collect(Collectors.toList()),
            a.getPatterns().stream().map(p -> p.getName()).collect(Collectors.toList())
        );
    }

    // DTOs
    public record AlgorithmVariant(
        String slug,
        String name,
        double similarity,
        List<String> sharedDataStructures,
        List<String> sharedTemplates
    ) {}

    public record PrerequisiteStep(
        String slug,
        String name,
        String reason
    ) {}

    public record NextStep(
        String slug,
        String name,
        String reason
    ) {}

    public record AlgorithmProgression(
        String slug,
        String name,
        List<PrerequisiteStep> prerequisites,
        List<NextStep> nextSteps
    ) {}

    public record AlgorithmComparison(
        List<Entry> entries
    ) {
        public record Entry(
            String slug,
            String name,
            String timeComplexity,
            String spaceComplexity,
            List<String> dataStructures,
            List<String> patterns
        ) {}
    }

    public record AlgorithmStats(
        String slug,
        String name,
        int problemCount,
        int patternCount,
        int templateCount,
        int dataStructureCount,
        String timeComplexity,
        String spaceComplexity
    ) {}
}