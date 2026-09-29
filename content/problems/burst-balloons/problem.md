---
id: "burst-balloons"
title: "Burst Balloons"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/burst-balloons/"
patterns: ["dp-2d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["maximum-subarray", "matrix-chain-multiplication", "remove-boxes"]
status: "PUBLISHED"
orderIndex: 46
reasonForSelection: "Interval DP; reverse thinking - last balloon burst"
---

# Problem Statement

You are given `n` balloons, indexed from `0` to `n - 1`. Each balloon is painted with a number on it represented by an array `nums`. You are asked to burst all the balloons.

If you burst the `i`th balloon, you will get `nums[left] * nums[i] * nums[right]` coins. Here `left` and `right` are adjacent indices of `i`. After the burst, the `left` and `right` then becomes adjacent.

Return the maximum coins you can collect by bursting the balloons wisely.

## Constraints

- n == nums.length
- 1 ≤ n ≤ 300
- 0 ≤ nums[i] ≤ 100

## Examples

**Example 1:**
```
Input: nums = [3,1,5,8]
Output: 167
Explanation: 
nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
coins =  3*1*5 + 3*5*8 + 1*3*8 + 1*8*1 = 15 + 120 + 24 + 8 = 167
```

**Example 2:**
```
Input: nums = [1,5]
Output: 10
```

## Variations

### Variation 1: Remove Boxes
**Name**: Remove Boxes

**What Changed**: Boxes with colors, remove contiguous same-color boxes for points = k².

**Original Approach Works**: Interval DP with extra state for contiguous same-color count.

**What Breaks**: Need 3D DP (i, j, k) where k = count of same color as boxes[i] to the left.

**New Constraint**: Points = k² for removing k same-color boxes.

**Algorithm Changes**: 3D DP with memoization.

**Complexity Changes**: Time O(n⁴), Space O(n³).

### Variation 2: Matrix Chain Multiplication
**Name**: Matrix Chain Multiplication

**What Changed**: Find optimal parenthesization for matrix multiplication.

**Original Approach Works**: Same interval DP structure.

**What Breaks**: Cost function is matrix multiplication cost.

**New Constraint**: Minimize scalar multiplications.

**Algorithm Changes**: dp[i][j] = min over k of dp[i][k] + dp[k+1][j] + cost.

**Complexity Changes**: Time O(n³), Space O(n²).