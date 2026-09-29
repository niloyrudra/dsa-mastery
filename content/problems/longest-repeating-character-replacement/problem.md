---
id: "longest-repeating-character-replacement"
title: "Longest Repeating Character Replacement"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/longest-repeating-character-replacement/"
patterns: ["sliding-window-variable"]
algorithms: []
dataStructures: ["hash-table"]
templates: ["sliding-window-template-ts"]
prerequisites: ["hash-table", "sliding-window"]
relatedProblems: ["longest-substring-without-repeating-characters", "minimum-window-substring", "permutation-in-string"]
status: "PUBLISHED"
orderIndex: 19
reasonForSelection: "Variable sliding window with character count; max frequency tracking"
---

# Problem Statement

You are given a string `s` and an integer `k`. You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most `k` times.

Return the length of the longest substring containing the same letter you can get after performing the above operations.

## Constraints

- 1 ≤ s.length ≤ 10⁵
- 0 ≤ k ≤ s.length
- s consists of uppercase English letters.

## Examples

**Example 1:**
```
Input: s = "ABAB", k = 2
Output: 4
Explanation: Replace the two 'A's with two 'B's or vice versa.
```

**Example 2:**
```
Input: s = "AABABBA", k = 1
Output: 4
Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters with length 4.
```

## Variations

### Variation 1: Minimum Window Substring
**Name**: Minimum Window Substring

**What Changed**: Find minimum window containing all characters of target.

**Original Approach Works**: Sliding window with frequency map, but validity is different.

**What Breaks**: Need to track if window contains all required characters with sufficient counts.

**New Constraint**: Window must contain all characters from target string.

**Algorithm Changes**: Yes - two frequency maps (window and target), track formed count.

**Complexity Changes**: Time O(n + m), Space O(n + m).