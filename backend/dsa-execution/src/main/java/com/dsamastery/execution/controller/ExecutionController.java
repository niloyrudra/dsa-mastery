package com.dsamastery.execution.controller;

import com.dsamastery.execution.dto.ExecutionRequest;
import com.dsamastery.execution.dto.ExecutionResponse;
import com.dsamastery.execution.entity.ExecutionAttempt;
import com.dsamastery.execution.service.ExecutionService;
import com.dsamastery.common.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/execution")
@RequiredArgsConstructor
public class ExecutionController {

    private final ExecutionService executionService;

    @PostMapping("/jobs")
    public ResponseEntity<ExecutionResponse> submitExecution(@RequestBody ExecutionRequest request) {
        Language language = request.getLanguage() != null ? request.getLanguage() : Language.TYPESCRIPT;
        ExecutionAttempt attempt = executionService.submitExecution(
            request.getProblemId(), 
            language, 
            request.getCode(), 
            request.getTestCaseNames()
        );
        return ResponseEntity.ok(toResponse(attempt));
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<ExecutionResponse> getJob(@PathVariable UUID id) {
        return executionService.getAttempt(id)
            .map(this::toResponse)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/jobs/{id}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamJob(@PathVariable UUID id) {
        // Poll the job status and emit SSE events until completed
        return Flux.interval(Duration.ofSeconds(1))
            .flatMap(tick -> Mono.fromCallable(() -> executionService.getAttempt(id)))
            .filter(attempt -> attempt.isPresent())
            .map(attempt -> attempt.get())
            .takeUntil(attempt -> attempt.getStatus().isTerminal())
            .map(this::toResponse)
            .map(response -> "data: " + response.toString() + "\n\n")
            .concatWith(Mono.just("event: complete\ndata: done\n\n"));
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<ExecutionResponse>> getHistory(
            @RequestParam(required = false) UUID problemId,
            @RequestParam(defaultValue = "50") int limit) {
        
        List<ExecutionAttempt> attempts = problemId != null
            ? executionService.getHistory(problemId)
            : executionService.getAllHistory();
        
        return ResponseEntity.ok(attempts.stream()
            .limit(limit)
            .map(this::toResponse)
            .collect(Collectors.toList()));
    }

    @GetMapping("/languages")
    public ResponseEntity<List<String>> getSupportedLanguages() {
        return ResponseEntity.ok(List.of("TYPESCRIPT", "JAVASCRIPT", "PYTHON"));
    }

    private ExecutionResponse toResponse(ExecutionAttempt attempt) {
        List<ExecutionResponse.TestResult> testResults = null;
        if (attempt.getTestResults() != null) {
            testResults = attempt.getTestResults().stream()
                .map(tr -> ExecutionResponse.TestResult.builder()
                    .name(tr.getName())
                    .passed(tr.isPassed())
                    .input(tr.getInput())
                    .expected(tr.getExpected())
                    .error(tr.getError())
                    .durationMs(tr.getDurationMs())
                    .build())
                .collect(Collectors.toList());
        }

        return ExecutionResponse.builder()
            .id(attempt.getId())
            .problemId(attempt.getProblemId())
            .language(attempt.getLanguage().name())
            .status(attempt.getStatus().name())
            .passedTests(attempt.getPassedTests())
            .totalTests(attempt.getTotalTests())
            .executionTimeMs(attempt.getExecutionTimeMs())
            .memoryBytes(attempt.getMemoryBytes())
            .output(attempt.getOutput())
            .error(attempt.getError())
            .attemptedAt(attempt.getAttemptedAt())
            .completedAt(attempt.getCompletedAt())
            .testResults(testResults)
            .build();
    }
}