---
id: "rotate-image"
title: "Rotate Image"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/rotate-image/"
patterns: ["geometry-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["spiral-matrix", "set-matrix-zeroes", "game-of-life", "valid-sudoku", "maximal-square"]
status: "PUBLISHED"
orderIndex: 62
reasonForSelection: "In-place matrix rotation; transpose + reverse rows"
---

# Problem Statement

You are given an `n x n` 2D matrix representing an image, rotate the image by **90** degrees (clockwise).

You have to rotate the image **in-place**, which means you have to modify the input 2D matrix directly. **DO NOT** allocate another 2D matrix and do the rotation.

## Constraints

- n == matrix.length == matrix[i].length
- 1 ≤ n ≤ 20
- -1000 ≤ matrix[i][j] ≤ 1000

## Examples

**Example 1:**
```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [[7,4,1],[8,5,2],[9,6,3]]
```

**Example 2:**
```
Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
Output: [[15,13,2,5],[14,3,4,16],[12,6,8,10],[16,10,11,11]]
```

## Variations

### Variation 1: Spiral Matrix
**Name**: Spiral Matrix

**What Changed**: Return elements in spiral order.

**Original Approach Works**: Simulate spiral with boundaries.

**What Breaks**: Traversal order vs rotation.

**New Constraint**: Return spiral order array.

**Algorithm Changes**: Four boundaries, traverse in order.

**Complexity Changes**: Time O(m*n), Space O(1) extra.

### Variation 2: Transpose Matrix
**Name**: Transpose Matrix

**What Changed**: Transpose without rotation.

**Original Approach Works**: Swap matrix[i][j] with matrix[j][i].

**What Breaks**: No row reversal.

**New Constraint**: Return transpose.

**Algorithm Changes**: Swap for i < j.

**Complexity Changes**: Time O(n²), Space O(1).