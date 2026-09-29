---
id: "search-a-2d-matrix"
title: "Search a 2D Matrix"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/search-a-2d-matrix/"
patterns: ["binary-search-on-answer", "binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["find-minimum-in-rotated-sorted-array", "search-in-rotated-sorted-array", "search-insert-position", "kth-smallest-element-in-a-bst"]
status: "PUBLISHED"
orderIndex: 23
reasonForSelection: "Treat 2D matrix as 1D sorted array; virtual index mapping"
---

# Problem Statement

Write an efficient algorithm that searches for a value `target` in an `m x n` integer matrix `matrix`. This matrix has the following properties:

- Integers in each row are sorted from left to right.
- The first integer of each row is greater than the last integer of the previous row.

## Constraints

- m == matrix.length
- n == matrix[i].length
- 1 ≤ m, n ≤ 100
- -10⁴ ≤ matrix[i][j], target ≤ 10⁴

## Examples

**Example 1:**
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
Output: true
```

**Example 2:**
```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
Output: false
```

## Variations

### Variation 1: Search in Rotated Sorted Array
**Name**: Search in Rotated Sorted Array

**What Changed**: 1D rotated array instead of 2D matrix.

**Original Approach Works**: Binary search on 1D.

**What Breaks**: Rotation handling.

**New Constraint**: Return index in rotated array.

**Algorithm Changes**: Determine sorted half, search there.

**Complexity Changes**: Time O(log n), Space O(1).

### Variation 2: Search a 2D Matrix II
**Name**: Search a 2D Matrix II

**What Changed**: Matrix sorted row-wise and column-wise (not strictly 1D).

**Original Approach Works**: Start from top-right, eliminate row/col.

**What Breaks**: Not a virtual 1D array.

**New Constraint**: Return true if target exists.

**Algorithm Changes**: Start from top-right, move left/down.

**Complexity Changes**: Time O(m+n), Space O(1).