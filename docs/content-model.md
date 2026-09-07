# DSA Mastery Platform - Content Model

## Content Authoring Philosophy

**Markdown as Source of Truth** → **Validated Import** → **Structured Database**

```
content/
├── problems/
│   ├── two-sum/
│   │   ├── problem.md           # Problem statement, variations, learning objectives
│   │   ├── solutions/
│   │   │   ├── brute-force.ts
│   │   │   ├── hash-map.ts
│   │   │   └── two-pointer.ts
│   │   ├── test-cases.yaml      # Structured test cases
│   │   ├── templates/
│   │   │   └── two-pointer-opposite.md
│   │   └── references.yaml
│   └── ...
├── patterns/
│   ├── two-pointer/
│   │   ├── pattern.md           # Definition, when-to-use, why-it-works, etc.
│   │   ├── templates/
│   │   │   ├── opposite-ends.md
│   │   │   ├── same-direction.md
│   │   │   └── fast-slow.md
│   │   └── references.yaml
│   └── ...
├── algorithms/
│   ├── binary-search/
│   │   ├── algorithm.md
│   │   ├── templates/
│   │   └── references.yaml
│   └── ...
├── data-structures/
│   ├── hash-map/
│   │   ├── data-structure.md
│   │   └── references.yaml
│   └── ...
├── concepts/
│   ├── arrays/
│   │   ├── concept.md
│   │   └── references.yaml
│   └── ...
└── curriculum.yaml              # Curriculum tree structure
```

---

## Markdown Frontmatter Schemas

### Problem (`problem.md`)
```yaml
---
id: "two-sum"                    # slug, unique
title: "Two Sum"
difficulty: "EASY"               # EASY, MEDIUM, HARD
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/two-sum/"
patterns: ["two-pointer", "hash-map"]
algorithms: ["hash-map-lookup", "two-pointer-opposite"]
dataStructures: ["hash-map", "array"]
templates: ["two-pointer-opposite", "hash-map-lookup"]
prerequisites: ["arrays", "hash-map"]
relatedProblems: ["three-sum", "two-sum-ii", "4sum"]
status: "PUBLISHED"              # DRAFT, RESEARCHED, GENERATED, VALIDATED, REVIEWED, PUBLISHED
orderIndex: 1
reasonForSelection: "Foundational complement-finding pattern; introduces hash map trade-off; appears in 3Sum, 4Sum, subarray sum problems"
---

# Problem Statement

Original educational description...

## Constraints
- 2 ≤ n ≤ 10⁴
- -10⁹ ≤ nums[i] ≤ 10⁹
- Exactly one valid answer

## Examples
...
```

### Problem Variations (in `problem.md` or separate)
```markdown
## Variations

### Variation 1: Sorted Input
**Name**: Two Sum II - Input Array Is Sorted

**What Changed**: Input array is guaranteed sorted in ascending order.

**Original Approach Works**: Hash map still works but wastes sorted property.

**What Breaks**: Nothing breaks, but O(n) space is suboptimal.

**New Constraint**: Sorted input enables two-pointer O(1) space.

**Algorithm Changes**: Yes - two-pointer opposite ends replaces hash map.

**Complexity Changes**: Time O(n) → O(n), Space O(n) → O(1).
```

### Pattern (`pattern.md`)
```yaml
---
id: "two-pointer"
name: "Two Pointer"
category: "ARRAYS_STRINGS"
patterns: []                     # sub-patterns
algorithms: ["two-pointer-opposite", "two-pointer-same", "fast-slow"]
dataStructures: ["array"]
templates: ["two-pointer-opposite", "two-pointer-same", "fast-slow"]
problems: ["two-sum", "three-sum", "container-with-most-water", "trapping-rain-water"]
prerequisites: ["arrays"]
relatedPatterns: ["sliding-window", "binary-search"]
status: "PUBLISHED"
---

# Two Pointer Pattern

## Definition
...

## When to Use
...

## Why It Works
...

## Recognition Signals
- "sorted array"
- "pair with target sum"
- "minimum/maximum subarray"
- "palindrome"
- "two pointers moving toward each other"

## Invariant
...

## Strengths
...

## Weaknesses
...

## Common Failure Modes
...

## Generalizations
...
```

### Template (`templates/two-pointer-opposite.md`)
```yaml
---
id: "two-pointer-opposite"
name: "Two Pointer - Opposite Ends"
pattern: "two-pointer"
language: "TYPESCRIPT"
purpose: "Find pair in sorted array meeting condition"
whenToReachForIt: "Sorted array, pair target, opposite direction movement"
prerequisites: ["arrays", "basic-loops"]
mentalModel: "Two people at opposite ends of a line walking toward each other"
status: "PUBLISHED"
---

# Template: Two Pointer - Opposite Ends

## Code
```typescript
function twoPointerOpposite<T>(
    arr: T[],                     // [1]
    target: T,                    // [2]
    compare: (sum: T, target: T) => number  // [3]
): [number, number] | null {
    let left = 0;                 // [4]
    let right = arr.length - 1;   // [5]
    
    while (left < right) {        // [6]
        const sum = arr[left] + arr[right];  // [7]
        const cmp = compare(sum, target);    // [8]
        
        if (cmp === 0) {          // [9]
            return [left, right];
        } else if (cmp < 0) {     // [10]
            left++;               // [11]
        } else {                  // [12]
            right--;              // [13]
        }
    }
    
    return null;                  // [14]
}
```

## Explanation
[1] Input array (must be sorted for this pattern)
[2] Target value to find
[3] Comparison function: returns -1 if sum < target, 0 if equal, 1 if sum > target
[4] Left pointer starts at beginning
[5] Right pointer starts at end
[6] Loop invariant: all pairs outside [left, right] have been checked
[7] Compute current pair sum
[8] Compare sum to target
[9-13] Move pointers based on comparison - this is the core insight
[14] No valid pair found

## Invariant
At each iteration, all pairs (i, j) where i < left or j > right have been evaluated and rejected.

## Common Modifications
- Return indices vs values
- Count pairs instead of finding one
- Handle duplicates
- Three-pointer partition (Dutch National Flag)

## Common Mistakes
- Forgetting to sort array first
- Moving wrong pointer (left++ when sum > target)
- Not handling duplicate values correctly
- Off-by-one in loop condition (left <= right vs left < right)

## Complexity
- Time: O(n) - each pointer moves at most n times
- Space: O(1) - only two pointers

## Example Problems
- Two Sum (sorted variant)
- 3Sum
- Container With Most Water
- Trapping Rain Water
```

### Algorithm (`algorithm.md`)
```yaml
---
id: "binary-search"
name: "Binary Search"
patterns: ["binary-search", "binary-search-on-answer"]
dataStructures: ["array"]
templates: ["binary-search-classic", "binary-search-boundary", "binary-search-rotated", "binary-search-on-answer"]
problems: ["search-insert-position", "find-first-last", "koko-eating-bananas", "capacity-to-ship-packages"]
prerequisites: ["arrays", "logarithms"]
status: "PUBLISHED"
---

# Binary Search Algorithm

## Description
...

## Step-by-Step Reasoning
...

## Complexity Analysis
...

## Variants
- Classic: exact match
- Boundary: first/last occurrence
- Rotated: search in rotated sorted array
- On Answer: binary search on answer space
```

### Data Structure (`data-structure.md`)
```yaml
---
id: "hash-map"
name: "Hash Map"
patterns: ["hash-map-lookup", "frequency-count", "prefix-sum"]
algorithms: ["hash-map-lookup"]
operations:
  get: "O(1) average"
  set: "O(1) average"
  delete: "O(1) average"
  contains: "O(1) average"
tradeoffs: "O(n) space for O(1) lookup vs O(1) space O(n) linear search"
alternatives: ["array (if small integer keys)", "tree map (if ordered iteration needed)"]
status: "PUBLISHED"
---

# Hash Map

## Description
...

## Why Useful Here
...

## Operations & Complexity
...

## Trade-offs
...

## Alternatives
...
```

### Test Cases (`test-cases.yaml`)
```yaml
- name: "Basic"
  description: "Standard two-sum case"
  input:
    nums: [2, 7, 11, 15]
    target: 9
  expected: [0, 1]
  type: "BASIC"
  hidden: false

- name: "Edge: Negative Numbers"
  description: "Negative values in array"
  input:
    nums: [-3, 4, 3, 90]
    target: 0
  expected: [0, 2]
  type: "EDGE_CASE"
  hidden: false

- name: "Edge: Duplicate Values"
  description: "Duplicate values, distinct indices"
  input:
    nums: [3, 3]
    target: 6
  expected: [0, 1]
  type: "EDGE_CASE"
  hidden: false

- name: "Performance: Large Array"
  description: "10,000 elements"
  input:
    nums: "GENERATE: range(10000)"
    target: 19999
  expected: [9999, 10000]
  type: "PERFORMANCE"
  hidden: true
```

### References (`references.yaml`)
```yaml
- title: "Introduction to Algorithms (CLRS)"
  url: "https://mitpress.mit.edu/9780262046305/introduction-to-algorithms/"
  type: "BOOK"
  relevantConcept: "Hash Tables, Chapter 11"
  verifies: "O(1) average case lookup, collision resolution"

- title: "Hash Table - Wikipedia"
  url: "https://en.wikipedia.org/wiki/Hash_table"
  type: "TECH_RESOURCE"
  relevantConcept: "Hash Map"
  verifies: "Average O(1) operations, load factor, collision handling"

- title: "Two Sum - LeetCode"
  url: "https://leetcode.com/problems/two-sum/"
  type: "PROBLEM_SOURCE"
  relevantConcept: "Two Sum Problem"
  verifies: "Problem constraints and examples"
```

### Curriculum (`curriculum.yaml`)
```yaml
- name: "DSA Mastery Curriculum"
  type: "CATEGORY"
  children:
    - name: "Foundations"
      type: "CATEGORY"
      children:
        - name: "Arrays & Hashing"
          type: "CATEGORY"
          children:
            - name: "Hash Map Lookup"
              type: "PATTERN"
              patternId: "hash-map-lookup"
              children:
                - name: "Two Sum"
                  type: "PROBLEM"
                  problemId: "two-sum"
                - name: "Contains Duplicate"
                  type: "PROBLEM"
                  problemId: "contains-duplicate"
            - name: "Two Pointer"
              type: "PATTERN"
              patternId: "two-pointer"
              children:
                - name: "Valid Palindrome"
                  type: "PROBLEM"
                  problemId: "valid-palindrome"
                - name: "Two Sum II"
                  type: "PROBLEM"
                  problemId: "two-sum-ii"
```

---

## Import Pipeline

```java
@Service
public class ContentImportService {
    
    public ImportResult importContent(Path contentRoot) {
        // 1. Parse curriculum.yaml → CurriculumNode tree
        // 2. For each problem directory:
        //    - Parse problem.md frontmatter + content
        //    - Parse solutions/*.ts
        //    - Parse test-cases.yaml
        //    - Parse templates/
        //    - Parse references.yaml
        // 3. Validate:
        //    - All referenced patterns/algorithms/templates exist
        //    - Solutions compile and pass tests
        //    - Complexity claims match actual performance
        //    - References are valid URLs
        // 4. Persist to database with status = VALIDATED
        // 5. Human review → status = REVIEWED → PUBLISHED
    }
}
```

---

## Validation Rules

### Automated (CI Pipeline)
1. **Syntax**: All Markdown frontmatter valid YAML
2. **References**: All slugs in frontmatter exist in registry
3. **Solutions**: Compile without errors
4. **Tests**: All solutions pass all test cases (basic, edge, performance)
5. **Complexity**: Performance tests pass within declared bounds
6. **Templates**: Annotated markers [1], [2]... match explanation count
6. **Mappings**: Template mappings reference valid template markers

### Human Review Checklist
- [ ] Problem description is original (not copied)
- [ ] Explanation teaches *why*, not just *how*
- [ ] Recognition signals are structural, not keyword-based
- [ ] Worst→Better→Best progression is clear
- [ ] Template mappings are accurate
- [ ] Variations are algorithmically meaningful
- [ ] References actually support claims
- [ ] No fabricated complexity/interview frequency claims

---

## Content Status Pipeline

```
DRAFT → RESEARCHED → GENERATED → VALIDATED → REVIEWED → PUBLISHED
         ↑              ↑            ↑           ↑
      Human          AI-assisted   Automated   Human
      research       generation    validation  review
```

### Status Definitions
- **DRAFT**: Placeholder, incomplete
- **RESEARCHED**: Human has gathered sources, outlined approach
- **GENERATED**: AI has produced initial content from prompts
- **VALIDATED**: Automated checks pass (compile, test, complexity)
- **REVIEWED**: Human has verified correctness and pedagogy
- **PUBLISHED**: Visible to learner in curriculum

---

## Versioning & Updates

- Content versioned via Git (markdown files)
- Database stores `content_version` hash
- Re-import detects changes via hash comparison
- Published content immutable unless new version explicitly created