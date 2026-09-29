---
id: "maximum-subarray"
title: "Maximum Subarray"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/maximum-subarray/"
patterns: ["greedy-fundamentals", "dp-1d-fundamentals", "prefix-sum"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["maximum-product-subarray", "minimum-size-subarray-sum", "best-time-to-buy-and-sell-stock", "jump-game", "maximum-sum-circular-subarray"]
status: "PUBLISHED"
orderIndex: 50
reasonForSelection: "Kadane's algorithm; greedy vs DP; maximum subarray sum"
---

# Problem Statement

Given an integer array `nums`, find the subarray with the largest sum, and return its sum.

## Constraints

- 1 ≤ nums.length ≤ 10⁵
- -10⁴ ≤ nums[i] ≤ 10⁴

## Examples

**Example 1:**
```
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: The subarray [4,-1,2,1] has the largest sum 6.
```

**Example 2:**
```
Input: nums = [1]
Output: 1
```

**Example 3:**
```
Input: nums = [5,4,-1,7,8]
Output: 23
```

## Variations

### Variation 1: Maximum Product Subarray
**Name**: Maximum Product Subarray

**What Changed**: Maximum product instead of sum.

**Original Approach Works**: Kadane's variant with min/max tracking.

**What Breaks**: Negative * negative = positive.

**New Constraint**: Return maximum product.

**Algorithm Changes**: Track min and max ending here.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Minimum Size Subarray Sum
**Name**: Minimum Size Subarray Sum

**What Changed**: Minimum length with sum ≥ target.

**Original Approach Works**: Sliding window or prefix sum + binary search.

**What Breaks**: Minimum length constraint.

**New Constraint**: Return minimum length.

**Algorithm Changes**: Sliding window or prefix sum + binary search.

**Complexity Changes**: Time O(n) or O(n log n), Space O(1) or O(n).