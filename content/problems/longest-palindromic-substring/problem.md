---
id: "longest-palindromic-substring"
title: "Longest Palindromic Substring"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/longest-palindromic-substring/"
patterns: ["two-pointer", "dp-1d-fundamentals"]
algorithms: ["two-pointer"]
dataStructures: ["array"]
templates: ["two-pointers-template-ts"]
prerequisites: ["array", "two-pointers"]
relatedProblems: ["valid-palindrome", "palindromic-substrings", "longest-common-subsequence", "manacher-algorithm"]
status: "PUBLISHED"
orderIndex: 44
reasonForSelection: "Expand around center O(n^2) or Manacher's O(n); DP for palindrome check"
---

# Problem Statement

Given a string `s`, return the longest palindromic substring in `s`.

## Constraints

- 1 ≤ s.length ≤ 1000
- `s` consist of only digits and English letters.

## Examples

**Example 1:**
```
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.
```

**Example 2:**
```
Input: s = "cbbd"
Output: "bb"
```

## Variations

### Variation 1: Valid Palindrome
**Name**: Valid Palindrome

**What Changed**: Check if whole string is palindrome.

**Original Approach Works**: Two pointers from ends.

**What Breaks**: Substring vs whole string.

**New Constraint**: Return true if palindrome.

**Algorithm Changes**: Two pointers from ends, skip non-alphanumeric.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Palindromic Substrings
**Name**: Palindromic Substrings

**What Changed**: Count all palindromic substrings.

**Original Approach Works**: Expand around center, count all.

**What Breaks**: Count vs find longest.

**New Constraint**: Return count of all palindromic substrings.

**Algorithm Changes**: Expand around each center, increment count.

**Complexity Changes**: Time O(n²), Space O(1).