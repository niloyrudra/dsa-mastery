package com.dsamastery.content.repository;

import com.dsamastery.content.entity.LearningObjective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LearningObjectiveRepository extends JpaRepository<LearningObjective, UUID> {
    
    List<LearningObjective> findByProblemId(UUID problemId);
}