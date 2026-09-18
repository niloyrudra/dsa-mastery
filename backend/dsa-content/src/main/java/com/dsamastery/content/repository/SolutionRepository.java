package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Solution;
import com.dsamastery.common.enums.Approach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {
    
    List<Solution> findByProblemIdOrderByApproachAsc(UUID problemId);
    
    List<Solution> findByProblemIdAndApproach(UUID problemId, Approach approach);
    
    boolean existsByProblemIdAndApproach(UUID problemId, Approach approach);
}