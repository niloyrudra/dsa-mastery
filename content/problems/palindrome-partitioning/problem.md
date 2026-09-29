---
id: "palindrome-partitioning"
title: "Palindrome Partitioning"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/palindrome-partitioning/"
patterns: ["backtracking-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion"]
relatedProblems: ["generate-parentheses", "n-queens", "word-break", "restore-ip-addresses"]
status: "PUBLISHED"
orderIndex: 57
reasonForSelection: "Backtracking with palindrome check; partition at every valid cut"
---

# Problem Statement

Given a string `s`, partition `s` such that every substring of the partition is a **palindrome**. Return all possible palindrome partitioning of `s`.

## Constraints

- 1 ≤ s.length ≤ 16
- `s` contains only lowercase English letters.

## Examples

**Example 1:**
```
Input: s = "aab"
Output: [["a","a","b"],["aa","b"]]
```

**Example 2:**
```
Input: s = "a"
Output: [["a"]]
```

## Variations

### Variation 1: Palindrome Partitioning II
**Name**: Palindrome Partitioning II

**What Changed**: Return minimum cuts instead of all partitions.

**Original Approach Works**: Same backtracking, track min cuts.

**What Breaks**: Min cuts vs all partitions.

**New Constraint**: Return minimum number of cuts.

**Algorithm Changes**: DP with palindrome precomputation, track min cuts.

**Complexity Changes**: Time O(n²), Space O(n²).

### Variation 2: Word Break
**Name**: Word Break

**What Changed**: Check if string can be segmented into dictionary words.

**Original Approach Works**: DP with dictionary lookup.

**What Breaks**: Dictionary words vs palindrome substrings.

**New Constraint**: Return boolean if word break possible.

**Algorithm Changes**: dp[i] = true if dp[j] && s[j..i] in dict.

**Complexity Changes**: Time O(n²), Space O(n).