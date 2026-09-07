package com.dsamastery.content.service;

import com.dsamastery.content.entity.TestCase;
import com.dsamastery.content.repository.TestCaseRepository;
import com.dsamastery.common.enums.TestCaseType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestCaseService {
    
    private final TestCaseRepository testCaseRepository;
    
    public List<TestCase> getTestCasesByProblem(UUID problemId) {
        return testCaseRepository.findByProblemIdOrderByTypeAscNameAsc(problemId);
    }
    
    public List<TestCase> getVisibleTestCases(UUID problemId) {
        return testCaseRepository.findByProblemIdAndIsHiddenFalse(problemId);
    }
    
    public List<TestCase> getTestCasesByType(UUID problemId, TestCaseType type) {
        return testCaseRepository.findByProblemIdAndType(problemId, type);
    }
    
    public List<TestCase> getTestCasesByVariation(UUID variationId) {
        return testCaseRepository.findByVariationId(variationId);
    }
}