package com.dsamastery.content.service;

import com.dsamastery.content.entity.Solution;
import com.dsamastery.content.repository.SolutionRepository;
import com.dsamastery.common.enums.Approach;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SolutionService {
    
    private final SolutionRepository solutionRepository;
    
    public List<Solution> getSolutionsByProblem(UUID problemId) {
        return solutionRepository.findByProblemIdOrderByApproachAsc(problemId);
    }
    
    public List<Solution> getSolutionsByProblemAndApproach(UUID problemId, Approach approach) {
        return solutionRepository.findByProblemIdAndApproach(problemId, approach);
    }
    
    public Optional<Solution> getBestSolution(UUID problemId) {
        return solutionRepository.findByProblemIdAndApproach(problemId, Approach.BEST)
            .stream().findFirst();
    }
}