---
id: "permutations-ii"
title: "Permutations II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/permutations-ii/"
patterns: ["backtracking-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion"]
relatedProblems: ["permutations", "subsets-ii", "combination-sum-ii", "next-permutation"]
status: "PUBLISHED"
orderIndex: 57
reasonForSelection: "Backtracking with duplicate skipping; sort + skip duplicates"
---

# Problem Statement

Given a collection of numbers, `nums`, that might contain duplicates, return all possible unique permutations **in any order**.

## Constraints

- 1 ≤ nums.length ≤ 8
- -10 ≤ nums[i] ≤ 10

## Examples

**Example 1:**
```
Input: nums = [1,1,2]
Output: [[1,1,2],[1,2,1],[2,1,1]]
```

**Example 2:**
```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
```

## Variations

### Variation 1: Permutations
**Name**: Permutations

**What Changed**: Array has distinct integers.

**Original Approach Works**: Simple backtracking with swap.

**What Breaks**: No duplicates to handle.

**New Constraint**: Distinct elements.

**Algorithm Changes**: Swap-based backtracking.

**Complexity Changes**: Time O(n!), Space O(n).

### Variation 2: Subsets II
**Name**: Subsets II

**What Changed**: Generate subsets with duplicates.

**Original Approach Works**: Backtracking with sort + skip.

**What Breaks**: Subsets not permutations.

**New Constraint**: Return all unique subsets.

**Algorithm Changes**: Sort, include/exclude, skip duplicates.

**Complexity Changes**: Time O(2ⁿ), Space O(n).