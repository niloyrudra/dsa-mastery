---
id: "cheapest-flights-within-k-stops"
title: "Cheapest Flights Within K Stops"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/cheapest-flights-within-k-stops/"
patterns: ["shortest-path", "binary-search-on-answer", "greedy-fundamentals"]
algorithms: ["dijkstra", "kruskal", "prim"]
dataStructures: ["graph", "heap"]
templates: ["graph-template-ts", "heap-template-ts"]
prerequisites: ["graph", "heap"]
relatedProblems: ["network-delay-time", "min-cost-to-connect-all-points", "find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance"]
status: "PUBLISHED"
orderIndex: 52
reasonForSelection: "Modified Dijkstra with stop constraint; Bellman-Ford alternative"
---

# Problem Statement

There are `n` cities connected by some number of flights. You are given an array `flights` where `flights[i] = [from, to, price]` indicates that there is a flight from city `from` to city `to` with cost `price`.

You are also given three integers `src`, `dst`, and `k`, return the cheapest price from `src` to `dst` with at most `k` stops. If there is no such route, return `-1`.

## Constraints

- 1 ≤ n ≤ 100
- 0 ≤ flights.length ≤ n * (n - 1) / 2
- flights[i].length == 3
- 0 ≤ from, to < n
- from != to
- 1 ≤ price ≤ 10⁴
- There will not be any multiple flights between two cities.
- 0 ≤ src, dst, k < n
- src != dst

## Examples

**Example 1:**
```
Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
Output: 700
Explanation: The optimal path with at most 1 stop is 0 -> 1 -> 3, cost = 100 + 600 = 700.
```

**Example 2:**
```
Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
Output: 200
Explanation: The optimal path with at most 1 stop is 0 -> 1 -> 2, cost = 100 + 100 = 200.
```

**Example 3:**
```
Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
Output: 500
Explanation: The optimal path with 0 stops is 0 -> 2, cost = 500.
```

## Variations

### Variation 1: Network Delay Time
**Name**: Network Delay Time

**What Changed**: Find time for signal to reach all nodes from source.

**Original Approach Works**: Dijkstra's algorithm on weighted graph.

**What Breaks**: No stop constraint, find max distance to all nodes.

**New Constraint**: Return max shortest path distance.

**Algorithm Changes**: Standard Dijkstra without stop limit.

**Complexity Changes**: Time O(E log V), Space O(V).

### Variation 2: Min Cost to Connect All Points
**Name**: Min Cost to Connect All Points

**What Changed**: Connect all points with minimum cost (MST).

**Original Approach Works**: Prim's or Kruskal's algorithm.

**What Breaks**: Different problem - MST not shortest path.

**New Constraint**: Return total MST cost.

**Algorithm Changes**: Prim's with min-heap or Kruskal with Union-Find.

**Complexity Changes**: Time O(E log V) or O(E log E), Space O(V).