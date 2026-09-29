---
id: "word-break"
title: "Word Break"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/word-break/"
patterns: ["dp-1d-fundamentals", "trie-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming", "trie"]
relatedProblems: ["climbing-stairs", "word-break-ii", "palindrome-partitioning", "decode-ways", "concatenated-words"]
status: "PUBLISHED"
orderIndex: 42
reasonForSelection: "1D DP with dictionary lookup; dp[i] = true if word ending at i"
---

# Problem Statement

Given a string `s` and a dictionary of strings `wordDict`, return `true` if `s` can be segmented into a space-separated sequence of one or more dictionary words.

**Note** that the same word in the dictionary may be reused multiple times in the segmentation.

## Constraints

- 1 ≤ s.length ≤ 300
- 1 ≤ wordDict.length ≤ 1000
- 1 ≤ wordDict[i].length ≤ 20
- `s` and `wordDict[i]` consist of only lowercase English letters.
- All the strings of `wordDict` are **unique**.

## Examples

**Example 1:**
```
Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
```

**Example 2:**
```
Input: s = "applepenapple", wordDict = ["apple","pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
Note that you can reuse dictionary words.
```

**Example 3:**
```
Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false
```

## Variations

### Variation 1: Word Break II
**Name**: Word Break II

**What Changed**: Return all possible sentences.

**Original Approach Works**: DP + backtracking for paths.

**What Breaks**: All paths vs boolean.

**New Constraint**: Return all sentences.

**Algorithm Changes**: DP for feasibility, backtrack for paths.

**Complexity Changes**: Time O(n² * 2ⁿ), Space O(n).

### Variation 2: Palindrome Partitioning
**Name**: Palindrome Partitioning

**What Changed**: Palindrome substrings instead of dictionary words.

**Original Approach Works**: DP with palindrome check.

**What Breaks**: Palindrome vs dictionary.

**New Constraint**: All palindrome partitions.

**Algorithm Changes**: Backtracking with palindrome check.

**Complexity Changes**: Time O(n * 2ⁿ), Space O(n).