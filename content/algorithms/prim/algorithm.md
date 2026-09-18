---
id: "prim"
name: "Prim's Algorithm"
description: "Minimum Spanning Tree algorithm: grow tree from arbitrary start, always add minimum edge connecting tree to outside."
step_by_step_reasoning: "1. Start with arbitrary vertex in MST. 2. Priority queue of edges crossing cut (tree to non-tree). 3. Extract minimum edge, add its vertex to MST. 4. Add new crossing edges to queue. 5. Repeat until all vertices in MST."
complexity_analysis: "With binary heap: O(E log V). Each edge added to heap once, each vertex extracted once. Fibonacci heap: O(E + V log V). Better for dense graphs."
time_complexity: "O(E log V)"
space_complexity: "O(V + E)"
time_reasoning: "V extractions, E edge additions to heap. Each heap operation O(log V)."
space_reasoning: "Priority queue O(E), visited array O(V), MST edges O(V)."
status: "PUBLISHED"
---
# Prim's Algorithm

## Overview
Prim's algorithm builds MST by growing a tree from an arbitrary starting vertex, always adding the minimum weight edge that connects the tree to a new vertex.

## Algorithm Steps
1. Start with arbitrary vertex in MST, mark visited
2. Add all edges from this vertex to priority queue (min-heap by weight)
3. While MST doesn't include all vertices:
   - Extract minimum weight edge (u, v, w) from queue
   - If v already visited, continue
   - Add edge to MST, mark v visited
   - Add all edges from v to unvisited neighbors to queue
4. Return MST

## Key Insight
At each step, the algorithm considers the cut between visited and unvisited vertices. The minimum edge crossing this cut is guaranteed to be in the MST (cut property).

## Complexity
- Time: O(E log V) with binary heap
- Space: O(V + E)

## When to Use
- Dense graphs (E ≈ V²)
- When graph is given as adjacency matrix/list
- Need to find MST from specific start vertex
- When you need to construct the actual MST edges

## Comparison with Kruskal
- Kruskal: better for sparse graphs, needs edge list
- Prim: better for dense graphs, works with adjacency representation