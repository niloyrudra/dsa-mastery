package com.dsamastery.curriculum.service;

import com.dsamastery.curriculum.entity.CurriculumNode;
import com.dsamastery.curriculum.repository.CurriculumNodeRepository;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.entity.Concept;
import com.dsamastery.content.service.ProblemService;
import com.dsamastery.content.service.PatternService;
import com.dsamastery.content.service.AlgorithmService;
import com.dsamastery.content.service.ConceptService;
import com.dsamastery.mastery.entity.MasteryRecord;
import com.dsamastery.mastery.service.MasteryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CurriculumService {
    
    private final CurriculumNodeRepository curriculumNodeRepository;
    private final ProblemService problemService;
    private final PatternService patternService;
    private final AlgorithmService algorithmService;
    private final ConceptService conceptService;
    private final MasteryService masteryService;
    
    public Optional<CurriculumNode> getRoot() {
        return curriculumNodeRepository.findRootNodes().stream().findFirst();
    }
    
    public Optional<CurriculumNode> getNodeByProblem(UUID problemId) {
        return curriculumNodeRepository.findByProblemId(problemId);
    }
    
    public Optional<LearningPath> getLearningPath(String problemSlug) {
        return problemService.getProblemBySlug(problemSlug)
            .map(problem -> buildLearningPath(problem));
    }
    
    private LearningPath buildLearningPath(Problem problem) {
        List<Prerequisite> prerequisites = problem.getPrerequisites().stream()
            .map(c -> new Prerequisite(
                c.getSlug(),
                c.getName(),
                "CONCEPT",
                getMasteryStatus("CONCEPT", c.getId())
            ))
            .toList();
        
        // Add pattern prerequisites
        problem.getPatterns().forEach(p -> {
            prerequisites.add(new Prerequisite(
                p.getSlug(),
                p.getName(),
                "PATTERN",
                getMasteryStatus("PATTERN", p.getId())
            ));
        });
        
        // Next problems - problems sharing patterns
        List<NextProblem> nextProblems = problem.getRelatedProblems().stream()
            .map(p -> new NextProblem(p.getSlug(), p.getTitle(), p.getDifficulty().name()))
            .toList();
        
        return new LearningPath(problem.getSlug(), prerequisites, nextProblems);
    }
    
    private String getMasteryStatus(String entityType, UUID entityId) {
        return masteryService.getMastery(entityType, entityId)
            .map(MasteryRecord::getStatus)
            .map(Enum::name)
            .orElse("NOT_STARTED");
    }
    
    public record LearningPath(
        String targetProblem,
        List<Prerequisite> prerequisites,
        List<NextProblem> nextProblems
    ) {}
    
    public record Prerequisite(
        String slug,
        String name,
        String type,
        String masteryStatus
    ) {}
    
    public record NextProblem(
        String slug,
        String title,
        String difficulty
    ) {}
}