---
id: "swim-in-rising-water"
title: "Swim in Rising Water"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/swim-in-rising-water/"
patterns: ["shortest-path", "minimum-spanning-tree", "binary-search-on-answer", "graph-union-find"]
algorithms: ["dijkstra", "kruskal", "prim", "union-find"]
dataStructures: ["graph", "heap", "array"]
templates: ["graph-template-ts", "heap-template-ts"]
prerequisites: ["graph", "heap", "array"]
relatedProblems: ["network-delay-time", "min-cost-to-connect-all-points", "cheapest-flights-within-k-stops", "minimum-spanning-tree", "path-with-minimum-effort"]
status: "PUBLISHED"
orderIndex: 53
reasonForSelection: "Dijkstra on grid with max elevation; binary search + BFS alternative"
---

# Problem Statement

You are given an `n x n` integer matrix `grid` where each value `grid[i][j]` represents the elevation at that point `(i, j)`.

The rain starts to fall. At time `t`, the depth of the water everywhere is `t`. You can swim from a square to another 4-directionally adjacent square if and only if the elevation of both squares individually is **at most** `t`. You can swim infinite distances in zero time. Of course, you must stay within the boundaries of the grid during your swim.

Return the **least time** until you can reach the bottom right square `(n - 1, n - 1)` if you start at the top left square `(0, 0)`.

## Constraints

- n == grid.length
- n == grid[i].length
- 2 ≤ n ≤ 50
- 0 ≤ grid[i][j] < n²
- Each value `grid[i][j]` is **unique**.

## Examples

**Example 1:**
```
Input: grid = [[0,2],[1,3]]
Output: 3
Explanation: At time 0, you are at (0,0). You cannot swim to (0,1) or (1,0). At time 2, you can swim to (0,1). At time 3, you can swim to (1,1).
```

**Example 2:**
```
Input: grid = [[0,1,2,3,4],[24,23,22,21,20],[12,13,14,15,16],[11,17,18,19,5],[10,9,8,7,6]]
Output: 16
```

## Variations

### Variation 1: Network Delay Time
**Name**: Network Delay Time

**What Changed**: Weighted directed graph, find max time to reach all.

**Original Approach Works**: Dijkstra's algorithm.

**What Breaks**: Directed graph, max distance vs min max elevation.

**New Constraint**: Return max time to reach all.

**Algorithm Changes**: Dijkstra with min-heap, return max distance.

**Complexity Changes**: Time O(E log V), Space O(V).

### Variation 2: Min Cost to Connect All Points
**Name**: Min Cost to Connect All Points

**What Changed**: MST on complete graph with Manhattan distance.

**Original Approach Works**: Prim's or Kruskal's algorithm.

**What Breaks**: MST vs path finding.

**New Constraint**: Return MST total cost.

**Algorithm Changes**: Prim's on complete graph.

**Complexity Changes**: Time O(n² log n), Space O(n).