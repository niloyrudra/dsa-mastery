---
id: "pacific-atlantic-water-flow"
title: "Pacific Atlantic Water Flow"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/pacific-atlantic-water-flow/"
patterns: ["graph-dfs", "graph-bfs", "geometry-fundamentals"]
algorithms: ["dfs", "bfs"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "array"]
relatedProblems: ["number-of-islands", "surrounded-regions", "rotting-oranges", "walls-and-gates", "swim-in-rising-water"]
status: "PUBLISHED"
orderIndex: 34
reasonForSelection: "Reverse flow from oceans; multi-source BFS/DFS"
---

# Problem Statement

There is an `m x n` rectangular island that borders both the **Pacific Ocean** and **Atlantic Ocean**. The **Pacific Ocean** touches the island's left and top edges, and the **Atlantic Ocean** touches the island's right and bottom edges.

The island is partitioned into a grid of square cells. You are given an `m x n` integer matrix `heights` where `heights[r][c]` represents the **height above sea level** of the cell at coordinate `(r, c)`.

The island receives a lot of rain, and the water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is **less than or equal to** the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean.

Return a **2D list** of grid coordinates `result` where `result[i] = [ri, ci]` denotes that rain water can flow from cell `(ri, ci)` to **both** the Pacific and Atlantic oceans.

## Constraints

- m == heights.length
- n == heights[r].length
- 1 ≤ m, n ≤ 200
- 1 ≤ heights[r][c] ≤ 10⁵

## Examples

**Example 1:**
```
Input: heights = [[1,2,2,3,5],[3,2,3,4,4],[2,4,5,3,1],[6,7,1,4,5],[5,1,1,2,4]]
Output: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
```

**Example 2:**
```
Input: heights = [[2,1],[1,2]]
Output: [[0,0],[0,1],[1,0],[1,1]]
```

## Variations

### Variation 1: Surrounded Regions
**Name**: Surrounded Regions

**What Changed**: Capture surrounded 'O' regions.

**Original Approach Works**: Boundary-first DFS/BFS.

**What Breaks**: Flip 'O' to 'X' vs find both-ocean cells.

**New Constraint**: Modify board in-place.

**Algorithm Changes**: DFS from borders, mark 'O' -> '#', then flip remaining.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Number of Islands
**Name**: Number of Islands

**What Changed**: Count connected components of '1's.

**Original Approach Works**: Grid DFS/BFS.

**What Breaks**: Count islands vs find dual-ocean cells.

**New Constraint**: Return number of islands.

**Algorithm Changes**: DFS from each unvisited '1', mark visited.

**Complexity Changes**: Time O(m*n), Space O(m*n).