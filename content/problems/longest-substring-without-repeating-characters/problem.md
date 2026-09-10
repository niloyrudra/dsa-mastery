---
id: "longest-substring-without-repeating-characters"
title: "Longest Substring Without Repeating Characters"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/longest-substring-without-repeating-characters/"
patterns: ["sliding-window-variable", "hash-map-lookup"]
algorithms: ["sliding-window-variable"]
dataStructures: ["hash-map", "array", "string"]
templates: ["sliding-window-variable", "sliding-window-char-set"]
prerequisites: ["arrays", "hash-map", "string"]
relatedProblems: ["longest-substring-with-at-most-k-distinct", "substring-with-concatenation"]
status: "PUBLISHED"
orderIndex: 18
reasonForSelection: "Classic variable sliding window with hash map; teaches window expansion/contraction"
---

# Problem Statement

Given a string `s`, find the length of the longest substring without repeating characters.

## Constraints

- 0 ≤ s.length ≤ 5 × 10⁴
- s consists of English letters, digits, symbols and spaces.

## Examples

**Example 1:**
```
Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
```

**Example 2:**
```
Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
```

**Example 3:**
```
Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
```

## Variations

### Variation 1: Longest Substring with At Most K Distinct
**Name**: Longest Substring with At Most K Distinct Characters

**What Changed**: Allow up to K distinct characters instead of no repeats.

**Original Approach Works**: Sliding window still works, but frequency map replaces set.

**What Breaks**: Set-based uniqueness check no longer applies.

**New Constraint**: Maximum K distinct characters in window.

**Algorithm Changes**: Yes - use frequency map instead of set, track distinct count.

**Complexity Changes**: Time O(n) → O(n), Space O(min(n,m)) → O(min(n,m)).