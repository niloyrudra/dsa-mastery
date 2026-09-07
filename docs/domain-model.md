# DSA Mastery Platform - Domain Model

## Core Entities

### Problem
```java
@Entity
@Table(name = "problems")
class Problem {
    @Id UUID id
    String slug                    // unique, URL-friendly
    String title
    @Lob String description        // original educational description
    String externalUrl             // link to LeetCode/NeetCode source
    Difficulty difficulty          // EASY, MEDIUM, HARD
    ContentStatus status           // DRAFT → PUBLISHED
    Integer orderIndex             // curriculum sequence
    
    @ManyToMany List<Pattern> patterns
    @ManyToMany List<Algorithm> algorithms
    @ManyToMany List<DataStructure> dataStructures
    @ManyToMany List<Template> templates
    @ManyToMany List<Problem> relatedProblems
    @ManyToMany List<Concept> prerequisites
    @OneToMany List<ProblemVariation> variations
    @OneToMany List<Solution> solutions
    @OneToMany List<TestCase> testCases
    @OneToMany List<Reference> references
    @OneToMany List<LearningObjective> learningObjectives
}
```

### Pattern
```java
@Entity
@Table(name = "patterns")
class Pattern {
    @Id UUID id
    String slug
    String name
    @Lob String definition
    @Lob String whenToUse
    @Lob String whyItWorks
    @Lob String intuition
    @Lob String invariant
    @Lob String strengths
    @Lob String weaknesses
    @Lob String recognitionSignals  // JSON array of signals
    @Lob String commonFailureModes
    @Lob String generalizations
    Complexity complexity
    
    @ManyToMany List<Template> templates
    @ManyToMany List<Algorithm> algorithms
    @ManyToMany List<DataStructure> dataStructures
    @ManyToMany List<Problem> problems
    @ManyToMany List<Pattern> subPatterns
    @ManyToMany List<Pattern> relatedPatterns
}
```

### Algorithm
```java
@Entity
@Table(name = "algorithms")
class Algorithm {
    @Id UUID id
    String slug
    String name
    @Lob String description
    @Lob String stepByStepReasoning
    @Lob String complexityAnalysis
    Complexity complexity
    
    @ManyToMany List<Pattern> patterns
    @ManyToMany List<Problem> problems
    @ManyToMany List<DataStructure> dataStructures
    @ManyToMany List<Template> templates
}
```

### DataStructure
```java
@Entity
@Table(name = "data_structures")
class DataStructure {
    @Id UUID id
    String slug
    String name
    @Lob String description
    @Lob String operations           // JSON: operation -> complexity
    @Lob String whyUsefulHere
    @Lob String tradeoffs
    @Lob String alternatives
    
    @ManyToMany List<Problem> problems
    @ManyToMany List<Pattern> patterns
    @ManyToMany List<Algorithm> algorithms
}
```

### Template
```java
@Entity
@Table(name = "templates")
class Template {
    @Id UUID id
    String slug
    String name
    String language                  // TYPESCRIPT, JAVASCRIPT, PYTHON
    String purpose
    String whenToReachForIt
    String prerequisites
    String mentalModel
    @Lob String code                 // annotated with [1], [2], etc.
    @Lob String explanation          // maps markers to logic
    String invariant
    @Lob String commonModifications
    @Lob String commonMistakes
    Complexity complexity
    
    @ManyToOne Pattern pattern
    @ManyToMany List<Problem> problems
    @OneToMany List<TemplateMapping> mappings
}
```

### TemplateMapping
```java
@Entity
@Table(name = "template_mappings")
class TemplateMapping {
    @Id UUID id
    
    @ManyToOne Template template
    @ManyToOne Solution solution
    
    Integer templateMarker           // e.g., 1, 2, 3 from [1], [2]
    String solutionCodeSnippet       // corresponding solution code
    String explanation               // how template maps to solution
}
```

### Solution
```java
@Entity
@Table(name = "solutions")
class Solution {
    @Id UUID id
    String name                      // "Brute Force", "Hash Map", "Two Pointer Optimal"
    String approach                  // WORST, BETTER, BEST
    @Lob String explanation
    @Lob String whyItWorks
    @Lob String whyNotIdeal          // for non-optimal approaches
    String bottleneckRemaining
    String insightToNext
    Complexity complexity
    String language
    @Lob String code
    
    @ManyToOne Problem problem
    @ManyToOne Template template     // primary template used
    @OneToMany List<TemplateMapping> mappings
    @OneToMany List<TestResult> testResults
}
```

### ProblemVariation
```java
@Entity
@Table(name = "problem_variations")
class ProblemVariation {
    @Id UUID id
    String name
    @Lob String description          // what changed from base
    @Lob String whatChanged
    Boolean originalApproachWorks
    @Lob String whatBreaks
    @Lob String newConstraintMatters
    Boolean dataStructureChanges
    Boolean algorithmChanges
    @Lob String complexityChanges
    
    @ManyToOne Problem baseProblem
    @OneToMany List<Solution> solutions
    @OneToMany List<TestCase> testCases
}
```

### Complexity (Embeddable)
```java
@Embeddable
class Complexity {
    String timeComplexity
    String spaceComplexity
    @Lob String timeReasoning
    @Lob String spaceReasoning
    String bestCaseTime
    String worstCaseTime
    String averageCaseTime
}
```

### TestCase
```java
@Entity
@Table(name = "test_cases")
class TestCase {
    @Id UUID id
    String name
    String input                     // JSON
    String expectedOutput            // JSON
    String description
    TestCaseType type                // BASIC, EDGE_CASE, PERFORMANCE, CUSTOM
    Boolean isHidden                 // for validation only
    
    @ManyToOne Problem problem
    @ManyToOne ProblemVariation variation
}
```

### Concept
```java
@Entity
@Table(name = "concepts")
class Concept {
    @Id UUID id
    String slug
    String name
    @Lob String description
    @Lob String explanation
    
    @ManyToMany List<Concept> prerequisites
    @ManyToMany List<Problem> problems
    @ManyToMany List<Pattern> patterns
}
```

### Reference
```java
@Entity
@Table(name = "references")
class Reference {
    @Id UUID id
    String title
    String url
    ReferenceType type               // OFFICIAL_DOCS, PRIMARY_SOURCE, UNIVERSITY, BOOK, TECH_RESOURCE, PROBLEM_SOURCE
    String relevantConcept
    @Lob String verifies
    
    @ManyToMany List<Problem> problems
    @ManyToMany List<Pattern> patterns
    @ManyToMany List<Algorithm> algorithms
}
```

### LearningObjective
```java
@Entity
@Table(name = "learning_objectives")
class LearningObjective {
    @Id UUID id
    String description
    String bloomLevel                // REMEMBER, UNDERSTAND, APPLY, ANALYZE, EVALUATE, CREATE
    
    @ManyToOne Problem problem
}
```

---

## Relationship Entities

### CurriculumNode
```java
@Entity
@Table(name = "curriculum_nodes")
class CurriculumNode {
    @Id UUID id
    String name
    String description
    NodeType type                    // CATEGORY, PATTERN, ALGORITHM, PROBLEM
    Integer orderIndex
    Integer depth
    
    @ManyToOne CurriculumNode parent
    @OneToMany List<CurriculumNode> children
    
    // Polymorphic references
    UUID problemId
    UUID patternId
    UUID algorithmId
}
```

### Relationship (Generic)
```java
@Entity
@Table(name = "relationships")
class Relationship {
    @Id UUID id
    String sourceType                // PROBLEM, PATTERN, ALGORITHM, DATA_STRUCTURE, TEMPLATE, CONCEPT
    UUID sourceId
    String targetType
    UUID targetId
    RelationshipType type            // USES, REQUIRES, VARIATION_OF, RELATED_TO, PREREQUISITE_OF, IMPLEMENTS
    String description
    Integer strength                 // 1-10 for graph weighting
}
```

---

## Progress Tracking

### MasteryRecord
```java
@Entity
@Table(name = "mastery_records")
class MasteryRecord {
    @Id UUID id
    
    // Polymorphic - can track problem, pattern, algorithm, concept
    String entityType
    UUID entityId
    
    MasteryStatus status             // NOT_STARTED, LEARNING, PRACTICING, SOLVED, MASTERED, NEEDS_REVIEW
    Integer masteryScore             // 0-100 (transparent calculation)
    
    // Problem-specific tracking
    Boolean viewed
    Boolean explanationViewed
    Boolean templateViewed
    Integer attempts
    Boolean passedTests
    Boolean solutionRevealed
    Boolean optimalUnderstood
    Boolean variationsAttempted
    LocalDateTime lastPracticed
    LocalDateTime masteredAt
    
    // Pattern/concept-specific
    Integer problemsSolved
    Integer problemsAttempted
    Double conceptProficiency        // 0.0-1.0
}
```

### ExecutionAttempt
```java
@Entity
@Table(name = "execution_attempts")
class ExecutionAttempt {
    @Id UUID id
    UUID problemId
    UUID userId                      // single-user for now, future-proof
    String language
    @Lob String code
    ExecutionStatus status           // COMPILING, RUNNING, PASSED, FAILED, ERROR, TIMEOUT
    Integer passedTests
    Integer totalTests
    Long executionTimeMs
    Long memoryBytes
    @Lob String output
    @Lob String error
    LocalDateTime attemptedAt
}
```

### AIConversation
```java
@Entity
@Table(name = "ai_conversations")
class AIConversation {
    @Id UUID id
    UUID problemId                   // nullable for general questions
    String modelUsed
    ModelRole modelRole
    
    @OneToMany List<AIMessage> messages
    LocalDateTime startedAt
    LocalDateTime lastMessageAt
}
```

### AIMessage
```java
@Entity
@Table(name = "ai_messages")
class AIMessage {
    @Id UUID id
    @ManyToOne AIConversation conversation
    MessageRole role                 // USER, ASSISTANT, SYSTEM
    @Lob String content
    MessageType type                 // QUESTION, HINT, EXPLANATION, CODE_REVIEW, COMPLEXITY_ANALYSIS, VARIATION
    Integer sequence
    LocalDateTime createdAt
}
```

---

## Enumerations

```java
enum Difficulty { EASY, MEDIUM, HARD }
enum ContentStatus { DRAFT, RESEARCHED, GENERATED, VALIDATED, REVIEWED, PUBLISHED }
enum MasteryStatus { NOT_STARTED, LEARNING, PRACTICING, SOLVED, MASTERED, NEEDS_REVIEW }
enum TestCaseType { BASIC, EDGE_CASE, PERFORMANCE, CUSTOM }
enum ExecutionStatus { COMPILING, RUNNING, PASSED, FAILED, ERROR, TIMEOUT }
enum NodeType { CATEGORY, PATTERN, ALGORITHM, PROBLEM }
enum RelationshipType { USES, REQUIRES, VARIATION_OF, RELATED_TO, PREREQUISITE_OF, IMPLEMENTS }
enum ReferenceType { OFFICIAL_DOCS, PRIMARY_SOURCE, UNIVERSITY, BOOK, TECH_RESOURCE, PROBLEM_SOURCE }
enum MessageRole { USER, ASSISTANT, SYSTEM }
enum MessageType { QUESTION, HINT, EXPLANATION, CODE_REVIEW, COMPLEXITY_ANALYSIS, VARIATION }
enum ModelRole { FAST, CODING, REASONING }
enum Language { TYPESCRIPT, JAVASCRIPT, PYTHON }
```

---

## Entity Relationship Diagram (Text)

```
Problem *──* Pattern
Problem *──* Algorithm
Problem *──* DataStructure
Problem *──* Template
Problem *──* Problem (related)
Problem *──* Concept (prerequisites)
Problem 1──* ProblemVariation
Problem 1──* Solution
Problem 1──* TestCase
Problem 1──* Reference
Problem 1──* LearningObjective
Problem 1──* MasteryRecord

Pattern *──* Template
Pattern *──* Algorithm
Pattern *──* DataStructure
Pattern *──* Pattern (sub/related)

Algorithm *──* DataStructure
Algorithm *──* Template

Template 1──* TemplateMapping
Solution *──* TemplateMapping
Solution 1──* TestResult

CurriculumNode ──* CurriculumNode (parent/children)

Relationship (generic polymorphic connections)

ExecutionAttempt N──1 Problem
AIConversation N──1 Problem (optional)
AIConversation 1──* AIMessage
```

---

## Validation Rules

1. **Problem must have**: at least 1 pattern, 1 algorithm, 1 template, 3 test cases (1 basic, 1 edge, 1 performance)
2. **Solution must have**: complexity with reasoning, executable code passing all tests
3. **Template must have**: annotated code, explanation mapping markers to logic, invariant
4. **Pattern must have**: definition, when-to-use, why-it-works, recognition signals, invariant
5. **Content status transitions**: DRAFT → RESEARCHED → GENERATED → VALIDATED → REVIEWED → PUBLISHED (no skipping)
6. **Mastery calculation**: transparent formula based on tracked fields