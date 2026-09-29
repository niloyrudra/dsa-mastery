---
id: "valid-sudoku"
title: "Valid Sudoku"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/valid-sudoku/"
patterns: ["hash-map-lookup", "geometry-fundamentals"]
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts"]
prerequisites: ["array", "hash-table"]
relatedProblems: ["contains-duplicate", "sudoku-solver", "set-matrix-zeroes", "game-of-life", "n-queens"]
status: "PUBLISHED"
orderIndex: 6
reasonForSelection: "Three hash sets per row/col/box; single pass validation"
---

# Problem Statement

Determine if a `9 x 9` Sudoku board is valid. Only the filled cells need to be validated **according to the following rules**:

1. Each row must contain the digits `1-9` without repetition.
2. Each column must contain the digits `1-9` without repetition.
3. Each of the nine `3 x 3` sub-boxes of the grid must contain the digits `1-9` without repetition.

**Note:**
- A Sudoku board (partially filled) could be valid but is not necessarily solvable.
- Only the filled cells need to be validated according to the mentioned rules.

## Constraints

- board.length == 9
- board[i].length == 9
- board[i][j] is a digit `1-9` or `'.'`.

## Examples

**Example 1:**
```
Input: board = 
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: true
```

**Example 2:**
```
Input: board = 
[["8","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: false
Explanation: Same as Example 1, except with the **5** in the first row changed to **8**. There are two **8**'s in the first row.
```

## Variations

### Variation 1: Sudoku Solver
**Name**: Sudoku Solver

**What Changed**: Solve the Sudoku puzzle.

**Original Approach Works**: Backtracking with constraints.

**What Breaks**: Generation vs validation.

**New Constraint**: Fill all empty cells.

**Algorithm Changes**: Backtracking with row/col/box constraints.

**Complexity Changes**: Time O(9^(empty)), Space O(1) for 9x9.

### Variation 2: N-Queens
**Name**: N-Queens

**What Changed**: Place n queens on n x n board.

**Original Approach Works**: Backtracking with row/col/diag constraints.

**What Breaks**: Different constraints.

**New Constraint**: No two queens attack each other.

**Algorithm Changes**: Track rows, cols, diagonals.

**Complexity Changes**: Time O(n!), Space O(n).