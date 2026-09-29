---
id: "generate-parentheses"
title: "Generate Parentheses"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/generate-parentheses/"
patterns: ["backtracking-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion"]
relatedProblems: ["valid-parentheses", "evaluate-reverse-polish-notation", "longest-valid-parentheses", "remove-invalid-parentheses"]
status: "PUBLISHED"
orderIndex: 55
reasonForSelection: "Backtracking with open/close count; classic parentheses generation"
---

# Problem Statement

Given `n` pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

## Constraints

- 1 ≤ n ≤ 8

## Examples

**Example 1:**
```
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
```

**Example 2:**
```
Input: n = 1
Output: ["()"]
```

## Variations

### Variation 1: Valid Parentheses
**Name**: Valid Parentheses

**What Changed**: Check if string is valid instead of generating.

**Original Approach Works**: Stack for matching.

**What Breaks**: Validation vs generation.

**New Constraint**: Return true if valid.

**Algorithm Changes**: Stack push/pop for matching.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Longest Valid Parentheses
**Name**: Longest Valid Parentheses

**What Changed**: Find length of longest valid substring.

**Original Approach Works**: Stack or DP for longest valid.

**What Breaks**: Different objective.

**New Constraint**: Return max length.

**Algorithm Changes**: Stack with indices or DP.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 3: Remove Invalid Parentheses
**Name**: Remove Invalid Parentheses

**What Changed**: Remove minimum to make valid.

**Original Approach Works**: BFS or backtracking with pruning.

**What Breaks**: Generate all valid with minimum removals.

**New Constraint**: Return all possible results.

**Algorithm Changes**: BFS level by level, stop at first valid level.

**Complexity Changes**: Time O(2ⁿ), Space O(n).