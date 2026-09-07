# DSA Mastery Platform - Code Execution Architecture

## Overview

Secure, isolated code execution for learner submissions supporting TypeScript, JavaScript, and Python.

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Spring Boot API                          │
│  POST /api/v1/execution/jobs                                    │
│         │                                                       │
│         ▼                                                       │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ ExecutionJob (Entity)                                    │   │
│  │ - id, problemId, language, code, status, timestamps    │   │
│  └─────────────────────────────────────────────────────────┘   │
│         │                                                       │
│         ▼ (@Async)                                              │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ CodeRunnerService                                        │   │
│  │ 1. Prepare workspace                                     │   │
│  │ 2. Generate test harness                                 │   │
│  │ 3. docker run (isolated)                                 │   │
│  │ 4. Capture results                                       │   │
│  │ 5. Update ExecutionJob                                   │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Docker Sandbox (Ephemeral)                   │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Container: dsa-runner-{language}                         │   │
│  │ --rm --cpus=1 --memory=256m --pids-limit=50            │   │
│  │ --network=none --read-only                              │   │
│  │ --tmpfs /workspace:exec,size=100m                       │   │
│  │ -v /tmp/dsa-runs/{jobId}:/workspace:ro                 │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

---

## Runner Images

### Base Requirements
- Non-root user (UID 1000)
- Read-only root filesystem
- No network access
- Resource limits enforced by Docker
- Pre-installed test frameworks

### TypeScript Runner (`dsa-runner-typescript`)
```dockerfile
FROM node:20-alpine

# Install TypeScript tooling
RUN npm install -g typescript ts-node vitest @types/node

# Non-root user
RUN adduser -D -u 1000 runner
USER runner

WORKDIR /workspace
ENTRYPOINT ["sh", "-c"]
```

### JavaScript Runner (`dsa-runner-javascript`)
```dockerfile
FROM node:20-alpine

RUN npm install -g vitest

RUN adduser -D -u 1000 runner
USER runner

WORKDIR /workspace
ENTRYPOINT ["sh", "-c"]
```

### Python Runner (`dsa-runner-python`)
```dockerfile
FROM python:3.13-alpine

RUN pip install pytest pytest-timeout

RUN adduser -D -u 1000 runner
USER runner

WORKDIR /workspace
ENTRYPOINT ["sh", "-c"]
```

---

## Test Harness Generation

### TypeScript/JavaScript
```typescript
// Generated test file: solution.test.ts
import { describe, it, expect } from 'vitest';
import { solution } from './solution';

const testCases = [
  { name: "Basic", input: [[2,7,11,15], 9], expected: [0,1] },
  { name: "Edge: Empty", input: [[], 5], expected: [] },
  // ... from database
];

describe('Solution', () => {
  testCases.forEach(({ name, input, expected }) => {
    it(name, () => {
      const result = solution(...input);
      expect(result).toEqual(expected);
    });
  });
});
```

### Python
```python
# Generated test file: test_solution.py
import pytest
from solution import solution

test_cases = [
    ("Basic", ([2,7,11,15], 9), [0,1]),
    ("Edge: Empty", ([], 5), []),
]

@pytest.mark.parametrize("name,input,expected", test_cases)
def test_solution(name, input, expected):
    result = solution(*input)
    assert result == expected, f"{name}: expected {expected}, got {result}"
```

---

## Execution Flow

### 1. Job Submission
```java
@PostMapping("/execution/jobs")
public ResponseEntity<JobResponse> submit(@Valid @RequestBody ExecutionRequest request) {
    ExecutionJob job = executionJobRepository.save(ExecutionJob.builder()
        .problemId(request.problemId())
        .language(request.language())
        .code(request.code())
        .status(ExecutionStatus.QUEUED)
        .build());
    
    codeRunnerService.executeAsync(job.getId());
    
    return ResponseEntity.accepted().body(new JobResponse(job.getId(), "QUEUED"));
}
```

### 2. Async Execution
```java
@Async("executionExecutor")
public void executeAsync(UUID jobId) {
    ExecutionJob job = jobRepository.findById(jobId).orElseThrow();
    job.setStatus(ExecutionStatus.RUNNING);
    jobRepository.save(job);
    
    try {
        ExecutionResult result = runInSandbox(job);
        job.setStatus(result.status());
        job.setPassedTests(result.passedTests());
        job.setTotalTests(result.totalTests());
        job.setExecutionTimeMs(result.executionTimeMs());
        job.setMemoryBytes(result.memoryBytes());
        job.setOutput(result.output());
        job.setError(result.error());
    } catch (Exception e) {
        job.setStatus(ExecutionStatus.ERROR);
        job.setError(e.getMessage());
    }
    
    job.setCompletedAt(Instant.now());
    jobRepository.save(job);
}
```

### 3. Sandbox Execution
```java
public ExecutionResult runInSandbox(ExecutionJob job) {
    Path workspace = Files.createTempDirectory("dsa-run-" + job.getId());
    
    try {
        // Write solution file
        writeSolutionFile(workspace, job);
        
        // Write test file
        writeTestFile(workspace, job);
        
        // Write package.json / pyproject.toml if needed
        writeConfigFiles(workspace, job.getLanguage());
        
        // Docker run command
        String image = getRunnerImage(job.getLanguage());
        List<String> cmd = buildDockerCommand(workspace.toString(), image, job.getLanguage());
        
        // Execute with timeout
        Process process = new ProcessBuilder(cmd)
            .redirectErrorStream(true)
            .start();
        
        // Capture output with timeout
        String output = captureOutput(process, Duration.ofSeconds(30));
        int exitCode = process.waitFor();
        
        // Parse results
        return parseTestOutput(output, exitCode);
        
    } finally {
        // Cleanup
        deleteRecursively(workspace);
    }
}
```

### 4. Docker Command Builder
```java
private List<String> buildDockerCommand(String workspace, String image, Language language) {
    List<String> cmd = new ArrayList<>();
    cmd.add("docker");
    cmd.add("run");
    cmd.add("--rm");
    cmd.add("--cpus=1");
    cmd.add("--memory=256m");
    cmd.add("--memory-swap=256m");
    cmd.add("--pids-limit=50");
    cmd.add("--network=none");
    cmd.add("--read-only");
    cmd.add("--tmpfs=/workspace:exec,size=100m");
    cmd.add("--user=1000:1000");
    cmd.add("-v");
    cmd.add(workspace + ":/workspace:ro");
    cmd.add(image);
    
    // Language-specific test command
    switch (language) {
        case TYPESCRIPT -> cmd.add("sh -c \"npx vitest run --reporter=json\"");
        case JAVASCRIPT -> cmd.add("sh -c \"npx vitest run --reporter=json\"");
        case PYTHON -> cmd.add("sh -c \"python -m pytest --json-report --tb=short\"");
    }
    
    return cmd;
}
```

---

## Resource Limits

| Resource | Limit | Enforcement |
|----------|-------|-------------|
| CPU | 1 core | `--cpus=1` |
| Memory | 256 MB | `--memory=256m --memory-swap=256m` |
| Processes | 50 | `--pids-limit=50` |
| Execution Time | 30 seconds | Application-level timeout |
| Output Size | 1 MB | Stream buffer limit |
| File System | 100 MB tmpfs | `--tmpfs=/workspace:size=100m` |
| Network | None | `--network=none` |
| Root FS | Read-only | `--read-only` |

---

## Security Measures

1. **No `eval()` or `exec()` in application process** - Only Docker
2. **Ephemeral containers** - `--rm` auto-cleanup
3. **Non-root user** - UID 1000 in container
4. **Read-only root FS** - `--read-only`
5. **No network** - `--network=none`
6. **Resource quotas** - cgroups via Docker flags
7. **Temp directory isolation** - Unique per job, deleted after
8. **No host filesystem access** - Only mounted workspace (read-only)
9. **No secrets in containers** - No env vars with credentials
10. **Timeout enforcement** - Both Docker and application level

---

## Test Result Parsing

### Vitest JSON Reporter Output
```json
{
  "success": true,
  "testResults": [
    { "name": "Basic", "status": "pass", "duration": 2.3 },
    { "name": "Edge: Empty", "status": "pass", "duration": 1.1 }
  ],
  "numPassedTests": 2,
  "numFailedTests": 0,
  "numTotalTests": 2
}
```

### Pytest JSON Report
```json
{
  "passed": 2,
  "failed": 0,
  "total": 2,
  "tests": [
    { "name": "test_solution[Basic]", "outcome": "passed", "duration": 0.01 },
    { "name": "test_solution[Edge: Empty]", "outcome": "passed", "duration": 0.01 }
  ]
}
```

---

## Performance Testing

### Performance Test Cases
```java
// Generated for problems with PERFORMANCE test cases
@Test
void performanceTest() {
    // Large input: n = 100,000
    int[] largeInput = generateLargeInput(100_000);
    long start = System.nanoTime();
    solution(largeInput, target);
    long elapsedMs = (System.nanoTime() - start) / 1_000_000;
    
    // Assert reasonable upper bound (e.g., O(n log n) < 500ms)
    assert elapsedMs < 500 : "Time limit exceeded: " + elapsedMs + "ms";
}
```

---

## Execution Job Lifecycle

```
QUEUED → RUNNING → COMPILING → RUNNING_TESTS → PASSED/FAILED
                    ↓              ↓
                 ERROR         TIMEOUT
```

### Status Transitions
- `QUEUED` → `RUNNING` (worker picks up)
- `RUNNING` → `COMPILING` (compilation started)
- `COMPILING` → `RUNNING_TESTS` (compilation success)
- `COMPILING` → `ERROR` (compilation failed)
- `RUNNING_TESTS` → `PASSED` (all tests pass)
- `RUNNING_TESTS` → `FAILED` (some tests fail)
- `RUNNING_TESTS` → `TIMEOUT` (exceeded 30s)
- Any → `ERROR` (infrastructure error)

---

## Queue Management (V1: Simple)

- Spring `@Async` with bounded thread pool (max 4 concurrent)
- For production: Redis + BullMQ or Kafka + separate worker service

```java
@Configuration
public class ExecutionConfig {
    @Bean("executionExecutor")
    public TaskExecutor executionExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("execution-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
```

---

## Future Extraction Points

The execution module is designed for extraction:

1. **Interface**: `CodeExecutionService` - can be implemented by remote service
2. **DTOs**: `ExecutionRequest`, `ExecutionResult` - shared contract
3. **Runner Images**: Published to registry, pulled by workers
4. **Queue**: Replace `@Async` with message broker
5. **Storage**: S3-compatible for workspace artifacts

---

## Monitoring

- Job duration histogram
- Queue depth gauge
- Error rate by language
- Resource utilization (via Docker stats)
- Timeout rate