---
id: "permutation-in-string"
title: "Permutation in String"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/permutation-in-string/"
patterns: ["sliding-window-variable"]
algorithms: []
dataStructures: ["hash-table"]
templates: ["sliding-window-template-ts"]
prerequisites: ["hash-table", "sliding-window"]
relatedProblems: ["minimum-window-substring", "longest-substring-without-repeating-characters", "find-all-anagrams-in-a-string"]
status: "PUBLISHED"
orderIndex: 18
reasonForSelection: "Fixed window size equals s1 length; character count matching"
---

# Problem Statement

Given two strings `s1` and `s2`, return `true` if `s2` contains a permutation of `s1`, or `false` otherwise.

In other words, return `true` if one of `s1`'s permutations is a substring of `s2`.

## Constraints

- 1 ≤ s1.length, s2.length ≤ 10⁴
- s1 and s2 consist of lowercase English letters.

## Examples

**Example 1:**
```
Input: s1 = "ab", s2 = "eidbaooo"
Output: true
Explanation: s2 contains one permutation of s1 ("ba").
```

**Example 2:**
```
Input: s1 = "ab", s2 = "eidboaoo"
Output: false
```

## Variations

### Variation 1: Find All Anagrams in a String
**Name**: Find All Anagrams in a String

**What Changed**: Return all start indices of anagrams instead of just true/false.

**Original Approach Works**: Same sliding window, but collect all valid start positions.

**What Breaks**: Need to store results, not just return boolean.

**New Constraint**: Return list of starting indices.

**Algorithm Changes**: Yes - collect indices when window matches.

**Complexity Changes**: Time O(n), Space O(1) for fixed alphabet + O(k) for results.