---
id: "longest-substring-without-repeating-characters"
title: "Longest Substring Without Repeating Characters"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/longest-substring-without-repeating-characters/"
patterns: ["sliding-window-variable"]
algorithms: []
dataStructures: ["hash-table"]
templates: ["sliding-window-template-ts"]
prerequisites: ["hash-table", "sliding-window"]
relatedProblems: ["minimum-window-substring", "permutation-in-string", "longest-repeating-character-replacement"]
status: "PUBLISHED"
orderIndex: 16
reasonForSelection: "Classic variable sliding window with hash map for character tracking"
---

# Problem Statement

Given a string `s`, find the length of the **longest substring** without repeating characters.

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
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
```

## Variations

### Variation 1: Longest Substring with At Most K Distinct Characters
**Name**: Longest Substring with At Most K Distinct Characters

**What Changed**: Allow up to K distinct characters instead of all unique.

**Original Approach Works**: Sliding window with frequency map, but window valid when distinct ≤ K.

**What Breaks**: Must track distinct count and shrink when it exceeds K.

**New Constraint**: Window can contain up to K distinct characters.

**Algorithm Changes**: Yes - maintain distinct count in frequency map.

**Complexity Changes**: Time O(n), Space O(K).

### Variation 2: Minimum Window Substring
**Name**: Minimum Window Substring

**What Changed**: Find minimum window containing all characters of target string.

**Original Approach Works**: Variable sliding window with frequency map, but validity is different.

**What Breaks**: Need to track if window contains all required characters with sufficient counts.

**New Constraint**: Window must contain all characters from target string.

**Algorithm Changes**: Yes - two frequency maps (window and target), track formed count.

**Complexity Changes**: Time O(n + m), Space O(n + m).