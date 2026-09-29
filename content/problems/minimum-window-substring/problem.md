---
id: "minimum-window-substring"
title: "Minimum Window Substring"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/minimum-window-substring/"
patterns: ["sliding-window-variable"]
algorithms: []
dataStructures: ["hash-table"]
templates: ["sliding-window-template-ts"]
prerequisites: ["hash-table", "sliding-window"]
relatedProblems: ["longest-substring-without-repeating-characters", "permutation-in-string", "longest-repeating-character-replacement"]
status: "PUBLISHED"
orderIndex: 17
reasonForSelection: "Advanced variable sliding window with character frequency matching"
---

# Problem Statement

Given two strings `s` and `t` of lengths `m` and `n` respectively, return the **minimum window substring** of `s` such that every character in `t` (including duplicates) is included in the window. If there is no such substring, return the empty string `""`.

The testcases will be generated such that the answer is **unique**.

## Constraints

- m == s.length
- n == t.length
- 1 ≤ m, n ≤ 10⁵
- s and t consist of uppercase and lowercase English letters.

## Examples

**Example 1:**
```
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
```

**Example 2:**
```
Input: s = "a", t = "a"
Output: "a"
```

**Example 3:**
```
Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window. Since the largest window of s only has one 'a', return empty string.
```

## Variations

### Variation 1: Permutation in String
**Name**: Permutation in String

**What Changed**: Check if any permutation of t exists as substring of s (exact match, not minimum).

**Original Approach Works**: Sliding window with frequency map, but check for exact match of all counts.

**What Breaks**: Window size is fixed = len(t), not variable.

**New Constraint**: Window must be exactly length of t.

**Algorithm Changes**: Yes - fixed window size, check if frequency maps match.

**Complexity Changes**: Time O(n), Space O(1) for fixed alphabet.

### Variation 2: Longest Repeating Character Replacement
**Name**: Longest Repeating Character Replacement

**What Changed**: Can replace up to k characters to make all same; find longest such window.

**Original Approach Works**: Sliding window with frequency map, validity condition is (window length - max frequency) ≤ k.

**What Breaks**: Need to track max frequency in window efficiently.

**New Constraint**: At most k replacements allowed.

**Algorithm Changes**: Yes - track max count in window, shrink when (right - left + 1) - maxCount > k.

**Complexity Changes**: Time O(n), Space O(1).