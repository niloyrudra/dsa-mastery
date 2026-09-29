---
id: "find-all-anagrams-in-a-string"
title: "Find All Anagrams in a String"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/find-all-anagrams-in-a-string/"
patterns: ["sliding-window-variable"]
algorithms: []
dataStructures: ["hash-table"]
templates: ["sliding-window-template-ts"]
prerequisites: ["hash-table", "sliding-window"]
relatedProblems: ["minimum-window-substring", "permutation-in-string", "valid-anagram", "group-anagrams"]
status: "PUBLISHED"
orderIndex: 18
reasonForSelection: "Fixed window size equals p length; character count matching"
---

# Problem Statement

Given two strings `s` and `p`, return an array of all the start indices of `p`'s anagrams in `s`. You may return the answer in any order.

## Constraints

- 1 ≤ s.length, p.length ≤ 3 × 10⁴
- `s` and `p` consist of lowercase English letters.

## Examples

**Example 1:**
```
Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".
```

**Example 2:**
```
Input: s = "abab", p = "ab"
Output: [0,1,2]
Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
```

## Variations

### Variation 1: Permutation in String
**Name**: Permutation in String

**What Changed**: Return boolean instead of all indices.

**Original Approach Works**: Same fixed window, return true on first match.

**What Breaks**: Don't need to collect all matches.

**New Constraint**: Return true if any anagram exists.

**Algorithm Changes**: Return true immediately when window matches.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Minimum Window Substring
**Name**: Minimum Window Substring

**What Changed**: Variable window, find minimum containing all chars of t.

**Original Approach Works**: Sliding window with frequency maps.

**What Breaks**: Window size not fixed, need minimum valid window.

**New Constraint**: Return minimum window substring.

**Algorithm Changes**: Variable window, track formed count, shrink when valid.

**Complexity Changes**: Time O(n + m), Space O(n + m).