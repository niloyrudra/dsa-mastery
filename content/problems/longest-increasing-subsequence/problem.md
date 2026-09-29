---
id: "longest-increasing-subsequence"
title: "Longest Increasing Subsequence"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/longest-increasing-subsequence/"
patterns: ["dp-1d-fundamentals", "binary-search-on-answer"]
algorithms: []
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "dynamic-programming", "binary-search"]
relatedProblems: ["climbing-stairs", "longest-common-subsequence", "russian-doll-envelopes", "maximum-length-of-pair-chain"]
status: "PUBLISHED"
orderIndex: 43
reasonForSelection: "LIS with O(n log n) binary search optimization; patience sorting"
---

# Problem Statement

Given an integer array `nums`, return the length of the longest strictly increasing subsequence.

## Constraints

- 1 ≤ nums.length ≤ 2500
- -10⁴ ≤ nums[i] ≤ 10⁴

## Examples

**Example 1:**
```
Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
```

**Example 2:**
```
Input: nums = [0,1,0,3,2,3]
Output: 4
```

**Example 3:**
```
Input: nums = [7,7,7,7,7,7,7]
Output: 1
```

## Variations

### Variation 1: Russian Doll Envelopes
**Name**: Russian Doll Envelopes

**What Changed**: 2D envelopes (w, h), fit if both dimensions smaller.

**Original Approach Works**: Sort by width asc, height desc, then LIS on height.

**What Breaks**: Two dimensions.

**New Constraint**: Return max envelopes nested.

**Algorithm Changes**: Sort, then LIS on height with binary search.

**Complexity Changes**: Time O(n log n), Space O(n).

### Variation 2: Maximum Length of Pair Chain
**Name**: Maximum Length of Pair Chain

**What Changed**: Pairs (a, b), chain if b < c for next pair (c, d).

**Original Approach Works**: Sort by end, then greedy or LIS.

**What Breaks**: Pairs instead of single values.

**New Constraint**: Return max chain length.

**Algorithm Changes**: Sort by end, greedy pick earliest end.

**Complexity Changes**: Time O(n log n), Space O(1).