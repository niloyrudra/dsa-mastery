---
id: "set-matrix-zeroes"
title: "Set Matrix Zeroes"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/set-matrix-zeroes/"
patterns: ["geometry-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["rotate-image", "spiral-matrix", "game-of-life", "valid-sudoku", "maximal-square"]
status: "PUBLISHED"
orderIndex: 62
reasonForSelection: "O(1) space using first row/col as markers; two-pass approach"
---

# Problem Statement

Given an `m x n` integer matrix `matrix`, if an element is `0`, set its entire row and column to `0`'s.

You must do it **in place**.

## Constraints

- m == matrix.length
- n == matrix[i].length
- 1 ≤ m, n ≤ 200
- -2³¹ ≤ matrix[i][j] ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
Output: [[1,0,1],[0,0,0],[1,0,1]]
```

**Example 2:**
```
Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
```

## Variations

### Variation 1: Game of Life
**Name**: Game of Life

**What Changed**: Cellular automaton, next state based on neighbors.

**Original Approach Works**: In-place with state encoding.

**What Breaks**: Different state transitions.

**New Constraint**: Return next state in-place.

**Algorithm Changes**: Encode states: 0->0, 1->1, 0->1 = 2, 1->0 = 3.

**Complexity Changes**: Time O(m*n), Space O(1).

### Variation 2: Valid Sudoku
**Name**: Valid Sudoku

**What Changed**: Check valid Sudoku board.

**Original Approach Works**: Check rows, cols, 3x3 boxes.

**What Breaks**: Validation vs modification.

**New Constraint**: Return true if valid.

**Algorithm Changes**: Hash sets for rows, cols, boxes.

**Complexity Changes**: Time O(1) for 9x9, Space O(1).