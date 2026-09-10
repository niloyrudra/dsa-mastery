package com.dsamastery.execution.service;

import com.dsamastery.execution.entity.ExecutionResult;
import com.dsamastery.common.enums.ExecutionStatus;
import com.dsamastery.common.enums.Language;
import com.dsamastery.content.entity.TestCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeRunnerService {
    
    @Value("${docker.runner.typescript-image:dsa-runner-typescript:latest}")
    private String typescriptImage;
    
    @Value("${docker.runner.javascript-image:dsa-runner-javascript:latest}")
    private String javascriptImage;
    
    @Value("${docker.runner.python-image:dsa-runner-python:latest}")
    private String pythonImage;
    
    @Value("${docker.runner.timeout-seconds:30}")
    private int timeoutSeconds;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public ExecutionResult run(Language language, String code, List<TestCase> testCases) {
        Instant start = Instant.now();
        Path workspace = null;
        
        try {
            workspace = Files.createTempDirectory("dsa-run-");
            
            // Write solution file
            writeSolutionFile(workspace, language, code);
            
            // Write test file
            writeTestFile(workspace, language, testCases);
            
            // Write config files
            writeConfigFiles(workspace, language);
            
            // Run in Docker
            String image = getImage(language);
            List<String> cmd = buildDockerCommand(workspace.toString(), image, language);
            
            log.info("Running command: {}", String.join(" ", cmd));
            
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            // Wait with timeout
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            
            if (!finished) {
                process.destroyForcibly();
                return new ExecutionResult(
                    ExecutionStatus.TIMEOUT,
                    0, testCases.size(),
                    Duration.between(start, Instant.now()).toMillis(),
                    null, "Execution timed out after " + timeoutSeconds + "s", null,
                    List.of()
                );
            }
            
            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.exitValue();
            
            long execTime = Duration.between(start, Instant.now()).toMillis();
            
            return parseResult(language, output, exitCode, testCases.size(), execTime);
            
        } catch (Exception e) {
            log.error("Execution error", e);
            return new ExecutionResult(
                ExecutionStatus.ERROR,
                0, testCases.size(),
                Duration.between(start, Instant.now()).toMillis(),
                null, e.getMessage(), null,
                List.of()
            );
        } finally {
            if (workspace != null) {
                deleteRecursively(workspace);
            }
        }
    }
    
    private void writeSolutionFile(Path workspace, Language language, String code) throws IOException {
        String filename = switch (language) {
            case TYPESCRIPT -> "solution.ts";
            case JAVASCRIPT -> "solution.js";
            case PYTHON -> "solution.py";
        };
        Files.writeString(workspace.resolve(filename), code);
    }
    
    private void writeTestFile(Path workspace, Language language, List<TestCase> testCases) throws IOException {
        String testCode = generateTestCode(language, testCases);
        String filename = switch (language) {
            case TYPESCRIPT, JAVASCRIPT -> "solution.test.ts";
            case PYTHON -> "test_solution.py";
        };
        Files.writeString(workspace.resolve(filename), testCode);
    }
    
    private String generateTestCode(Language language, List<TestCase> testCases) {
        return switch (language) {
            case TYPESCRIPT, JAVASCRIPT -> generateVitestCode(testCases);
            case PYTHON -> generatePytestCode(testCases);
        };
    }
    
    private String generateVitestCode(List<TestCase> testCases) {
        StringBuilder sb = new StringBuilder();
        sb.append("import { describe, it, expect } from 'vitest';\n");
        sb.append("import { solution } from './solution';\n\n");
        sb.append("const testCases = [\n");
        
        for (TestCase tc : testCases) {
            sb.append("  { name: ").append(escapeJson(tc.getName())).append(", ");
            sb.append("input: ").append(toJson(tc.getInput())).append(", ");
            sb.append("expected: ").append(toJson(tc.getExpectedOutput())).append(" },\n");
        }
        sb.append("];\n\n");
        
        sb.append("describe('Solution', () => {\n");
        sb.append("  testCases.forEach(({ name, input, expected }) => {\n");
        sb.append("    it(name, () => {\n");
        sb.append("      const result = solution(...input);\n");
        sb.append("      expect(result).toEqual(expected);\n");
        sb.append("    });\n");
        sb.append("  });\n");
        sb.append("});\n");
        
        return sb.toString();
    }
    
    private String generatePytestCode(List<TestCase> testCases) {
        StringBuilder sb = new StringBuilder();
        sb.append("import pytest\n");
        sb.append("import json\n");
        sb.append("from solution import solution\n\n");
        sb.append("test_cases = [\n");
        
        for (TestCase tc : testCases) {
            sb.append("    (").append(escapeJson(tc.getName())).append(", ");
            sb.append(toJson(tc.getInput())).append(", ");
            sb.append(toJson(tc.getExpectedOutput())).append("),\n");
        }
        sb.append("]\n\n");
        
        sb.append("@pytest.mark.parametrize(\"name,input,expected\", test_cases)\n");
        sb.append("def test_solution(name, input, expected):\n");
        sb.append("    result = solution(*input)\n");
        sb.append("    assert result == expected, f\"{name}: expected {expected}, got {result}\"\n");
        
        return sb.toString();
    }
    
    private void writeConfigFiles(Path workspace, Language language) throws IOException {
        if (language == Language.TYPESCRIPT) {
            String tsconfig = """
                {
                  "compilerOptions": {
                    "target": "ES2020",
                    "module": "commonjs",
                    "strict": true,
                    "esModuleInterop": true,
                    "skipLibCheck": true,
                    "forceConsistentCasingInFileNames": true
                  },
                  "include": ["solution.ts", "solution.test.ts"]
                }
                """;
            Files.writeString(workspace.resolve("tsconfig.json"), tsconfig);
            
            String packageJson = """
                {
                  "name": "dsa-solution",
                  "version": "1.0.0",
                  "type": "module",
                  "scripts": {
                    "test": "vitest run --reporter=json"
                  },
                  "devDependencies": {
                    "vitest": "^2.0.0",
                    "typescript": "^5.0.0"
                  }
                }
                """;
            Files.writeString(workspace.resolve("package.json"), packageJson);
        } else if (language == Language.JAVASCRIPT) {
            String packageJson = """
                {
                  "name": "dsa-solution",
                  "version": "1.0.0",
                  "type": "module",
                  "scripts": {
                    "test": "vitest run --reporter=json"
                  },
                  "devDependencies": {
                    "vitest": "^2.0.0"
                  }
                }
                """;
            Files.writeString(workspace.resolve("package.json"), packageJson);
        } else if (language == Language.PYTHON) {
            String pyproject = """
                [tool.pytest.ini_options]
                json_report = true
                json_report_file = "pytest-report.json"
                """;
            Files.writeString(workspace.resolve("pyproject.toml"), pyproject);
        }
    }
    
    private String getImage(Language language) {
        return switch (language) {
            case TYPESCRIPT -> typescriptImage;
            case JAVASCRIPT -> javascriptImage;
            case PYTHON -> pythonImage;
        };
    }
    
    private List<String> buildDockerCommand(String workspace, String image, Language language) {
        String testCmd = switch (language) {
            case TYPESCRIPT, JAVASCRIPT -> "sh -c \"npm install && npx vitest run --reporter=json 2>&1\"";
            case PYTHON -> "sh -c \"python -m pytest --json-report --json-report-file=/tmp/report.json --tb=short -v 2>&1 && cat /tmp/report.json\"";
        };
        
        return List.of(
            "docker", "run", "--rm",
            "--cpus=1",
            "--memory=256m",
            "--memory-swap=256m",
            "--pids-limit=50",
            "--network=none",
            "--read-only",
            "--tmpfs=/workspace:exec,size=100m",
            "--tmpfs=/tmp:exec,size=50m",
            "--user=10000:10000",
            "-v", workspace + ":/workspace:ro",
            "-w", "/workspace",
            image,
            testCmd
        );
    }
    
    private ExecutionResult parseResult(Language language, String output, int exitCode, int totalTests, long execTime) {
        try {
            if (language == Language.TYPESCRIPT || language == Language.JAVASCRIPT) {
                return parseVitestResult(output, exitCode, totalTests, execTime);
            } else if (language == Language.PYTHON) {
                return parsePytestResult(output, exitCode, totalTests, execTime);
            }
        } catch (Exception e) {
            log.warn("Failed to parse test output, falling back to basic parsing: {}", e.getMessage());
        }
        
        // Fallback basic parsing
        return fallbackParse(output, exitCode, totalTests, execTime);
    }
    
    private ExecutionResult parseVitestResult(String output, int exitCode, int totalTests, long execTime) throws IOException {
        // Vitest JSON reporter outputs JSON to stdout
        // Find the JSON part (last line that looks like JSON)
        String jsonOutput = extractJsonFromOutput(output);
        if (jsonOutput == null) {
            return fallbackParse(output, exitCode, totalTests, execTime);
        }
        
        JsonNode root = objectMapper.readTree(jsonOutput);
        boolean success = root.path("success").asBoolean(false);
        int passed = root.path("numPassedTests").asInt(0);
        int failed = root.path("numFailedTests").asInt(0);
        int total = root.path("numTotalTests").asInt(totalTests);
        
        List<ExecutionResult.TestResult> testResults = new ArrayList<>();
        JsonNode testResultsNode = root.path("testResults");
        if (testResultsNode.isArray()) {
            for (JsonNode testResult : testResultsNode) {
                String name = testResult.path("name").asText("Unknown");
                String status = testResult.path("status").asText("unknown");
                long duration = testResult.path("duration").asLong(0);
                String error = testResult.path("errors").isArray() && testResult.path("errors").size() > 0
                    ? testResult.path("errors").get(0).path("message").asText("")
                    : null;
                
                testResults.add(new ExecutionResult.TestResult(
                    name,
                    "pass".equals(status),
                    null, // input not in output
                    null, // expected not in output
                    error,
                    duration
                ));
            }
        }
        
        ExecutionStatus status = success ? ExecutionStatus.PASSED : ExecutionStatus.FAILED;
        return new ExecutionResult(status, passed, total, execTime, null, output, null, testResults);
    }
    
    private ExecutionResult parsePytestResult(String output, int exitCode, int totalTests, long execTime) throws IOException {
        // Pytest JSON report is written to file, but we also capture stdout
        // Try to find JSON in output
        String jsonOutput = extractJsonFromOutput(output);
        if (jsonOutput == null) {
            return fallbackParse(output, exitCode, totalTests, execTime);
        }
        
        JsonNode root = objectMapper.readTree(jsonOutput);
        int passed = root.path("passed").asInt(0);
        int failed = root.path("failed").asInt(0);
        int total = root.path("total").asInt(totalTests);
        
        List<ExecutionResult.TestResult> testResults = new ArrayList<>();
        JsonNode testsNode = root.path("tests");
        if (testsNode.isArray()) {
            for (JsonNode test : testsNode) {
                String name = test.path("name").asText("Unknown");
                String outcome = test.path("outcome").asText("unknown");
                long duration = (long) (test.path("duration").asDouble(0) * 1000); // Convert to ms
                String error = test.path("call").path("longrepr").asText(null);
                
                testResults.add(new ExecutionResult.TestResult(
                    name,
                    "passed".equals(outcome),
                    null,
                    null,
                    error,
                    duration
                ));
            }
        }
        
        ExecutionStatus status = (exitCode == 0 || failed == 0) ? ExecutionStatus.PASSED : ExecutionStatus.FAILED;
        return new ExecutionResult(status, passed, total, execTime, null, output, null, testResults);
    }
    
    private String extractJsonFromOutput(String output) {
        // Find the last valid JSON object in the output
        String[] lines = output.split("\n");
        for (int i = lines.length - 1; i >= 0; i--) {
            String line = lines[i].trim();
            if (line.startsWith("{") && line.endsWith("}")) {
                try {
                    objectMapper.readTree(line); // Validate it's valid JSON
                    return line;
                } catch (Exception ignored) {
                    // Not valid JSON, continue searching
                }
            }
        }
        return null;
    }
    
    private ExecutionResult fallbackParse(String output, int exitCode, int totalTests, long execTime) {
        if (exitCode == 0) {
            return new ExecutionResult(
                ExecutionStatus.PASSED,
                totalTests, totalTests,
                execTime, null, output, null,
                List.of()
            );
        } else {
            return new ExecutionResult(
                ExecutionStatus.FAILED,
                0, totalTests,
                execTime, null, output, "Tests failed",
                List.of()
            );
        }
    }
    
    private String escapeJson(String s) {
        return "\"" + s.replace("\"", "\\\"").replace("\n", "\\n") + "\"";
    }
    
    private String toJson(Object obj) {
        if (obj instanceof String) return "\"" + obj + "\"";
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            return "[" + list.stream().map(this::toJson).collect(java.util.stream.Collectors.joining(",")) + "]";
        }
        if (obj instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) obj;
            return "{" + map.entrySet().stream()
                .map(e -> "\"" + e.getKey() + "\":" + toJson(e.getValue()))
                .collect(java.util.stream.Collectors.joining(",")) + "}";
        }
        return obj.toString();
    }
    
    private void deleteRecursively(Path path) {
        try {
            Files.walk(path)
                .sorted(java.util.Comparator.reverseOrder())
                .forEach(p -> {
                    try { Files.delete(p); } catch (IOException ignored) {}
                });
        } catch (IOException ignored) {}
    }
}