---
id: "surrounded-regions"
title: "Surrounded Regions"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/surrounded-regions/"
patterns: ["graph-dfs", "graph-bfs", "geometry-fundamentals"]
algorithms: ["dfs", "bfs"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "array"]
relatedProblems: ["number-of-islands", "pacific-atlantic-water-flow", "rotting-oranges", "walls-and-gates", "number-of-closed-islands"]
status: "PUBLISHED"
orderIndex: 34
reasonForSelection: "Boundary DFS/BFS to mark non-captured; flip remaining"
---

# Problem Statement

Given an `m x n` matrix `board` containing `'X'` and `'O'`, capture all regions that are **4-directionally surrounded** by `'X'`.

A region is **captured** by flipping all `'O'`s into `'X'`s in that surrounded region.

## Constraints

- m == board.length
- n == board[i].length
- 1 ≤ m, n ≤ 200
- board[i][j] is `'X'` or `'O'`.

## Examples

**Example 1:**
```
Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
Explanation: Surrounded regions should not be on the border.
```

**Example 2:**
```
Input: board = [["X"]]
Output: [["X"]]
```

## Variations

### Variation 1: Number of Islands
**Name**: Number of Islands

**What Changed**: Count connected components of '1's.

**Original Approach Works**: Grid DFS/BFS.

**What Breaks**: Count vs boundary marking.

**New Constraint**: Return number of islands.

**Algorithm Changes**: DFS from each unvisited '1', mark visited.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Pacific Atlantic Water Flow
**Name**: Pacific Atlantic Water Flow

**What Changed**: Find cells flowing to both oceans.

**Original Approach Works**: Reverse flow from oceans.

**What Breaks**: Dual ocean vs boundary marking.

**New Constraint**: Return cells reaching both oceans.

**Algorithm Changes**: Multi-source DFS/BFS from borders.

**Complexity Changes**: Time O(m*n), Space O(m*n).