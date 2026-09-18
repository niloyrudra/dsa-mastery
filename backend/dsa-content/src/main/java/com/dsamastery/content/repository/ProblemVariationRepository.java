package com.dsamastery.content.repository;

import com.dsamastery.content.entity.ProblemVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProblemVariationRepository extends JpaRepository<ProblemVariation, UUID> {
    
    List<ProblemVariation> findByBaseProblemId(UUID problemId);
}