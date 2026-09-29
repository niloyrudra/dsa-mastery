---
id: "partition-equal-subset-sum"
title: "Partition Equal Subset Sum"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/partition-equal-subset-sum/"
patterns: ["dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["target-sum", "coin-change", "last-stone-weight-ii", "subset-sum"]
status: "PUBLISHED"
orderIndex: 43
reasonForSelection: "Subset sum to target = sum/2; 0/1 knapsack variant"
---

# Problem Statement

Given an integer array `nums`, return `true` if you can partition the array into two subsets such that the sum of the elements in both subsets is equal, or `false` otherwise.

## Constraints

- 1 ≤ nums.length ≤ 200
- 1 ≤ nums[i] ≤ 100

## Examples

**Example 1:**
```
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
```

**Example 2:**
```
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
```

## Variations

### Variation 1: Target Sum
**Name**: Target Sum

**What Changed**: Assign + or - to each number to reach target.

**Original Approach Works**: Same DP, target = (sum + target) / 2.

**What Breaks**: +/- instead of partition.

**New Constraint**: Return number of ways to reach target.

**Algorithm Changes**: Subset sum to (sum + target) / 2.

**Complexity Changes**: Time O(n * target), Space O(target).

### Variation 2: Coin Change
**Name**: Coin Change

**What Changed**: Minimum coins to make amount.

**Original Approach Works**: 0/1 knapsack vs unbounded knapsack.

**What Breaks**: Unbounded vs 0/1.

**New Constraint**: Return minimum coins.

**Algorithm Changes**: Unbounded knapsack DP.

**Complexity Changes**: Time O(n * amount), Space O(amount).