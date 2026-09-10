package com.dsamastery.execution.service;

import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Solution;
import com.dsamastery.content.entity.TestCase;
import com.dsamastery.content.repository.ProblemRepository;
import com.dsamastery.content.repository.SolutionRepository;
import com.dsamastery.content.repository.TestCaseRepository;
import com.dsamastery.common.enums.Approach;
import com.dsamastery.common.enums.Language;
import com.dsamastery.execution.entity.ExecutionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Validates all solutions in the database by running them against their test cases.
 * Can be run on-demand or scheduled (e.g., nightly).
 * Produces a validation report with pass/fail status for each solution.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContentValidationService {

    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;
    private final TestCaseRepository testCaseRepository;
    private final CodeRunnerService codeRunnerService;
    private final ExecutionService executionService;

    /**
     * Validate all published problems' solutions.
     * Returns a validation report.
     */
    @Transactional(readOnly = true)
    public ValidationReport validateAllSolutions() {
        ValidationReport report = new ValidationReport();
        report.setStartedAt(Instant.now());

        List<Problem> problems = problemRepository.findByStatusOrderByOrderIndexAsc(
            com.dsamastery.common.enums.ContentStatus.PUBLISHED);

        log.info("Starting validation of {} problems", problems.size());

        for (Problem problem : problems) {
            try {
                ProblemValidationResult problemResult = validateProblem(problem);
                report.addProblemResult(problemResult);
                
                if (problemResult.isAllSolutionsValid()) {
                    report.incrementPassed();
                } else {
                    report.incrementFailed();
                }
            } catch (Exception e) {
                log.error("Error validating problem {}: {}", problem.getSlug(), e.getMessage());
                report.addError(problem.getSlug(), e.getMessage());
            }
        }

        report.setCompletedAt(Instant.now());
        report.setDurationMs(report.getCompletedAt().toEpochMilli() - report.getStartedAt().toEpochMilli());
        
        log.info("Validation completed: {} passed, {} failed, {} errors", 
            report.getPassed(), report.getFailed(), report.getErrors().size());
        
        return report;
    }

    /**
     * Validate a single problem's solutions.
     */
    public ProblemValidationResult validateProblem(Problem problem) {
        ProblemValidationResult result = new ProblemValidationResult(problem.getSlug(), problem.getTitle());
        
        List<TestCase> testCases = testCaseRepository.findByProblemIdAndIsHiddenFalse(problem.getId());
        if (testCases.isEmpty()) {
            result.addError("No visible test cases found");
            return result;
        }

        // Group solutions by approach
        Map<Approach, List<Solution>> solutionsByApproach = problem.getSolutions().stream()
            .collect(Collectors.groupingBy(Solution::getApproach));

        for (Map.Entry<Approach, List<Solution>> entry : solutionsByApproach.entrySet()) {
            Approach approach = entry.getKey();
            for (Solution solution : entry.getValue()) {
                SolutionValidationResult solResult = validateSolution(
                    problem, solution, testCases);
                result.addSolutionResult(solResult);
            }
        }

        return result;
    }

    /**
     * Validate a single solution against test cases.
     */
    private SolutionValidationResult validateSolution(
            Problem problem, Solution solution, List<TestCase> testCases) {
        
        SolutionValidationResult result = new SolutionValidationResult(
            solution.getId().toString(),
            solution.getName(),
            solution.getApproach().name(),
            solution.getLanguage().name()
        );

        try {
            // Run solution in each supported language
            for (Language language : Language.values()) {
                LanguageValidationResult langResult = validateSolutionInLanguage(
                    solution, testCases, language);
                result.addLanguageResult(langResult);
                
                if (!langResult.isPassed()) {
                    result.setPassed(false);
                }
            }
        } catch (Exception e) {
            log.error("Error validating solution {}: {}", solution.getId(), e.getMessage());
            result.addError(e.getMessage());
            result.setPassed(false);
        }

        return result;
    }

    /**
     * Run a solution in a specific language against test cases.
     */
    private LanguageValidationResult validateSolutionInLanguage(
            Solution solution, List<TestCase> testCases, Language language) {
        
        LanguageValidationResult result = new LanguageValidationResult(language.name());

        try {
            ExecutionResult execResult = codeRunnerService.run(
                language, solution.getCode(), testCases);
            
            result.setPassed(execResult.getStatus().name().equals("PASSED"));
            result.setPassedTests(execResult.getPassedTests());
            result.setTotalTests(execResult.getTotalTests());
            result.setExecutionTimeMs(execResult.getExecutionTimeMs());
            result.setMemoryBytes(execResult.getMemoryBytes());
            result.setTestResults(execResult.getTestResults());
            
            if (!result.isPassed()) {
                result.setError(execResult.getError());
                result.setOutput(execResult.getOutput());
            }
        } catch (Exception e) {
            result.setPassed(false);
            result.setError("Execution failed: " + e.getMessage());
        }

        return result;
    }

    /**
     * Validate a specific problem by slug.
     */
    public ProblemValidationResult validateProblemBySlug(String slug) {
        return problemRepository.findBySlug(slug)
            .map(this::validateProblem)
            .orElseThrow(() -> new IllegalArgumentException("Problem not found: " + slug));
    }

    // Scheduled validation (disabled by default - enable via property)
    @Scheduled(cron = "${validation.schedule:0 2 * * *}") // Daily at 2 AM
    public void scheduledValidation() {
        log.info("Running scheduled content validation");
        ValidationReport report = validateAllSolutions();
        
        // Could send notification, store report, etc.
        if (report.getFailed() > 0 || !report.getErrors().isEmpty()) {
            log.warn("Scheduled validation found issues: {} failed, {} errors", 
                report.getFailed(), report.getErrors().size());
        }
    }

    // DTOs
    public static class ValidationReport {
        private Instant startedAt;
        private Instant completedAt;
        private long durationMs;
        private int passed = 0;
        private int failed = 0;
        private final List<ProblemValidationResult> problemResults = new ArrayList<>();
        private final List<ValidationError> errors = new ArrayList<>();

        public void addProblemResult(ProblemValidationResult result) {
            problemResults.add(result);
        }

        public void addError(String problemSlug, String error) {
            errors.add(new ValidationError(problemSlug, error));
        }

        public void incrementPassed() { passed++; }
        public void incrementFailed() { failed++; }

        // Getters
        public Instant getStartedAt() { return startedAt; }
        public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
        public Instant getCompletedAt() { return completedAt; }
        public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
        public long getDurationMs() { return durationMs; }
        public void setDurationMs(long durationMs) { this.durationMs = durationMs; }
        public int getPassed() { return passed; }
        public int getFailed() { return failed; }
        public List<ProblemValidationResult> getProblemResults() { return problemResults; }
        public List<ValidationError> getErrors() { return errors; }
    }

    public static class ValidationError {
        private final String problemSlug;
        private final String error;

        public ValidationError(String problemSlug, String error) {
            this.problemSlug = problemSlug;
            this.error = error;
        }

        public String getProblemSlug() { return problemSlug; }
        public String getError() { return error; }
    }

    public static class ProblemValidationResult {
        private final String problemSlug;
        private final String problemTitle;
        private boolean allPassed = true;
        private final List<SolutionValidationResult> solutionResults = new ArrayList<>();
        private final List<String> errors = new ArrayList<>();

        public ProblemValidationResult(String problemSlug, String problemTitle) {
            this.problemSlug = problemSlug;
            this.problemTitle = problemTitle;
        }

        public void addSolutionResult(SolutionValidationResult result) {
            solutionResults.add(result);
            if (!result.isPassed()) {
                allPassed = false;
            }
        }

        public void addError(String error) {
            errors.add(error);
            allPassed = false;
        }

        public boolean isAllSolutionsValid() { return allPassed; }

        // Getters
        public String getProblemSlug() { return problemSlug; }
        public String getProblemTitle() { return problemTitle; }
        public boolean isPassed() { return allPassed; }
        public List<SolutionValidationResult> getSolutionResults() { return solutionResults; }
        public List<String> getErrors() { return errors; }
    }

    public static class SolutionValidationResult {
        private final String solutionId;
        private final String solutionName;
        private final String approach;
        private final String language;
        private boolean passed = true;
        private final List<LanguageValidationResult> languageResults = new ArrayList<>();
        private final List<String> errors = new ArrayList<>();

        public SolutionValidationResult(String solutionId, String solutionName, 
                                       String approach, String language) {
            this.solutionId = solutionId;
            this.solutionName = solutionName;
            this.approach = approach;
            this.language = language;
        }

        public void addLanguageResult(LanguageValidationResult result) {
            languageResults.add(result);
            if (!result.isPassed()) {
                passed = false;
            }
        }

        public void addError(String error) {
            errors.add(error);
            passed = false;
        }

        public void setPassed(boolean passed) { this.passed = passed; }
        public boolean isPassed() { return passed; }

        // Getters
        public String getSolutionId() { return solutionId; }
        public String getSolutionName() { return solutionName; }
        public String getApproach() { return approach; }
        public String getLanguage() { return language; }
        public List<LanguageValidationResult> getLanguageResults() { return languageResults; }
        public List<String> getErrors() { return errors; }
    }

    public static class LanguageValidationResult {
        private final String language;
        private boolean passed = false;
        private int passedTests = 0;
        private int totalTests = 0;
        private long executionTimeMs = 0;
        private long memoryBytes = 0;
        private List<ExecutionResult.TestResult> testResults;
        private String error;
        private String output;

        public LanguageValidationResult(String language) {
            this.language = language;
        }

        // Getters and setters
        public String getLanguage() { return language; }
        public boolean isPassed() { return passed; }
        public void setPassed(boolean passed) { this.passed = passed; }
        public int getPassedTests() { return passedTests; }
        public void setPassedTests(int passedTests) { this.passedTests = passedTests; }
        public int getTotalTests() { return totalTests; }
        public void setTotalTests(int totalTests) { this.totalTests = totalTests; }
        public long getExecutionTimeMs() { return executionTimeMs; }
        public void setExecutionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; }
        public long getMemoryBytes() { return memoryBytes; }
        public void setMemoryBytes(long memoryBytes) { this.memoryBytes = memoryBytes; }
        public List<ExecutionResult.TestResult> getTestResults() { return testResults; }
        public void setTestResults(List<ExecutionResult.TestResult> testResults) { this.testResults = testResults; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
        public String getOutput() { return output; }
        public void setOutput(String output) { this.output = output; }
    }
}