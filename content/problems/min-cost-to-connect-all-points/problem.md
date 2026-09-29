---
id: "min-cost-to-connect-all-points"
title: "Min Cost to Connect All Points"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/min-cost-to-connect-all-points/"
patterns: ["minimum-spanning-tree", "graph-union-find", "heap-fundamentals", "shortest-path"]
algorithms: ["kruskal", "prim", "dijkstra"]
dataStructures: ["graph", "heap", "array"]
templates: ["graph-template-ts", "heap-template-ts"]
prerequisites: ["graph", "heap", "array"]
relatedProblems: ["network-delay-time", "cheapest-flights-within-k-stops", "swim-in-rising-water", "minimum-spanning-tree"]
status: "PUBLISHED"
orderIndex: 52
reasonForSelection: "Prim's on complete graph; Manhattan distance as edge weight"
---

# Problem Statement

You are given an array `points` where `points[i] = [x, y]` represents the coordinates of a point on the X-Y plane. The cost of connecting two points `[x1, y1]` and `[x2, y2]` is the Manhattan distance: `|x1 - x2| + |y1 - y2|`.

Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path between any two points.

## Constraints

- 1 ≤ points.length ≤ 1000
- -10⁶ ≤ x, y ≤ 10⁶
- All pairs `(x, y)` are distinct.

## Examples

**Example 1:**
```
Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
Output: 20
Explanation: Connect points as shown in the image.
```

**Example 2:**
```
Input: points = [[3,12],[-2,5],[-4,1]]
Output: 18
```

## Variations

### Variation 1: Network Delay Time
**Name**: Network Delay Time

**What Changed**: Weighted directed graph, find time for signal to reach all nodes.

**Original Approach Works**: Dijkstra's algorithm.

**What Breaks**: Directed graph, single source.

**New Constraint**: Return max time to reach all nodes.

**Algorithm Changes**: Dijkstra with min-heap.

**Complexity Changes**: Time O(E log V), Space O(V).

### Variation 2: Minimum Spanning Tree
**Name**: Minimum Spanning Tree (same problem, Kruskal's)

**What Changed**: Use Union-Find instead of Prim's.

**Original Approach Works**: Kruskal's with Union-Find.

**What Breaks**: Different algorithm.

**New Constraint**: N/A.

**Algorithm Changes**: Sort edges, Union-Find.

**Complexity Changes**: Time O(E log E), Space O(V).