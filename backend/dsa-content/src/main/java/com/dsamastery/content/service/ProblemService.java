package com.dsamastery.content.service;

import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.repository.ProblemRepository;
import com.dsamastery.common.enums.ContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {
    
    private final ProblemRepository problemRepository;
    
    public List<Problem> getPublishedProblems() {
        return problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED);
    }
    
    public Optional<Problem> getProblemBySlug(String slug) {
        Optional<Problem> problemOpt = problemRepository.findBySlugWithRelations(slug);
        if (problemOpt.isEmpty()) {
            return problemOpt;
        }
        
        Problem problem = problemOpt.get();
        
        // Fetch remaining relations separately to avoid MultipleBagFetchException
        Optional<Problem> detailsOpt = problemRepository.findBySlugWithDetails(slug);
        if (detailsOpt.isPresent()) {
            Problem details = detailsOpt.get();
            
            // Merge the collections
            if (details.getVariations() != null) {
                problem.getVariations().addAll(details.getVariations());
            }
            if (details.getSolutions() != null) {
                problem.getSolutions().addAll(details.getSolutions());
            }
            if (details.getTestCases() != null) {
                problem.getTestCases().addAll(details.getTestCases());
            }
            if (details.getLearningObjectives() != null) {
                problem.getLearningObjectives().addAll(details.getLearningObjectives());
            }
            if (details.getReferences() != null) {
                problem.getReferences().addAll(details.getReferences());
            }
            if (details.getRelatedProblems() != null) {
                problem.getRelatedProblems().addAll(details.getRelatedProblems());
            }
            if (details.getPrerequisites() != null) {
                problem.getPrerequisites().addAll(details.getPrerequisites());
            }
        }
        
        return Optional.of(problem);
    }
    
    public Optional<Problem> getProblemById(UUID id) {
        return problemRepository.findById(id);
    }
    
    public List<Problem> getProblemsByPattern(String patternSlug) {
        return problemRepository.findByPatternsSlug(patternSlug);
    }
    
    public List<Problem> getProblemsByAlgorithm(String algorithmSlug) {
        return problemRepository.findByAlgorithmsSlug(algorithmSlug);
    }
    
    public List<Problem> getProblemsByDataStructure(String dataStructureSlug) {
        return problemRepository.findByDataStructuresSlug(dataStructureSlug);
    }
}