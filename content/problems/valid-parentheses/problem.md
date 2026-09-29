---
id: "valid-parentheses"
title: "Valid Parentheses"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/valid-parentheses/"
patterns: ["stack-fundamentals"]
algorithms: []
dataStructures: ["stack"]
templates: []
prerequisites: ["stack"]
relatedProblems: ["generate-parentheses", "evaluate-reverse-polish-notation", "min-stack", "longest-valid-parentheses", "remove-invalid-parentheses"]
status: "PUBLISHED"
orderIndex: 12
reasonForSelection: "Stack for matching; hash map for bracket pairs"
---

# Problem Statement

Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:

1. Open brackets must be closed by the same type of brackets.
2. Open brackets must be closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

## Constraints

- 1 ≤ s.length ≤ 10⁴
- `s` consists of parentheses only `'()[]{}'`.

## Examples

**Example 1:**
```
Input: s = "()"
Output: true
```

**Example 2:**
```
Input: s = "()[]{}"
Output: true
```

**Example 3:**
```
Input: s = "(]"
Output: false
```

**Example 4:**
```
Input: s = "([)]"
Output: false
```

**Example 5:**
```
Input: s = "{[]}"
Output: true
```

## Variations

### Variation 1: Generate Parentheses
**Name**: Generate Parentheses

**What Changed**: Generate all valid combinations.

**Original Approach Works**: Backtracking with open/close count.

**What Breaks**: Generation vs validation.

**New Constraint**: Return all valid combinations.

**Algorithm Changes**: Backtracking with open/close count.

**Complexity Changes**: Time O(4ⁿ/√n), Space O(n).

### Variation 2: Longest Valid Parentheses
**Name**: Longest Valid Parentheses

**What Changed**: Find length of longest valid substring.

**Original Approach Works**: Stack with indices or DP.

**What Breaks**: Length vs boolean.

**New Constraint**: Return max length.

**Algorithm Changes**: Stack with indices, or DP.

**Complexity Changes**: Time O(n), Space O(n).