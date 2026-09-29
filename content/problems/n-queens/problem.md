---
id: "n-queens"
title: "N-Queens"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/n-queens/"
patterns: ["backtracking-fundamentals", "bit-manipulation-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion", "bit-manipulation-fundamentals"]
relatedProblems: ["permutations", "generate-parentheses", "palindrome-partitioning", "sudoku-solver"]
status: "PUBLISHED"
orderIndex: 58
reasonForSelection: "Backtracking with diagonal/column/row constraints; bitmask optimization"
---

# Problem Statement

The **n-queens** puzzle is the problem of placing `n` queens on an `n x n` chessboard such that no two queens attack each other.

Given an integer `n`, return all distinct solutions to the **n-queens puzzle**. You may return the answer in any order.

Each solution contains a distinct board configuration of the n-queens' placement, where `'Q'` and `'.'` both indicate a queen and an empty space, respectively.

## Constraints

- 1 ≤ n ≤ 9

## Examples

**Example 1:**
```
Input: n = 4
Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.
```

**Example 2:**
```
Input: n = 1
Output: [["Q"]]
```

## Variations

### Variation 1: N-Queens II
**Name**: N-Queens II

**What Changed**: Return count instead of board configurations.

**Original Approach Works**: Same backtracking, increment count.

**What Breaks**: Count vs generate boards.

**New Constraint**: Return number of solutions.

**Algorithm Changes**: Increment counter instead of saving board.

**Complexity Changes**: Time O(N!), Space O(N) - same but less space for output.

### Variation 2: Sudoku Solver
**Name**: Sudoku Solver

**What Changed**: Fill 9x9 grid with digits 1-9.

**Original Approach Works**: Backtracking with row/col/box constraints.

**What Breaks**: Different constraints (3x3 boxes).

**New Constraint**: Valid Sudoku solution.

**Algorithm Changes**: Track row/col/box used digits, backtrack.

**Complexity Changes**: Time O(9^(81)) worst, Space O(1) for 9x9.