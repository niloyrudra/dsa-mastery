package com.dsamastery.content.repository;

import com.dsamastery.content.entity.TestCase;
import com.dsamastery.common.enums.TestCaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, UUID> {
    
    List<TestCase> findByProblemIdOrderByTypeAscNameAsc(UUID problemId);
    
    List<TestCase> findByVariationId(UUID variationId);
    
    List<TestCase> findByProblemIdAndType(UUID problemId, TestCaseType type);
    
    List<TestCase> findByProblemIdAndIsHiddenFalse(UUID problemId);
}