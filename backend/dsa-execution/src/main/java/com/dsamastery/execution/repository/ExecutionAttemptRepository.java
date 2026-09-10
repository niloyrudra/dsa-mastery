package com.dsamastery.execution.repository;

import com.dsamastery.execution.entity.ExecutionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExecutionAttemptRepository extends JpaRepository<ExecutionAttempt, UUID> {
    
    List<ExecutionAttempt> findByProblemIdOrderByAttemptedAtDesc(UUID problemId);
    
    List<ExecutionAttempt> findByUserIdOrderByAttemptedAtDesc(UUID userId);
    
    List<ExecutionAttempt> findByStatus(String status);
    
    List<ExecutionAttempt> findAllByOrderByAttemptedAtDesc();
}