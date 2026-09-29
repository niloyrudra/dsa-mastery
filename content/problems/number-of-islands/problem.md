---
id: "number-of-islands"
title: "Number of Islands"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/number-of-islands/"
patterns: ["graph-dfs", "graph-bfs", "graph-union-find", "geometry-fundamentals"]
algorithms: ["dfs", "bfs", "union-find"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "array"]
relatedProblems: ["clone-graph", "number-of-connected-components-in-an-undirected-graph", "max-area-of-island", "surrounded-regions", "pacific-atlantic-water-flow"]
status: "PUBLISHED"
orderIndex: 30
reasonForSelection: "Classic grid DFS; connected components in 2D grid"
---

# Problem Statement

Given an `m x n` 2D binary grid `grid` which represents a map of `'1'`s (land) and `'0'`s (water), return the number of islands.

An **island** is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

## Constraints

- m == grid.length
- n == grid[i].length
- 1 ≤ m, n ≤ 300
- grid[i][j] is `'0'` or `'1'`.

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

**What Changed**: Return max island area instead of count.

**Original Approach Works**: Same DFS/BFS, track area.

**What Breaks**: Area vs count.

**New Constraint**: Return maximum area.

**Algorithm Changes**: Track area during DFS/BFS, update max.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Surrounded Regions
**Name**: Surrounded Regions

**What Changed**: Capture surrounded regions (flip 'O' to 'X').

**Original Approach Works**: Boundary-first DFS, mark border-connected.

**What Breaks**: Flip vs count.

**New Constraint**: Modify board in-place.

**Algorithm Changes**: DFS from borders, mark 'O' -> '#', then flip.

**Complexity Changes**: Time O(m*n), Space O(m*n).