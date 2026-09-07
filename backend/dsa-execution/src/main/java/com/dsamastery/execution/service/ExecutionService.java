package com.dsamastery.execution.service;

import com.dsamastery.execution.entity.ExecutionAttempt;
import com.dsamastery.execution.entity.ExecutionResult;
import com.dsamastery.execution.repository.ExecutionAttemptRepository;
import com.dsamastery.common.enums.ExecutionStatus;
import com.dsamastery.common.enums.Language;
import com.dsamastery.content.entity.TestCase;
import com.dsamastery.content.service.TestCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ExecutionService {
    
    private final ExecutionAttemptRepository attemptRepository;
    private final TestCaseService testCaseService;
    private final CodeRunnerService codeRunnerService;
    
    @Transactional
    public ExecutionAttempt submitExecution(UUID problemId, Language language, String code, List<String> testCaseNames) {
        List<TestCase> testCases = testCaseNames != null && !testCaseNames.isEmpty()
            ? testCaseNames.stream()
                .flatMap(name -> testCaseService.getTestCasesByProblem(problemId).stream()
                    .filter(tc -> tc.getName().equals(name)))
                .toList()
            : testCaseService.getVisibleTestCases(problemId);
        
        ExecutionAttempt attempt = ExecutionAttempt.builder()
            .problemId(problemId)
            .language(language)
            .code(code)
            .totalTests(testCases.size())
            .status(ExecutionStatus.QUEUED)
            .build();
        
        attempt = attemptRepository.save(attempt);
        
        // Execute asynchronously
        executeAsync(attempt.getId(), testCases);
        
        return attempt;
    }
    
    @Async("executionExecutor")
    @Transactional
    public void executeAsync(UUID attemptId, List<TestCase> testCases) {
        Optional<ExecutionAttempt> attemptOpt = attemptRepository.findById(attemptId);
        if (attemptOpt.isEmpty()) return;
        
        ExecutionAttempt attempt = attemptOpt.get();
        attempt.setStatus(ExecutionStatus.RUNNING);
        attemptRepository.save(attempt);
        
        try {
            ExecutionResult result = codeRunnerService.run(attempt.getLanguage(), attempt.getCode(), testCases);
            
            attempt.setStatus(result.status());
            attempt.setPassedTests(result.passedTests());
            attempt.setTotalTests(result.totalTests());
            attempt.setExecutionTimeMs(result.executionTimeMs());
            attempt.setMemoryBytes(result.memoryBytes());
            attempt.setOutput(result.output());
            attempt.setError(result.error());
            attempt.setCompletedAt(Instant.now());
            
        } catch (Exception e) {
            attempt.setStatus(ExecutionStatus.ERROR);
            attempt.setError(e.getMessage());
            attempt.setCompletedAt(Instant.now());
        }
        
        attemptRepository.save(attempt);
    }
    
    @Transactional(readOnly = true)
    public Optional<ExecutionAttempt> getAttempt(UUID attemptId) {
        return attemptRepository.findById(attemptId);
    }
    
    @Transactional(readOnly = true)
    public List<ExecutionAttempt> getHistory(UUID problemId) {
        return attemptRepository.findByProblemIdOrderByAttemptedAtDesc(problemId);
    }
}