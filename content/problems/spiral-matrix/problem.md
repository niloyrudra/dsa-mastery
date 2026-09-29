---
id: "spiral-matrix"
title: "Spiral Matrix"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/spiral-matrix/"
patterns: ["geometry-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["rotate-image", "set-matrix-zeroes", "game-of-life", "valid-sudoku", "spiral-matrix-ii"]
status: "PUBLISHED"
orderIndex: 63
reasonForSelection: "Boundary tracking with four directions; shrink boundaries"
---

# Problem Statement

Given an `m x n` matrix, return all elements of the matrix in spiral order.

## Constraints

- m == matrix.length
- n == matrix[i].length
- 1 ≤ m, n ≤ 10
- -100 ≤ matrix[i][j] ≤ 100

## Examples

**Example 1:**
```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
```

**Example 2:**
```
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
```

## Variations

### Variation 1: Rotate Image
**Name**: Rotate Image

**What Changed**: In-place 90 degree rotation.

**Original Approach Works**: Transpose + reverse rows.

**What Breaks**: Rotation vs spiral traversal.

**New Constraint**: Rotate matrix in-place.

**Algorithm Changes**: Transpose, then reverse each row.

**Complexity Changes**: Time O(n²), Space O(1).

### Variation 2: Spiral Matrix II
**Name**: Spiral Matrix II

**What Changed**: Generate spiral matrix of size n.

**Original Approach Works**: Simulate spiral, fill values.

**What Breaks**: Fill vs traverse.

**New Constraint**: Generate n x n spiral matrix.

**Algorithm Changes**: Four boundaries, fill in spiral order.

**Complexity Changes**: Time O(n²), Space O(1) extra.