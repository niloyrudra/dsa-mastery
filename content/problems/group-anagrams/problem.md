---
id: "group-anagrams"
title: "Group Anagrams"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/group-anagrams/"
patterns: ["hash-map-lookup"]
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts"]
prerequisites: ["array", "hash-table"]
relatedProblems: ["valid-anagram", "find-all-anagrams-in-a-string", "permutation-in-string"]
status: "PUBLISHED"
orderIndex: 4
reasonForSelection: "Groups strings by sorted character signature; extends hash map pattern"
---

# Problem Statement

Given an array of strings `strs`, group the anagrams together. You can return the answer in any order.

An **Anagram** is a word formed by rearranging the letters of another, using all the original letters exactly once.

## Constraints

- 1 ≤ strs.length ≤ 10⁴
- 0 ≤ strs[i].length ≤ 100
- strs[i] consists of lowercase English letters.

## Examples

**Example 1:**
```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Example 2:**
```
Input: strs = [""]
Output: [[""]]
```

**Example 3:**
```
Input: strs = ["a"]
Output: [["a"]]
```

## Variations

### Variation 1: Valid Anagram
**Name**: Valid Anagram

**What Changed**: Check if two strings are anagrams (pairwise).

**Original Approach Works**: Same frequency counting or sorting approach.

**What Breaks**: Just pairwise comparison, not grouping.

**New Constraint**: Return boolean for two strings.

**Algorithm Changes**: Yes - compare two frequency maps or sorted strings.

**Complexity Changes**: Time O(n), Space O(1) for fixed alphabet.

### Variation 2: Find All Anagrams in a String
**Name**: Find All Anagrams in a String

**What Changed**: Find all start indices of anagrams of p in s.

**Original Approach Works**: Fixed window sliding with frequency count matching.

**What Breaks**: Window size fixed = len(p), collect all matches.

**New Constraint**: Return all starting indices.

**Algorithm Changes**: Yes - fixed window size, collect indices.

**Complexity Changes**: Time O(n), Space O(1) for fixed alphabet + O(k) for results.