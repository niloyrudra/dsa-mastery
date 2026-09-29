---
id: "regular-expression-matching"
title: "Regular Expression Matching"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/regular-expression-matching/"
patterns: ["dp-2d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["edit-distance", "interleaving-string", "wildcard-matching", "distinct-subsequences"]
status: "PUBLISHED"
orderIndex: 48
reasonForSelection: "2D DP with * handling; classic regex DP problem"
---

# Problem Statement

Given an input string `s` and a pattern `p`, implement regular expression matching with support for `'.'` and `'*'` where:

- `'.'` Matches any single character.
- `'*'` Matches zero or more of the preceding element.

The matching should cover the **entire** input string (not partial).

## Constraints

- 1 ≤ s.length ≤ 20
- 1 ≤ p.length ≤ 20
- `s` contains only lowercase English letters.
- `p` contains only lowercase English letters, `'.'`, and `'*'`.
- It is guaranteed for each appearance of the character `'*'`, there will be a previous valid character to match.

## Examples

**Example 1:**
```
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
```

**Example 2:**
```
Input: s = "aa", p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
```

**Example 3:**
```
Input: s = "ab", p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
```

## Variations

### Variation 1: Wildcard Matching
**Name**: Wildcard Matching

**What Changed**: Pattern with `?` and `*` (no preceding element for `*`).

**Original Approach Works**: 2D DP, different `*` handling.

**What Breaks**: `*` matches any sequence (not preceding element).

**New Constraint**: `?` matches single char, `*` matches any sequence.

**Algorithm Changes**: dp[i][j] = dp[i-1][j-1] if match or p[j-1]=='?', dp[i-1][j] || dp[i][j-1] if p[j-1]=='*'.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Edit Distance
**Name**: Edit Distance

**What Changed**: Minimum edit operations between strings.

**Original Approach Works**: 2D DP on string pairs.

**What Breaks**: Different operations.

**New Constraint**: Return minimum operations.

**Algorithm Changes**: dp[i][j] = min of insert/delete/replace.

**Complexity Changes**: Time O(m*n), Space O(m*n).