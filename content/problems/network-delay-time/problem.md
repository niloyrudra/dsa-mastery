---
id: "network-delay-time"
title: "Network Delay Time"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/network-delay-time/"
patterns: ["shortest-path", "heap-fundamentals", "minimum-spanning-tree"]
algorithms: ["dijkstra", "kruskal", "prim"]
dataStructures: ["graph", "heap"]
templates: ["graph-template-ts", "heap-template-ts"]
prerequisites: ["graph", "heap"]
relatedProblems: ["cheapest-flights-within-k-stops", "min-cost-to-connect-all-points", "minimum-spanning-tree", "find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance"]
status: "PUBLISHED"
orderIndex: 53
reasonForSelection: "Dijkstra's algorithm; maximum distance to any node"
---

# Problem Statement

You are given a network of `n` nodes labeled from `1` to `n`. You are also given `times`, a list of travel times as directed edges `times[i] = [u, v, w]`, where `u` is the source node, `v` is the target node, and `w` is the time it takes for a signal to travel from source to target.

We will send a signal from a given node `k`. Return the **minimum** time it takes for all the `n` nodes to receive the signal. If it is impossible for all nodes to receive the signal, return `-1`.

## Constraints

- 1 ≤ n ≤ 100
- 1 ≤ times.length ≤ 6000
- times[i].length == 3
- 1 ≤ u, v ≤ n
- u != v
- 1 ≤ w ≤ 100
- All the pairs `(u, v)` are **unique**.
- 1 ≤ k ≤ n

## Examples

**Example 1:**
```
Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
Output: 2
Explanation: The signal travels 2 -> 1 (time 1) and 2 -> 3 (time 1), then 3 -> 4 (time 1). Total time = 2.
```

**Example 2:**
```
Input: times = [[1,2,1]], n = 2, k = 1
Output: 1
```

**Example 3:**
```
Input: times = [[1,2,1]], n = 2, k = 2
Output: -1
```

## Variations

### Variation 1: Cheapest Flights Within K Stops
**Name**: Cheapest Flights Within K Stops

**What Changed**: Limit on number of stops.

**Original Approach Works**: Modified Dijkstra or Bellman-Ford with stops constraint.

**What Breaks**: Stop limit.

**New Constraint**: At most k stops.

**Algorithm Changes**: Bellman-Ford or Dijkstra with stops tracking.

**Complexity Changes**: Time O(E * k) or O(E log V), Space O(V).

### Variation 2: Min Cost to Connect All Points
**Name**: Min Cost to Connect All Points

**What Changed**: MST on complete graph with Manhattan distance.

**Original Approach Works**: Prim's algorithm.

**What Breaks**: Undirected, complete graph.

**New Constraint**: Return MST total cost.

**Algorithm Changes**: Prim's with min-heap on complete graph.

**Complexity Changes**: Time O(n² log n) or O(n²), Space O(n).