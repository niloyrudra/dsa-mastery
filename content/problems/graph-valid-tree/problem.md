---
id: "graph-valid-tree"
title: "Graph Valid Tree"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/graph-valid-tree/"
patterns: ["graph-union-find", "graph-dfs", "graph-bfs"]
algorithms: ["union-find", "dfs", "bfs"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "array"]
relatedProblems: ["number-of-islands", "number-of-connected-components-in-an-undirected-graph", "redundant-connection", "accounts-merge"]
status: "PUBLISHED"
orderIndex: 32
reasonForSelection: "Union-Find for cycle detection and connectivity; n-1 edges required"
---

# Problem Statement

You have a graph of `n` nodes labeled from `0` to `n - 1`. You are given an integer `n` and a list of `edges` where `edges[i] = [a, b]` indicates that there is an undirected edge between nodes `a` and `b` in the graph.

Return `true` if the edges of the given graph make up a valid tree, and `false` otherwise.

## Constraints

- 1 ≤ n ≤ 2000
- 0 ≤ edges.length ≤ 5000
- edges[i].length == 2
- 0 ≤ a, b < n
- a != b
- There are no self-loops or repeated edges.

## Examples

**Example 1:**
```
Input: n = 5, edges = [[0,1],[0,2],[0,3],[1,4]]
Output: true
```

**Example 2:**
```
Input: n = 5, edges = [[0,1],[1,2],[2,3],[1,3],[1,4]]
Output: false
```

## Variations

### Variation 1: Number of Connected Components in an Undirected Graph
**Name**: Number of Connected Components in an Undirected Graph

**What Changed**: Count connected components instead of checking tree.

**Original Approach Works**: Same Union-Find, count roots.

**What Breaks**: Different return value.

**New Constraint**: Return number of connected components.

**Algorithm Changes**: Count unique roots after unions.

**Complexity Changes**: Time O(n α(n)), Space O(n).

### Variation 2: Redundant Connection
**Name**: Redundant Connection

**What Changed**: Find edge that creates cycle.

**Original Approach Works**: Union-Find, return edge where union fails.

**What Breaks**: Return specific edge.

**New Constraint**: Return redundant edge.

**Algorithm Changes**: Return [a,b] when union returns false.

**Complexity Changes**: Time O(n α(n)), Space O(n).