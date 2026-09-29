---
id: "permutations"
title: "Permutations"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/permutations/"
patterns: ["backtracking-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion"]
relatedProblems: ["subsets", "permutations-ii", "n-queens", "next-permutation", "permutation-sequence"]
status: "PUBLISHED"
orderIndex: 56
reasonForSelection: "Classic backtracking with swap; all n! permutations"
---

# Problem Statement

Given an array `nums` of **distinct** integers, return all the possible permutations. You can return the answer in any order.

## Constraints

- 1 ≤ nums.length ≤ 6
- -10 ≤ nums[i] ≤ 10
- All the integers of `nums` are **unique**.

## Examples

**Example 1:**
```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

**Example 2:**
```
Input: nums = [0,1]
Output: [[0,1],[1,0]]
```

**Example 3:**
```
Input: nums = [1]
Output: [[1]]
```

## Variations

### Variation 1: Permutations II
**Name**: Permutations II

**What Changed**: Array may contain duplicates.

**Original Approach Works**: Same backtracking, skip duplicates.

**What Breaks**: Duplicate permutations.

**New Constraint**: Return unique permutations.

**Algorithm Changes**: Sort array, skip if used[i-1] && nums[i] == nums[i-1].

**Complexity Changes**: Time O(n! * n), Space O(n).

### Variation 2: Subsets
**Name**: Subsets

**What Changed**: Generate all subsets (not permutations).

**Original Approach Works**: Backtracking with include/exclude.

**What Breaks**: Order doesn't matter, include/exclude.

**New Constraint**: Return all subsets.

**Algorithm Changes**: Include/exclude at each step.

**Complexity Changes**: Time O(2ⁿ * n), Space O(n).