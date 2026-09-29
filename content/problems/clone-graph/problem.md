---
id: "clone-graph"
title: "Clone Graph"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/clone-graph/"
patterns: ["graph-dfs", "graph-bfs"]
algorithms: ["dfs", "bfs"]
dataStructures: ["graph", "hash-table"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "hash-table", "dfs"]
relatedProblems: ["number-of-islands", "course-schedule", "surrounded-regions"]
status: "PUBLISHED"
orderIndex: 31
reasonForSelection: "Graph cloning with hash map for visited tracking"
---

# Problem Statement

Given a reference of a node in a **connected** undirected graph, return a **deep copy** (clone) of the graph.

Each node in the graph contains a value (`int`) and a list (`List[Node]`) of its neighbors.

## Constraints

- The number of nodes in the graph is in the range [0, 100].
- 1 ≤ Node.val ≤ 100
- Node.val is unique for each node.
- There are no repeated edges and no self-loops.
- The Graph is connected and all nodes can be visited from the given node.

## Examples

**Example 1:**
```
Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
Output: [[2,4],[1,3],[2,4],[1,3]]
Explanation: There are 4 nodes in the graph.
1st node (val = 1) neighbors: 2nd (val = 2) and 4th (val = 4).
2nd node (val = 2) neighbors: 1st (val = 1) and 3rd (val = 3).
3rd node (val = 3) neighbors: 2nd (val = 2) and 4th (val = 4).
4th node (val = 4) neighbors: 1st (val = 1) and 3rd (val = 3).
```

**Example 2:**
```
Input: adjList = [[]]
Output: [[]]
Explanation: The graph has one node with no neighbors.
```

**Example 3:**
```
Input: adjList = []
Output: []
Explanation: Empty graph.
```

## Variations

### Variation 1: Copy List with Random Pointer
**Name**: Copy List with Random Pointer

**What Changed**: Linked list with random pointers instead of graph.

**Original Approach Works**: Hash map original -> copy, two passes.

**What Breaks**: Linear structure vs graph, but same cloning pattern.

**New Constraint**: Each node has next and random pointer.

**Algorithm Changes**: Hash map for nodes, set random in second pass.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Number of Islands
**Name**: Number of Islands

**What Changed**: Count connected components in grid.

**Original Approach Works**: DFS/BFS on grid graph.

**What Breaks**: Grid represented as implicit graph.

**New Constraint**: Return count of connected components.

**Algorithm Changes**: Grid traversal with visited marking.

**Complexity Changes**: Time O(m*n), Space O(m*n).