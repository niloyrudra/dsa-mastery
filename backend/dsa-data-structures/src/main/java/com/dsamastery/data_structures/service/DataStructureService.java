package com.dsamastery.data_structures.service;

import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.repository.DataStructureRepository;
import com.dsamastery.common.enums.ContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Data structure-specific business logic: comparisons, alternatives, learning progression.
 * Complements the basic CRUD in dsa-content DataStructureService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataStructureService {

    private final DataStructureRepository dataStructureRepository;

    /**
     * Get all published data structures.
     */
    public List<DataStructure> getPublishedDataStructures() {
        return dataStructureRepository.findByStatusOrderByNameAsc(ContentStatus.PUBLISHED);
    }

    /**
     * Get data structure by slug with relations.
     */
    public Optional<DataStructure> getDataStructureBySlug(String slug) {
        return dataStructureRepository.findBySlug(slug);
    }

    /**
     * Get alternative data structures for a given use case.
     * Based on the 'alternatives' field and shared problem domains.
     */
    public List<DataStructure> getAlternatives(String dataStructureSlug) {
        return dataStructureRepository.findBySlug(dataStructureSlug)
            .map(ds -> {
                // Parse alternatives from the alternatives field (comma-separated slugs)
                List<String> altSlugs = parseAlternatives(ds.getAlternatives());
                
                // Also find data structures used in similar problems
                List<String> problemBasedAlts = ds.getProblems().stream()
                    .flatMap(p -> p.getDataStructures().stream())
                    .map(other -> other.getSlug())
                    .filter(s -> !s.equals(dataStructureSlug))
                    .distinct()
                    .collect(Collectors.toList());

                // Combine and fetch
                Set<String> allAlts = new LinkedHashSet<>();
                allAlts.addAll(altSlugs);
                allAlts.addAll(problemBasedAlts);

                return dataStructureRepository.findAllById(
                    dataStructureRepository.findByStatus(ContentStatus.PUBLISHED).stream()
                        .filter(d -> allAlts.contains(d.getSlug()))
                        .map(DataStructure::getId)
                        .collect(Collectors.toList())
                );
            })
            .orElse(List.of());
    }

    /**
     * Get data structures commonly used with the given one (co-occurrence in problems).
     */
    public List<DataStructure> getCommonlyPaired(String dataStructureSlug, int limit) {
        return dataStructureRepository.findBySlug(dataStructureSlug)
            .map(ds -> ds.getProblems().stream()
                .flatMap(p -> p.getDataStructures().stream())
                .filter(other -> !other.getSlug().equals(dataStructureSlug))
                .distinct()
                .limit(limit)
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    /**
     * Get data structure learning progression.
     */
    public DataStructureProgression getLearningProgression(String dataStructureSlug) {
        return dataStructureRepository.findBySlug(dataStructureSlug)
            .map(ds -> {
                // Prerequisites: simpler data structures used in same problems
                List<DataStructure> prereqs = dataStructureRepository.findByStatus(ContentStatus.PUBLISHED).stream()
                    .filter(other -> !other.getSlug().equals(dataStructureSlug))
                    .filter(other -> isPrerequisiteFor(other, ds))
                    .collect(Collectors.toList());

                // Next steps: more advanced structures that build on this
                List<DataStructure> nextSteps = dataStructureRepository.findByStatus(ContentStatus.PUBLISHED).stream()
                    .filter(other -> !other.getSlug().equals(dataStructureSlug))
                    .filter(other -> isPrerequisiteFor(ds, other))
                    .collect(Collectors.toList());

                return new DataStructureProgression(
                    ds.getSlug(),
                    ds.getName(),
                    prereqs.stream().map(d -> new PrerequisiteStep(d.getSlug(), d.getName(), "Foundational")).collect(Collectors.toList()),
                    nextSteps.stream().map(d -> new NextStep(d.getSlug(), d.getName(), "Advanced")).collect(Collectors.toList())
                );
            })
            .orElse(null);
    }

    /**
     * Compare data structures by operations, tradeoffs, and use cases.
     */
    public DataStructureComparison compareDataStructures(Collection<String> slugs) {
        List<DataStructure> structures = dataStructureRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .filter(d -> slugs.contains(d.getSlug()))
            .collect(Collectors.toList());

        return new DataStructureComparison(
            structures.stream().map(this::toComparisonEntry).collect(Collectors.toList())
        );
    }

    /**
     * Get data structure statistics.
     */
    public List<DataStructureStats> getDataStructureStatistics() {
        return dataStructureRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .map(d -> new DataStructureStats(
                d.getSlug(),
                d.getName(),
                d.getProblems().size(),
                d.getPatterns().size(),
                d.getAlgorithms().size(),
                d.getOperations() != null ? d.getOperations().size() : 0
            ))
            .sorted(Comparator.comparing(DataStructureStats::problemCount).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Find data structures by operation complexity.
     */
    public List<DataStructure> findByOperationComplexity(String operation, String complexity) {
        return dataStructureRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .filter(d -> d.getOperations() != null && 
                complexity.equalsIgnoreCase(d.getOperations().get(operation)))
            .collect(Collectors.toList());
    }

    /**
     * Get data structures used in a specific pattern category.
     */
    public List<DataStructure> getByPatternCategory(String patternCategory) {
        return dataStructureRepository.findByStatus(ContentStatus.PUBLISHED).stream()
            .filter(d -> d.getPatterns().stream()
                .anyMatch(p -> patternCategory.equalsIgnoreCase(p.getCategory())))
            .collect(Collectors.toList());
    }

    private boolean isPrerequisiteFor(DataStructure prereq, DataStructure target) {
        // Heuristic: prerequisite if it has fewer operations / simpler operations
        // and is used in subset of the same problems
        if (prereq.getOperations() == null || target.getOperations() == null) return false;
        
        Set<String> prereqOps = new HashSet<>(prereq.getOperations().keySet());
        Set<String> targetOps = new HashSet<>(target.getOperations().keySet());
        
        // Target has all prereq operations plus more
        return targetOps.containsAll(prereqOps) && prereqOps.size() < targetOps.size();
    }

    private List<String> parseAlternatives(String alternatives) {
        if (alternatives == null || alternatives.isBlank()) return List.of();
        return Arrays.stream(alternatives.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }

    private DataStructureComparison.Entry toComparisonEntry(DataStructure d) {
        return new DataStructureComparison.Entry(
            d.getSlug(),
            d.getName(),
            d.getOperations(),
            d.getTradeoffs(),
            d.getAlternatives(),
            d.getWhyUsefulHere(),
            d.getProblems().size(),
            d.getPatterns().size(),
            d.getAlgorithms().size()
        );
    }

    // DTOs
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

    public record DataStructureProgression(
        String slug,
        String name,
        List<PrerequisiteStep> prerequisites,
        List<NextStep> nextSteps
    ) {}

    public record DataStructureComparison(
        List<Entry> entries
    ) {
        public record Entry(
            String slug,
            String name,
            Map<String, String> operations,
            String tradeoffs,
            String alternatives,
            String whyUsefulHere,
            int problemCount,
            int patternCount,
            int algorithmCount
        ) {}
    }

    public record DataStructureStats(
        String slug,
        String name,
        int problemCount,
        int patternCount,
        int algorithmCount,
        int operationCount
    ) {}
}