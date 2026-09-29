---
id: "longest-common-subsequence"
title: "Longest Common Subsequence"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/longest-common-subsequence/"
patterns: ["dp-2d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["edit-distance", "distinct-subsequences", "interleaving-string", "longest-increasing-subsequence"]
status: "PUBLISHED"
orderIndex: 46
reasonForSelection: "Classic 2D DP on two strings; space optimization possible"
---

# Problem Statement

Given two strings `text1` and `text2`, return the length of their longest **common subsequence**. If there is no common subsequence, return `0`.

A **subsequence** of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters. (e.g., `"ace"` is a subsequence of `"abcde"` while `"aec"` is not).

## Constraints

- 1 ≤ text1.length, text2.length ≤ 1000
- `text1` and `text2` consist of only lowercase English letters.

## Examples

**Example 1:**
```
Input: text1 = "abcde", text2 = "ace"
Output: 3
Explanation: The longest common subsequence is "ace" and its length is 3.
```

**Example 2:**
```
Input: text1 = "abc", text2 = "abc"
Output: 3
```

**Example 3:**
```
Input: text1 = "abc", text2 = "def"
Output: 0
```

## Variations

### Variation 1: Edit Distance
**Name**: Edit Distance

**What Changed**: Minimum operations to convert one string to another.

**Original Approach Works**: Same 2D DP on string pairs.

**What Breaks**: Min operations vs max LCS.

**New Constraint**: Return minimum edit distance.

**Algorithm Changes**: dp[i][j] = min of insert/delete/replace.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Longest Increasing Subsequence
**Name**: Longest Increasing Subsequence

**What Changed**: 1D DP on single array.

**Original Approach Works**: dp[i] = 1 + max(dp[j]) for j < i and nums[j] < nums[i].

**What Breaks**: Single sequence vs two sequences.

**New Constraint**: Return length of LIS.

**Algorithm Changes**: 1D DP with binary search optimization.

**Complexity Changes**: Time O(n log n) with binary search, Space O(n).