---
id: "edit-distance"
title: "Edit Distance"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/edit-distance/"
patterns: ["dp-2d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["longest-common-subsequence", "distinct-subsequences", "interleaving-string", "regular-expression-matching"]
status: "PUBLISHED"
orderIndex: 45
reasonForSelection: "Classic 2D DP on strings; Levenshtein distance"
---

# Problem Statement

Given two strings `word1` and `word2`, return the minimum number of operations required to convert `word1` to `word2`.

You have the following three operations permitted on a word:

- Insert a character
- Delete a character
- Replace a character

## Constraints

- 0 ≤ word1.length, word2.length ≤ 500
- `word1` and `word2` consist of lowercase English letters.

## Examples

**Example 1:**
```
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation:
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
```

**Example 2:**
```
Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation:
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')
```

## Variations

### Variation 1: Longest Common Subsequence
**Name**: Longest Common Subsequence

**What Changed**: Find LCS length instead of edit distance.

**Original Approach Works**: Similar 2D DP, but maximize instead of minimize.

**What Breaks**: Different recurrence.

**New Constraint**: Return LCS length.

**Algorithm Changes**: dp[i][j] = dp[i-1][j-1] + 1 if match, else max(dp[i-1][j], dp[i][j-1]).

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Distinct Subsequences
**Name**: Distinct Subsequences

**What Changed**: Count distinct subsequences matching t.

**Original Approach Works**: 2D DP on string pairs.

**What Breaks**: Counting vs minimizing.

**New Constraint**: Return count of subsequences.

**Algorithm Changes**: dp[i][j] = dp[i-1][j-1] + dp[i-1][j] if match, else dp[i-1][j].

**Complexity Changes**: Time O(m*n), Space O(m*n).