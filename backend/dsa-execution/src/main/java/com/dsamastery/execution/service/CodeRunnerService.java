package com.dsamastery.execution.service;

import com.dsamastery.execution.entity.ExecutionResult;
import com.dsamastery.common.enums.ExecutionStatus;
import com.dsamastery.common.enums.Language;
import com.dsamastery.content.entity.TestCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
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
                    null, "Execution timed out after " + timeoutSeconds + "s", null
                );
            }
            
            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.exitValue();
            
            long execTime = Duration.between(start, Instant.now()).toMillis();
            
            return parseResult(output, exitCode, testCases.size(), execTime);
            
        } catch (Exception e) {
            log.error("Execution error", e);
            return new ExecutionResult(
                ExecutionStatus.ERROR,
                0, testCases.size(),
                Duration.between(start, Instant.now()).toMillis(),
                null, e.getMessage(), null
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
            case PYTHON -> "sh -c \"python -m pytest --json-report --tb=short -v 2>&1\"";
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
            "--user=1000:1000",
            "-v", workspace + ":/workspace:ro",
            "-w", "/workspace",
            image,
            testCmd
        );
    }
    
    private ExecutionResult parseResult(String output, int exitCode, int totalTests, long execTime) {
        // Parse Vitest JSON output or Pytest JSON output
        // For simplicity, basic parsing
        if (exitCode == 0) {
            return new ExecutionResult(
                ExecutionStatus.PASSED,
                totalTests, totalTests,
                execTime, null, output, null
            );
        } else {
            // Try to extract passed/failed from output
            int passed = extractPassedCount(output);
            return new ExecutionResult(
                ExecutionStatus.FAILED,
                passed, totalTests,
                execTime, null, output, "Tests failed"
            );
        }
    }
    
    private int extractPassedCount(String output) {
        // Basic extraction - would be improved with proper JSON parsing
        return 0;
    }
    
    private String escapeJson(String s) {
        return "\"" + s.replace("\"", "\\\"").replace("\n", "\\n") + "\"";
    }
    
    private String toJson(Object obj) {
        // Simple JSON conversion - in production use Jackson
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