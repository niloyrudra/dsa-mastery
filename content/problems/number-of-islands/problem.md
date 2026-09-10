---
id: "number-of-islands"
title: "Number of Islands"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/number-of-islands/"
patterns: ["graph-dfs", "graph-bfs", "graph-union-find"]
algorithms: ["grid-dfs", "grid-bfs", "union-find"]
dataStructures: ["graph", "grid", "union-find"]
templates: ["grid-dfs", "grid-bfs", "union-find"]
prerequisites: ["graph", "dfs", "bfs"]
relatedProblems: ["max-area-of-island", "surrounded-regions", "number-of-islands-ii"]
status: "PUBLISHED"
orderIndex: 56
reasonForSelection: "Classic grid DFS/BFS; introduces grid as implicit graph; union-find alternative"
---

# Problem Statement

Given an `m x n` 2D binary grid `grid` which represents a map of `'1'`s (land) and `'0'`s (water), return the number of islands.

An **island** is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

## Constraints

- m == grid.length
- n == grid[i].length
- 1 ≤ m, n ≤ 300
- grid[i][j] is '0' or '1'.

## Examples

**Example 1:**
```
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
```

**Example 2:**
```
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
```

## Variations

### Variation 1: Max Area of Island
**Name**: Max Area of Island

**What Changed**: Find largest island area instead of count.

**Original Approach Works**: DFS/BFS still works, return area instead of count.

**What Breaks**: Need to track area during traversal.

**New Constraint**: Return max area, not count.

**Algorithm Changes**: Minor - accumulate area in DFS.

**Complexity Changes**: Time O(m*n) → O(m*n), Space O(m*n) → O(m*n).