---
id: "distinct-subsequences"
title: "Distinct Subsequences"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/distinct-subsequences/"
patterns: ["dp-2d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["edit-distance", "interleaving-string", "longest-common-subsequence", "decode-ways"]
status: "PUBLISHED"
orderIndex: 47
reasonForSelection: "2D DP on string pairs; count distinct subsequences"
---

# Problem Statement

Given two strings `s` and `t`, return the number of distinct subsequences of `s` which equals `t`.

A string's **subsequence** is a new string formed from the original string by deleting some (can be none) of the characters without disturbing the remaining characters' relative positions. (i.e., `"ACE"` is a subsequence of `"ABCDE"` while `"AEC"` is not).

The test cases are generated so that the answer fits in a 32-bit integer.

## Constraints

- 1 ≤ s.length, t.length ≤ 1000
- `s` and `t` consist of English letters.

## Examples

**Example 1:**
```
Input: s = "rabbbit", t = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from s.
rabbbit
rabbbit
rabbbit
```

**Example 2:**
```
Input: s = "babgbag", t = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from s.
babgbag
babgbag
babgbag
babgbag
babgbag
```

## Variations

### Variation 1: Edit Distance
**Name**: Edit Distance

**What Changed**: Minimum operations to convert s to t.

**Original Approach Works**: Same 2D DP on string pairs.

**What Breaks**: Min operations vs count subsequences.

**New Constraint**: Return minimum edit distance.

**Algorithm Changes**: dp[i][j] = min of insert/delete/replace.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Interleaving String
**Name**: Interleaving String

**What Changed**: Check if s3 is interleaving of s1 and s2.

**Original Approach Works**: 2D DP on string pairs.

**What Breaks**: Three strings, boolean result.

**New Constraint**: Return true if s3 interleaves s1 and s2.

**Algorithm Changes**: dp[i][j] = (dp[i-1][j] && s1[i-1]==s3[i+j-1]) || (dp[i][j-1] && s2[j-1]==s3[i+j-1]).

**Complexity Changes**: Time O(m*n), Space O(m*n).