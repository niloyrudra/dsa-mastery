package com.dsamastery.problems.service;

import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.ProblemVariation;
import com.dsamastery.content.repository.ProblemRepository;
import com.dsamastery.common.enums.ContentStatus;
import com.dsamastery.common.enums.Difficulty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Problem-specific business logic: progress tracking, recommendations, analysis.
 * Complements the basic CRUD in dsa-content ProblemService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {

    private final ProblemRepository problemRepository;

    /**
     * Get all published problems ordered by curriculum order.
     */
    public List<Problem> getPublishedProblems() {
        return problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED);
    }

    /**
     * Get problem by slug with all relations loaded.
     */
    public Optional<Problem> getProblemBySlug(String slug) {
        return problemRepository.findBySlugWithAllRelations(slug);
    }

    /**
     * Get problems by difficulty.
     */
    public List<Problem> getProblemsByDifficulty(Difficulty difficulty) {
        return problemRepository.findByStatusOrderByOrderIndexAsc(ContentStatus.PUBLISHED).stream()
            .filter(p -> p.getDifficulty() == difficulty)
            .collect(Collectors.toList());
    }

    /**
     * Get next recommended problem for a learner based on completed problems.
     */
    public Optional<Problem> getNextRecommendedProblem(Set<String> completedSlugs, Optional<Difficulty> preferredDifficulty) {
        List<Problem> candidates = problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED).stream()
            .filter(p -> !completedSlugs.contains(p.getSlug()))
            .filter(p -> preferredDifficulty.isEmpty() || p.getDifficulty() == preferredDifficulty.get())
            .collect(Collectors.toList());

        if (candidates.isEmpty()) return Optional.empty();

        // Score candidates by prerequisite completion
        return candidates.stream()
            .max(Comparator.comparingDouble(p -> calculateReadinessScore(p, completedSlugs)));
    }

    /**
     * Get problem variations with their differences highlighted.
     */
    public List<ProblemVariationInfo> getVariationAnalysis(String problemSlug) {
        return problemRepository.findBySlugWithAllRelations(problemSlug)
            .map(p -> p.getVariations().stream()
                .map(v -> new ProblemVariationInfo(
                    v.getId().toString(),
                    v.getName(),
                    v.getWhatChanged(),
                    v.getOriginalApproachWorks(),
                    v.getWhatBreaks(),
                    v.getNewConstraintMatters(),
                    v.getComplexityChanges(),
                    v.getDataStructureChanges(),
                    v.getAlgorithmChanges()
                ))
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    /**
     * Get problems that are prerequisites for the given problem.
     */
    public List<ProblemPrerequisite> getPrerequisites(String problemSlug) {
        return problemRepository.findBySlugWithAllRelations(problemSlug)
            .map(p -> p.getPrerequisites().stream()
                .map(prereq -> new ProblemPrerequisite(
                    prereq.getSlug(),
                    prereq.getName(),
                    prereq.getDescription()
                ))
                .collect(Collectors.toList()))
            .orElse(List.of());
    }

    /**
     * Get problems that build on this problem (reverse prerequisites).
     */
    public List<Problem> getDependentProblems(String problemSlug) {
        return problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED).stream()
            .filter(p -> p.getPrerequisites().stream()
                .anyMatch(prereq -> prereq.getSlug().equals(problemSlug)))
            .collect(Collectors.toList());
    }

    /**
     * Get similar problems based on shared patterns, algorithms, data structures.
     */
    public List<SimilarProblem> getSimilarProblems(String problemSlug, int limit) {
        return problemRepository.findBySlugWithAllRelations(problemSlug)
            .map(base -> {
                Set<String> basePatterns = base.getPatterns().stream().map(p -> p.getSlug()).collect(Collectors.toSet());
                Set<String> baseAlgorithms = base.getAlgorithms().stream().map(a -> a.getSlug()).collect(Collectors.toSet());
                Set<String> baseDS = base.getDataStructures().stream().map(ds -> ds.getSlug()).collect(Collectors.toSet());

                return problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED).stream()
                    .filter(p -> !p.getSlug().equals(problemSlug))
                    .map(p -> {
                        Set<String> pPatterns = p.getPatterns().stream().map(pat -> pat.getSlug()).collect(Collectors.toSet());
                        Set<String> pAlgorithms = p.getAlgorithms().stream().map(a -> a.getSlug()).collect(Collectors.toSet());
                        Set<String> pDS = p.getDataStructures().stream().map(ds -> ds.getSlug()).collect(Collectors.toSet());

                        double similarity = (jaccard(basePatterns, pPatterns) 
                            + jaccard(baseAlgorithms, pAlgorithms) 
                            + jaccard(baseDS, pDS)) / 3.0;

                        return new SimilarProblem(
                            p.getSlug(),
                            p.getTitle(),
                            p.getDifficulty().name(),
                            similarity,
                            getSharedConcepts(base, p)
                        );
                    })
                    .filter(s -> s.similarity() > 0)
                    .sorted(Comparator.comparing(SimilarProblem::similarity).reversed())
                    .limit(limit)
                    .collect(Collectors.toList());
            })
            .orElse(List.of());
    }

    /**
     * Get problem statistics by difficulty.
     */
    public Map<Difficulty, Long> getProblemCountByDifficulty() {
        return problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED).stream()
            .collect(Collectors.groupingBy(Problem::getDifficulty, Collectors.counting()));
    }

    /**
     * Get problem statistics by pattern.
     */
    public List<ProblemPatternStats> getProblemStatsByPattern() {
        return problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED).stream()
            .flatMap(p -> p.getPatterns().stream()
                .map(pat -> new AbstractMap.SimpleEntry<>(pat.getSlug(), pat.getName())))
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.counting()))
            .entrySet().stream()
            .map(e -> new ProblemPatternStats(e.getKey(), 
                problemRepository.findByPatternsSlug(e.getKey()).get(0).getPatterns().stream()
                    .filter(p -> p.getSlug().equals(e.getKey()))
                    .findFirst()
                    .map(p -> p.getName())
                    .orElse(e.getKey()),
                e.getValue()))
            .sorted(Comparator.comparing(ProblemPatternStats::count).reversed())
            .collect(Collectors.toList());
    }

    /**
     * Get the learning path to a target problem (all prerequisites in order).
     */
    public List<Problem> getLearningPath(String targetSlug) {
        Set<String> visited = new HashSet<>();
        List<Problem> path = new ArrayList<>();
        buildLearningPath(targetSlug, visited, path);
        Collections.reverse(path);
        return path;
    }

    private void buildLearningPath(String slug, Set<String> visited, List<Problem> path) {
        if (visited.contains(slug)) return;
        visited.add(slug);
        
        problemRepository.findBySlugWithAllRelations(slug).ifPresent(p -> {
            for (var prereq : p.getPrerequisites()) {
                buildLearningPath(prereq.getSlug(), visited, path);
            }
            path.add(p);
        });
    }

    private double calculateReadinessScore(Problem p, Set<String> completedSlugs) {
        if (p.getPrerequisites() == null || p.getPrerequisites().isEmpty()) return 1.0;
        
        long completedPrereqs = p.getPrerequisites().stream()
            .filter(prereq -> completedSlugs.contains(prereq.getSlug()))
            .count();
        
        return (double) completedPrereqs / p.getPrerequisites().size();
    }

    private double jaccard(Set<String> a, Set<String> b) {
        if (a.isEmpty() && b.isEmpty()) return 1.0;
        if (a.isEmpty() || b.isEmpty()) return 0.0;
        
        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        Set<String> union = new HashSet<>(a);
        union.addAll(b);
        
        return (double) intersection.size() / union.size();
    }

    private List<String> getSharedConcepts(Problem a, Problem b) {
        List<String> shared = new ArrayList<>();
        Set<String> aPatterns = a.getPatterns().stream().map(p -> p.getSlug()).collect(Collectors.toSet());
        Set<String> bPatterns = b.getPatterns().stream().map(p -> p.getSlug()).collect(Collectors.toSet());
        aPatterns.retainAll(bPatterns);
        shared.addAll(aPatterns);
        
        Set<String> aAlgos = a.getAlgorithms().stream().map(a2 -> a2.getSlug()).collect(Collectors.toSet());
        Set<String> bAlgos = b.getAlgorithms().stream().map(a2 -> a2.getSlug()).collect(Collectors.toSet());
        aAlgos.retainAll(bAlgos);
        shared.addAll(aAlgos);
        
        Set<String> aDS = a.getDataStructures().stream().map(d -> d.getSlug()).collect(Collectors.toSet());
        Set<String> bDS = b.getDataStructures().stream().map(d -> d.getSlug()).collect(Collectors.toSet());
        aDS.retainAll(bDS);
        shared.addAll(aDS);
        
        return shared;
    }

    // DTOs
    public record ProblemVariationInfo(
        String slug,
        String name,
        String whatChanged,
        boolean originalApproachWorks,
        String whatBreaks,
        String newConstraintMatters,
        String complexityChanges,
        boolean dataStructureChanges,
        boolean algorithmChanges
    ) {}

    public record ProblemPrerequisite(
        String slug,
        String name,
        String description
    ) {}

    public record SimilarProblem(
        String slug,
        String title,
        String difficulty,
        double similarity,
        List<String> sharedConcepts
    ) {}

    public record ProblemPatternStats(
        String patternSlug,
        String patternName,
        long count
    ) {}
}