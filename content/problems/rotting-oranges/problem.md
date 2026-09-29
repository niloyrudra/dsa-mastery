---
id: "rotting-oranges"
title: "Rotting Oranges"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/rotting-oranges/"
patterns: ["graph-bfs", "graph-dfs", "geometry-fundamentals"]
algorithms: ["bfs", "dfs"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "array"]
relatedProblems: ["number-of-islands", "pacific-atlantic-water-flow", "walls-and-gates", "surrounded-regions", "swim-in-rising-water"]
status: "PUBLISHED"
orderIndex: 31
reasonForSelection: "Multi-source BFS; minutes = BFS levels"
---

# Problem Statement

You are given an `m x n` grid where each cell can have one of three values:

- `0` representing an empty cell,
- `1` representing a fresh orange,
- `2` representing a rotten orange.

Every minute, any fresh orange that is **4-directionally adjacent** to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return `-1`.

## Constraints

- m == grid.length
- n == grid[i].length
- 1 ≤ m, n ≤ 10
- grid[i][j] is `0`, `1`, or `2`.

## Examples

**Example 1:**
```
Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
```

**Example 2:**
```
Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten.
```

**Example 3:**
```
Input: grid = [[0,2]]
Output: 0
Explanation: There are no fresh oranges initially.
```

## Variations

### Variation 1: Walls and Gates
**Name**: Walls and Gates

**What Changed**: Fill rooms with distance to nearest gate.

**Original Approach Works**: Multi-source BFS from gates.

**What Breaks**: Distance to gate vs minutes to rot.

**New Constraint**: Update empty rooms with distance.

**Algorithm Changes**: Multi-source BFS from gates, fill distances.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Number of Islands
**Name**: Number of Islands

**What Changed**: Count connected components of '1's.

**Original Approach Works**: DFS/BFS grid traversal.

**What Breaks**: Count vs multi-source BFS.

**New Constraint**: Return number of islands.

**Algorithm Changes**: DFS from each unvisited '1', mark visited.

**Complexity Changes**: Time O(m*n), Space O(m*n).