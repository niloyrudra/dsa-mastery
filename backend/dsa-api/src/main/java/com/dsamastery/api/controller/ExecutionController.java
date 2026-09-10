package com.dsamastery.api.controller;

import com.dsamastery.api.dto.ExecutionJobResponse;
import com.dsamastery.api.dto.ExecutionRequest;
import com.dsamastery.api.dto.ExecutionResultDto;
import com.dsamastery.api.dto.TestResultDto;
import com.dsamastery.execution.entity.ExecutionAttempt;
import com.dsamastery.execution.service.ExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/execution")
@RequiredArgsConstructor
public class ExecutionController {
    
    private final ExecutionService executionService;
    
    @PostMapping("/jobs")
    public ResponseEntity<ExecutionJobResponse> submit(@RequestBody ExecutionRequest request) {
        ExecutionAttempt attempt = executionService.submitExecution(
            request.getProblemId(),
            request.getLanguage(),
            request.getCode(),
            request.getTestCases()
        );
        
        return ResponseEntity.accepted().body(new ExecutionJobResponse(
            attempt.getId(),
            attempt.getStatus(),
            attempt.getAttemptedAt().toString()
        ));
    }
    
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<ExecutionResultDto> getJob(@PathVariable UUID jobId) {
        return executionService.getAttempt(jobId)
            .map(this::toResultDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<ExecutionResultDto>> getHistory(
            @RequestParam(required = false) UUID problemId) {
        
        List<ExecutionAttempt> attempts = problemId != null
            ? executionService.getHistory(problemId)
            : executionService.getAllHistory();
        
        return ResponseEntity.ok(attempts.stream()
            .map(this::toResultDto)
            .collect(Collectors.toList()));
    }
    
    private ExecutionResultDto toResultDto(ExecutionAttempt attempt) {
        List<TestResultDto> testResults = attempt.getTestResults() != null
            ? attempt.getTestResults().stream()
                .map(tr -> TestResultDto.builder()
                    .name(tr.getName())
                    .passed(tr.isPassed())
                    .input(tr.getInput() != null ? tr.getInput().toString() : null)
                    .expected(tr.getExpected() != null ? tr.getExpected().toString() : null)
                    .actual(null) // Not captured in current implementation
                    .durationMs(tr.getDurationMs())
                    .build())
                .collect(Collectors.toList())
            : List.of();
        
        return ExecutionResultDto.builder()
            .jobId(attempt.getId())
            .status(attempt.getStatus())
            .passedTests(attempt.getPassedTests())
            .totalTests(attempt.getTotalTests())
            .executionTimeMs(attempt.getExecutionTimeMs())
            .memoryBytes(attempt.getMemoryBytes())
            .output(attempt.getOutput())
            .error(attempt.getError())
            .testResults(testResults)
            .completedAt(attempt.getCompletedAt() != null ? attempt.getCompletedAt().toString() : null)
            .build();
    }
}