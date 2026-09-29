---
id: "maximum-product-subarray"
title: "Maximum Product Subarray"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/maximum-product-subarray/"
patterns: ["dp-1d-fundamentals", "prefix-sum"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["maximum-subarray", "minimum-size-subarray-sum", "product-of-array-except-self", "best-time-to-buy-and-sell-stock"]
status: "PUBLISHED"
orderIndex: 44
reasonForSelection: "Track both min and max product; negative * negative = positive"
---

# Problem Statement

Given an integer array `nums`, find a contiguous non-empty subarray within the array that has the largest product, and return the product.

The test cases are generated so that the answer will fit in a **32-bit** integer.

## Constraints

- 1 ≤ nums.length ≤ 2 × 10⁴
- -10 ≤ nums[i] ≤ 10
- The product of any prefix or suffix of `nums` is **guaranteed** to fit in a 32-bit integer.

## Examples

**Example 1:**
```
Input: nums = [2,3,-2,4]
Output: 6
Explanation: [2,3] has the largest product 6.
```

**Example 2:**
```
Input: nums = [-2,0,-1]
Output: 0
Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
```

## Variations

### Variation 1: Maximum Subarray
**Name**: Maximum Subarray

**What Changed**: Maximum sum instead of product.

**Original Approach Works**: Kadane's algorithm (track max ending here).

**What Breaks**: Sum vs product, negative handling different.

**New Constraint**: Return maximum sum.

**Algorithm Changes**: Track max ending here, update global max.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Product of Array Except Self
**Name**: Product of Array Except Self

**What Changed**: Product of all elements except self.

**Original Approach Works**: Prefix and suffix products.

**What Breaks**: No subarray, just products.

**New Constraint**: Return array of products.

**Algorithm Changes**: Left and right product arrays.

**Complexity Changes**: Time O(n), Space O(n).