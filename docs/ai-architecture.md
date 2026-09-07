# DSA Mastery Platform - AI Architecture

## AI Provider Abstraction

### Core Interface

```java
public interface AIProvider {
    String getName();
    List<ModelInfo> getAvailableModels();
    CompletionResponse complete(CompletionRequest request);
    Flux<CompletionChunk> streamComplete(CompletionRequest request);
    EmbeddingResponse embed(EmbeddingRequest request);
    boolean isHealthy();
}

public record ModelInfo(
    String id,
    String name,
    ModelRole recommendedRole,
    int contextWindow,
    boolean supportsStreaming
) {}

public enum ModelRole {
    FAST,       // Classification, tagging, simple explanation
    CODING,     // Code review, algorithm reasoning, template mapping
    REASONING   // Content generation, difficult explanations, variation generation
}
```

### Provider Implementations

#### 1. OllamaProvider (Primary - Local)
```java
@Component
@Profile("!cloud-ai")
public class OllamaProvider implements AIProvider {
    private final WebClient webClient;
    private final OllamaProperties props;
    
    // Models: llama3.1:8b (FAST), codellama:13b (CODING), deepseek-coder:33b (REASONING)
}
```

#### 2. OpenAICompatibleProvider (Cloud Fallback)
```java
@Component
@Profile("cloud-ai")
public class OpenAICompatibleProvider implements AIProvider {
    // Supports: OpenAI, Together AI, Groq, OpenRouter, etc.
    // Configured via: OPENAI_BASE_URL, OPENAI_API_KEY
}
```

#### 3. AnthropicProvider (Cloud Alternative)
```java
@Component
@Profile("cloud-ai")
public class AnthropicProvider implements AIProvider {
    // Claude 3.5 Sonnet, Haiku, Opus
}
```

### Model Router

```java
@Service
public class ModelRouter {
    private final Map<ModelRole, AIProvider> roleProviders;
    
    public AIProvider getProvider(ModelRole role) {
        return roleProviders.getOrDefault(role, roleProviders.get(ModelRole.FAST));
    }
    
    public CompletionResponse complete(ModelRole role, CompletionRequest request) {
        return getProvider(role).complete(request);
    }
}
```

**Configuration:**
```yaml
ai:
  providers:
    ollama:
      base-url: http://localhost:11434
      models:
        fast: "llama3.1:8b"
        coding: "codellama:13b"
        reasoning: "deepseek-coder:33b"
    openai:
      base-url: https://api.openai.com/v1
      api-key: ${OPENAI_API_KEY}
      models:
        fast: "gpt-4o-mini"
        coding: "gpt-4o"
        reasoning: "o1-preview"
```

---

## AI Tutor Capabilities

### 1. Concept Explanation (Multi-Level)

**Prompt Template:**
```
You are a DSA tutor. Explain {concept} at {level} level.

Levels:
- SIMPLE: 2-3 sentences, analogy, no jargon
- INTERMEDIATE: Technical definition, key operations, when to use
- ADVANCED: Invariants, trade-offs, variations, complexity proofs
- INTERVIEW: How to explain in interview, common follow-ups, edge cases

Context: Current problem = {problemTitle}, Pattern = {patternName}
```

### 2. Socratic Guidance

**Behavior:** Never give direct answer. Ask progressively specific questions.

**Prompt Template:**
```
Student is working on: {problemTitle}
Current code: {code or "none"}
Question: {studentQuestion}

Your role: Socratic tutor. Ask ONE question that guides toward insight.
Do not explain. Do not give hints. Just ask the next question.

Examples:
- "What information would you need from earlier elements?"
- "What property does the sorted array give you?"
- "If you fix one pointer, what happens to the other?"
```

### 3. Progressive Hints

**Hint Levels:**
```
Hint 1 (Direction): "Think about what data structure allows O(1) lookup"
Hint 2 (Data Structure): "A hash map could store previously seen values"
Hint 3 (Pattern): "This is a complement-finding problem - Two Sum pattern"
Hint 4 (Algorithm): "For each element, check if target - element exists in map"
Hint 5 (Implementation): "Store value->index mapping as you iterate"
```

**Implementation:**
```java
@Service
public class HintService {
    public HintResponse getNextHint(UUID problemId, int currentLevel, String studentCode) {
        // Level 1-5 progression
        // Track hints given per problem per session
    }
}
```

### 4. Solution Review

**Analysis Dimensions:**
- Correctness (logic errors, edge cases)
- Complexity (time/space with reasoning)
- Code quality (readability, idioms, naming)
- Pattern adherence (uses template correctly)
- Optimization opportunities

**Prompt Template:**
```
Review this {language} solution for {problemTitle}:

Code:
{code}

Test Results: {passed}/{total} passed

Provide structured feedback:
1. Correctness: [PASS/ISSUES] - specific bugs
2. Complexity: [Time: X, Space: Y] - with reasoning
3. Strengths: [what works well]
4. Issues: [bugs, edge cases missed, inefficiencies]
5. Optimization: [if applicable, what pattern/template would improve]
6. Why Optimization Works: [invariant, search space reduction]
```

### 5. Misconception Detection

**Common Misconceptions:**
- "Sorting first" when order matters
- "Sliding window" for non-contiguous problems
- "DP" for greedy problems
- "BFS" when DFS needed (or vice versa)
- Wrong heap direction (min vs max)

**Detection:**
```java
@Service
public class MisconceptionDetector {
    public List<Misconception> detect(String problemSlug, String studentCode, String studentExplanation) {
        // Pattern matching on code structure + NLP on explanation
        // Return specific misconceptions with corrections
    }
}
```

### 6. Complexity Explanation

**Interaction:**
```
AI: "What do you think the time complexity is?"
Student: "O(n²) because of nested loops"
AI: "Let's trace through. How many times does the inner loop actually run?"
```

### 7. Code Tracing

**Step-by-step execution visualization:**
```
Input: [2,7,11,15], target=9

Iteration 0: i=0, num=2, complement=7, map={} → not found, map={2:0}
Iteration 1: i=1, num=7, complement=2, map={2:0} → FOUND at index 0
Return [0,1]
```

### 8. Problem Variation Generation

**Prompt Template:**
```
Generate 3 meaningful variations of {problemTitle} that test pattern understanding.

Base Problem: {description}
Pattern: {patternName}
Template: {templateName}

For each variation:
1. Name
2. What changed (constraints, input format, output format)
3. Does original approach work?
4. What breaks?
5. New algorithm/data structure needed?
6. Complexity change?

Variations must be algorithmically meaningful, not cosmetic.
```

---

## Context Construction

### Problem Context (for Tutor)
```java
public record ProblemContext(
    ProblemSummary problem,
    List<PatternSummary> patterns,
    List<TemplateSummary> templates,
    List<SolutionSummary> solutions,
    List<TestCaseSummary> testCases,
    MasteryRecord mastery,
    List<ExecutionAttempt> recentAttempts
) {}
```

**Token Budget:** ~4000 tokens max per request

### Conversation Context
- Last 10 messages
- Current problem context
- Student's code (if any)
- Mastery status

---

## Local-First AI Strategy

### Graceful Degradation

| Feature | Ollama Available | Ollama Unavailable |
|---------|------------------|-------------------|
| Concept Explanation | ✅ Full | ✅ Pre-generated static content |
| Socratic Guidance | ✅ Full | ⚠️ Static hint bank |
| Progressive Hints | ✅ Full | ✅ Pre-defined hint levels |
| Solution Review | ✅ Full | ❌ Disabled |
| Misconception Detection | ✅ Full | ❌ Disabled |
| Code Tracing | ✅ Full | ⚠️ Static traces for common problems |
| Variation Generation | ✅ Full | ✅ Pre-defined variations |

### Static Content Fallback
- Pre-generated explanations stored in database
- Hint bank per problem (5 levels)
- Common misconceptions catalog per pattern
- Code traces for published solutions

---

## Content Generation Pipeline (AI-Assisted)

### Stages
1. **RESEARCH** - Human researches problem, sources, patterns
2. **DRAFT** - AI generates initial content from structured prompt
3. **VALIDATE** - Automated: compile, run tests, complexity check
4. **REVIEW** - Human reviews for correctness, pedagogy
5. **PUBLISH** - Status → PUBLISHED

### Generation Prompts (Examples)

**Problem Description:**
```
Write an original educational problem description for {canonicalProblemName}.

Requirements:
- DO NOT copy LeetCode/NeetCode wording
- Define the computational challenge precisely
- Include constraints (n range, value ranges)
- Include 2-3 examples with explanation
- Note: This is for learning, not competitive programming

Reference (for verification only): {sourceUrl}
```

**Pattern Explanation:**
```
Write a comprehensive pattern explanation for {patternName}.

Structure:
1. Definition (2-3 sentences)
2. When to Use (specific recognition signals)
3. Why It Works (invariant, mathematical basis)
4. Intuition (visual/analogy)
5. Step-by-Step Reasoning
6. Strengths & Weaknesses
7. Complexity with reasoning
8. Common Failure Modes
9. Generalizations

Reference: {algorithmTextbook}
```

---

## AI Safety & Guardrails

### Input Validation
- Max prompt length: 8000 tokens
- Sanitize code input (no system commands)
- Timeout: 30s per request

### Output Validation
- No executable code in explanations (markdown code blocks only)
- Complexity claims must include reasoning
- References must be verifiable URLs

### Privacy
- No conversation data leaves local network (Ollama)
- Cloud providers: only send problem context + student code
- No PII in prompts

---

## Monitoring & Observability

- Log all AI requests/responses (truncated)
- Track: latency, token usage, error rate, fallback rate
- Alert: Ollama unavailable > 5min, error rate > 10%