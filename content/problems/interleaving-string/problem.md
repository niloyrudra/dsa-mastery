---
id: "interleaving-string"
title: "Interleaving String"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/interleaving-string/"
patterns: ["dp-2d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["edit-distance", "distinct-subsequences", "longest-common-subsequence", "regular-expression-matching"]
status: "PUBLISHED"
orderIndex: 48
reasonForSelection: "2D DP on string indices; s3 formed by interleaving s1 and s2"
---

# Problem Statement

Given strings `s1`, `s2`, and `s3`, find whether `s3` is formed by an **interleaving** of `s1` and `s2`.

An **interleaving** of two strings `s` and `t` is a configuration where `s` and `t` are divided into `n` and `m` substrings respectively, such that:

- `s = s1 + s2 + ... + sn`
- `t = t1 + t2 + ... + tm`
- `|n - m| ≤ 1`
- The **interleaving** is `s1 + t1 + s2 + t2 + s3 + t3 + ...` or `t1 + s1 + t2 + s2 + ...`

**Note:** `a + b` is the concatenation of strings `a` and `b`.

## Constraints

- 0 ≤ s1.length, s2.length ≤ 100
- 0 ≤ s3.length ≤ 200
- `s1`, `s2`, and `s3` consist of lowercase English letters.

## Examples

**Example 1:**
```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
Output: true
Explanation: One way to interleave is "aa" + "dbbc" + "bc" + "a" + "c" + "a" + "c" + "b" + "a" = s3.
```

**Example 2:**
```
Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
Output: false
```

**Example 3:**
```
Input: s1 = "", s2 = "", s3 = ""
Output: true
```

## Variations

### Variation 1: Edit Distance
**Name**: Edit Distance

**What Changed**: Minimum operations to convert s1 to s2.

**Original Approach Works**: Same 2D DP on string pairs.

**What Breaks**: Min operations vs boolean interleaving check.

**New Constraint**: Return minimum edit distance.

**Algorithm Changes**: dp[i][j] = min of insert/delete/replace.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Distinct Subsequences
**Name**: Distinct Subsequences

**What Changed**: Count distinct subsequences of s matching t.

**Original Approach Works**: 2D DP on string pairs.

**What Breaks**: Counting vs boolean.

**New Constraint**: Return count of subsequences.

**Algorithm Changes**: dp[i][j] = dp[i-1][j-1] + dp[i-1][j] if match, else dp[i-1][j].

**Complexity Changes**: Time O(m*n), Space O(m*n).