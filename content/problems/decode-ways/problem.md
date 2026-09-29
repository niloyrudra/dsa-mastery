---
id: "decode-ways"
title: "Decode Ways"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/decode-ways/"
patterns: ["dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming", "recursion"]
relatedProblems: ["climbing-stairs", "coin-change", "word-break", "distinct-subsequences"]
status: "PUBLISHED"
orderIndex: 41
reasonForSelection: "1D DP with two-digit validation; string to number mapping"
---

# Problem Statement

A message containing letters from `A-Z` can be **encoded** into numbers using the following mapping:

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"

To **decode** an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, `"11106"` can be mapped into:

- `"AAJF"` with the grouping `(1 1 10 6)`
- `"KJF"` with the grouping `(11 10 6)`

Note that the grouping `(1 11 06)` is invalid because `"06"` cannot be mapped into `'F'` since `"6"` is different from `"06"`.

Given a string `s` containing only digits, return the **number of ways** to decode it.

The test cases are generated so that the answer fits in a **32-bit** integer.

## Constraints

- 1 ≤ s.length ≤ 100
- s contains only digits and may contain leading zero(s).

## Examples

**Example 1:**
```
Input: s = "12"
Output: 2
Explanation: "12" could be decoded as "AB" (1 2) or "L" (12).
```

**Example 2:**
```
Input: s = "226"
Output: 3
Explanation: "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
```

**Example 3:**
```
Input: s = "06"
Output: 0
Explanation: "06" cannot be mapped to "F" because of the leading zero.
```

## Variations

### Variation 1: Climbing Stairs
**Name**: Climbing Stairs

**What Changed**: Count ways to climb n steps (1 or 2 steps).

**Original Approach Works**: Same DP recurrence: dp[i] = dp[i-1] + dp[i-2].

**What Breaks**: No string validation needed.

**New Constraint**: Steps 1 or 2, no encoding.

**Algorithm Changes**: Simpler - no digit validation.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Word Break
**Name**: Word Break

**What Changed**: Check if string can be segmented into dictionary words.

**Original Approach Works**: DP with dictionary lookup instead of digit validation.

**What Breaks**: Dictionary of words instead of fixed 1-26 mapping.

**New Constraint**: Return boolean if word break possible.

**Algorithm Changes**: dp[i] = true if dp[j] && s[j..i] in dict.

**Complexity Changes**: Time O(n²), Space O(n).