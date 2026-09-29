---
id: "product-of-array-except-self"
title: "Product of Array Except Self"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/product-of-array-except-self/"
patterns: ["prefix-sum", "dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["maximum-subarray", "maximum-product-subarray", "contains-duplicate", "find-the-duplicate-number"]
status: "PUBLISHED"
orderIndex: 3
reasonForSelection: "Prefix and suffix products; O(n) time, O(1) extra space"
---

# Problem Statement

Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all the elements of `nums` except `nums[i]`.

The product of any prefix or suffix of `nums` is **guaranteed** to fit in a **32-bit** integer.

You must write an algorithm that runs in `O(n)` time and without using the division operation.

## Constraints

- 2 ≤ nums.length ≤ 10⁵
- -30 ≤ nums[i] ≤ 30
- The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer.

## Examples

**Example 1:**
```
Input: nums = [1,2,3,4]
Output: [24,12,8,6]
```

**Example 2:**
```
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
```

## Variations

### Variation 1: Maximum Product Subarray
**Name**: Maximum Product Subarray

**What Changed**: Maximum product of contiguous subarray.

**Original Approach Works**: Track min/max ending here.

**What Breaks**: Product vs prefix/suffix.

**New Constraint**: Return maximum product.

**Algorithm Changes**: Track min and max ending here.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Contains Duplicate
**Name**: Contains Duplicate

**What Changed**: Check if duplicate exists.

**Original Approach Works**: Hash set.

**What Breaks**: Boolean vs product array.

**New Constraint**: Return true if duplicate.

**Algorithm Changes**: Hash set lookup.

**Complexity Changes**: Time O(n), Space O(n).