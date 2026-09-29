---
id: "subsets"
title: "Subsets"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/subsets/"
patterns: ["backtracking-fundamentals", "bit-manipulation-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion", "bit-manipulation-fundamentals"]
relatedProblems: ["permutations", "subsets-ii", "combination-sum", "letter-combinations-of-a-phone-number"]
status: "PUBLISHED"
orderIndex: 55
reasonForSelection: "Backtracking or bitmask; all 2^n subsets"
---

# Problem Statement

Given an integer array `nums` of **unique** elements, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.

## Constraints

- 1 ≤ nums.length ≤ 10
- -10 ≤ nums[i] ≤ 10
- All the elements of `nums` are **unique**.

## Examples

**Example 1:**
```
Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```

**Example 2:**
```
Input: nums = [0]
Output: [[],[0]]
```

## Variations

### Variation 1: Subsets II
**Name**: Subsets II

**What Changed**: Array may contain duplicates.

**Original Approach Works**: Sort + backtracking with skip duplicates.

**What Breaks**: Duplicate subsets.

**New Constraint**: Return unique subsets.

**Algorithm Changes**: Sort, skip duplicates: if i > start && nums[i] == nums[i-1] continue.

**Complexity Changes**: Time O(2ⁿ * n), Space O(n).

### Variation 2: Permutations
**Name**: Permutations

**What Changed**: Generate all permutations (order matters).

**Original Approach Works**: Backtracking with used array or swap.

**What Breaks**: Order matters.

**New Constraint**: Return all permutations.

**Algorithm Changes**: Swap-based or used array.

**Complexity Changes**: Time O(n!), Space O(n).