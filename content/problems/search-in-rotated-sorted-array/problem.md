---
id: "search-in-rotated-sorted-array"
title: "Search in Rotated Sorted Array"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/search-in-rotated-sorted-array/"
patterns: ["binary-search-on-answer", "binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["find-minimum-in-rotated-sorted-array", "search-a-2d-matrix", "search-insert-position", "find-peak-element"]
status: "PUBLISHED"
orderIndex: 23
reasonForSelection: "Binary search on rotated array; determine sorted half"
---

# Problem Statement

There is an integer array `nums` sorted in ascending order (with **distinct** values).

Prior to being passed to your function, `nums` is **possibly rotated** at an unknown pivot index `k` (`1 ≤ k < nums.length`) such that the resulting array is `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]` (0-indexed). For example, `[0,1,2,4,5,6,7]` might be rotated at pivot index `3` and become `[4,5,6,7,0,1,2]`.

Given the array `nums` after the possible rotation and an integer `target`, return the index of `target` if it is in `nums`, or `-1` if it is not in `nums`.

You must write an algorithm with `O(log n)` runtime complexity.

## Constraints

- 1 ≤ nums.length ≤ 5000
- -10⁴ ≤ nums[i] ≤ 10⁴
- All values of `nums` are **unique**.
- `nums` is an ascending array that is possibly rotated.
- -10⁴ ≤ target ≤ 10⁴

## Examples

**Example 1:**
```
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
```

**Example 2:**
```
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
```

**Example 3:**
```
Input: nums = [1], target = 0
Output: -1
```

## Variations

### Variation 1: Find Minimum in Rotated Sorted Array
**Name**: Find Minimum in Rotated Sorted Array

**What Changed**: Find minimum instead of target.

**Original Approach Works**: Binary search on rotation point.

**What Breaks**: Minimum is at rotation point.

**New Constraint**: Return minimum element.

**Algorithm Changes**: Compare mid with right, move left/right.

**Complexity Changes**: Time O(log n), Space O(1).

### Variation 2: Search a 2D Matrix
**Name**: Search a 2D Matrix

**What Changed**: 2D matrix with virtual 1D indexing.

**Original Approach Works**: Binary search on 1D mapping.

**What Breaks**: 2D vs 1D.

**New Constraint**: Treat matrix as 1D sorted array.

**Algorithm Changes**: Virtual index mapping: row = mid / n, col = mid % n.

**Complexity Changes**: Time O(log(m*n)), Space O(1).