---
id: "number-of-connected-components-in-an-undirected-graph"
title: "Number of Connected Components in an Undirected Graph"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/"
patterns: ["graph-union-find", "graph-dfs", "graph-bfs"]
algorithms: ["union-find", "dfs", "bfs"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "array"]
relatedProblems: ["graph-valid-tree", "number-of-islands", "redundant-connection", "accounts-merge"]
status: "PUBLISHED"
orderIndex: 33
reasonForSelection: "Union-Find or DFS/BFS for component counting"
---

# Problem Statement

You have a graph of `n` nodes labeled from `0` to `n - 1`. You are given an integer `n` and an array `edges` where `edges[i] = [a, b]` indicates that there is an undirected edge between nodes `a` and `b` in the graph.

Return the number of connected components in the graph.

## Constraints

- 1 ≤ n ≤ 2000
- 1 ≤ edges.length ≤ 5000
- edges[i].length == 2
- 0 ≤ a, b < n
- a != b
- There are no self-loops or repeated edges.

## Examples

**Example 1:**
```
Input: n = 5, edges = [[0,1],[1,2],[3,4]]
Output: 2
```

**Example 2:**
```
Input: n = 5, edges = [[0,1],[1,2],[2,3],[3,4]]
Output: 1
```

## Variations

### Variation 1: Graph Valid Tree
**Name**: Graph Valid Tree

**What Changed**: Check if connected and no cycles (1 component, n-1 edges).

**Original Approach Works**: Union-Find, check cycle and connectivity.

**What Breaks**: Validation vs counting.

**New Constraint**: Return true if valid tree.

**Algorithm Changes**: Union-Find, check cycle and count components.

**Complexity Changes**: Time O(n α(n)), Space O(n).

### Variation 2: Number of Islands
**Name**: Number of Islands

**What Changed**: Grid instead of explicit graph.

**Original Approach Works**: Union-Find or DFS/BFS on grid.

**What Breaks**: Implicit graph from grid.

**New Constraint**: Return count of islands.

**Algorithm Changes**: Grid DFS/BFS or Union-Find on 2D to 1D mapping.

**Complexity Changes**: Time O(m*n), Space O(m*n).