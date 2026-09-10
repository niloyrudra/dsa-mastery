package com.dsamastery.content.service;

import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.repository.ProblemRepository;
import com.dsamastery.common.enums.ContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {
    
    private final ProblemRepository problemRepository;
    
    public List<Problem> getPublishedProblems() {
        return problemRepository.findPublishedOrdered(ContentStatus.PUBLISHED);
    }
    
    public Optional<Problem> getProblemBySlug(String slug) {
        return problemRepository.findBySlugWithAllRelations(slug);
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