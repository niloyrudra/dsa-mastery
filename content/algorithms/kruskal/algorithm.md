---
id: "kruskal"
name: "Kruskal's Algorithm"
description: "Minimum Spanning Tree algorithm: sort all edges by weight, add edges that don't form cycles using Union-Find."
step_by_step_reasoning: "1. Sort all edges by weight ascending. 2. Initialize Union-Find for all vertices. 3. For each edge in sorted order: if endpoints in different components, add edge to MST, union components. 4. Stop when MST has V-1 edges."
complexity_analysis: "Sorting edges: O(E log E). Union-Find operations: O(E α(V)) ≈ O(E). Total: O(E log E). Good for sparse graphs."
time_complexity: "O(E log E)"
space_complexity: "O(V + E)"
time_reasoning: "Edge sorting dominates. Union-Find with path compression is nearly constant."
space_reasoning: "Edge list O(E), Union-Find arrays O(V), MST edges O(V)."
status: "PUBLISHED"
---
# Kruskal's Algorithm

## Overview
Kruskal's algorithm finds Minimum Spanning Tree by greedily adding the smallest edges that don't create cycles.

## Algorithm Steps
1. Sort all edges by weight (ascending)
2. Initialize Union-Find (Disjoint Set) for all vertices
3. For each edge (u, v, weight) in sorted order:
   - If find(u) != find(v):  // different components
     - Add edge to MST
     - union(u, v)
4. Stop when MST has V-1 edges

## Key Insight
Cut property: For any cut of the graph, the minimum weight edge crossing the cut is in some MST. Kruskal considers edges in weight order, so it always picks valid minimum edges.

## Complexity
- Time: O(E log E) - dominated by sorting
- Space: O(V + E)

## When to Use
- Sparse graphs (E ≈ V)
- When edges are already available as list
- Need to find MST weight or edges
- Can be parallelized

## Union-Find Optimizations
- Path compression: flatten tree during find
- Union by rank/size: attach smaller tree to larger