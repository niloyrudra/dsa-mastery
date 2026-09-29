---
id: "valid-anagram"
title: "Valid Anagram"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/valid-anagram/"
patterns: ["hash-map-lookup"]
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts"]
prerequisites: ["array", "hash-table"]
relatedProblems: ["group-anagrams", "find-all-anagrams-in-a-string", "permutation-in-string"]
status: "PUBLISHED"
orderIndex: 3
reasonForSelection: "Classic anagram detection using frequency counting"
---

# Problem Statement

Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.

An **Anagram** is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all the original letters exactly once.

## Constraints

- 1 ≤ s.length, t.length ≤ 5 × 10⁴
- s and t consist of lowercase English letters.

## Examples

**Example 1:**
```
Input: s = "anagram", t = "nagaram"
Output: true
```

**Example 2:**
```
Input: s = "rat", t = "car"
Output: false
```

## Variations

### Variation 1: Group Anagrams
**Name**: Group Anagrams

**What Changed**: Group all anagrams together from an array of strings.

**Original Approach Works**: Sort each string or use frequency count as key in hash map.

**What Breaks**: Need to handle multiple groups, not just pairwise comparison.

**New Constraint**: Return list of groups of anagrams.

**Algorithm Changes**: Yes - hash map with sorted string or frequency tuple as key, values are lists.

**Complexity Changes**: Time O(n * k log k) with sorting or O(n * k) with counting, Space O(n * k).

### Variation 2: Find All Anagrams in a String
**Name**: Find All Anagrams in a String

**What Changed**: Find all start indices of anagrams of p in s.

**Original Approach Works**: Fixed window sliding with frequency count matching.

**What Breaks**: Window size fixed = len(p), collect all matches.

**New Constraint**: Return all starting indices.

**Algorithm Changes**: Yes - fixed window size, collect indices.

**Complexity Changes**: Time O(n), Space O(1) for fixed alphabet + O(k) for results.