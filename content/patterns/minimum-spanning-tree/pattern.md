---
id: "minimum-spanning-tree"
name: "Minimum Spanning Tree"
category: "Graph"
definition: "Finding minimum weight subset of edges connecting all vertices: Kruskal's algorithm (sort edges + Union-Find) and Prim's algorithm (priority queue + greedy)."
when_to_use: "Connecting all nodes with minimum cost; network design; clustering; approximate TSP; maze generation."
why_it_works: "Kruskal: sort edges by weight, add if they don't form cycle (Union-Find). Prim: grow tree from arbitrary start, always add minimum edge connecting tree to outside."
intuition: "Both are greedy - make locally optimal choice (smallest edge) that maintains connectivity. Cut property: minimum edge across any cut is in MST."
invariant: "Kruskal: forest gradually becomes single tree. Prim: growing tree always connected, vertices in tree have minimum connection to outside."
strengths: "Kruskal: O(E log E) simple with Union-Find; good for sparse graphs. Prim: O(E log V) with heap; better for dense graphs; easier to reconstruct tree."
weaknesses: "Only for undirected connected graphs; MST not unique if equal weights; Prim needs adjacency list/matrix; Kruskal needs edge list."
recognition_signals:
  - "minimum spanning tree"
  - "connect all points"
  - "minimum cost to connect"
  - "kruskal"
  - "prim"
  - "network design"
  - "clustering"
common_failure_modes:
  - "Using on directed graph"
  - "Not handling disconnected graph (no MST exists)"
  - "Union-Find path compression/union by rank missing"
  - "Prim: not updating keys correctly"
  - "Forgetting to check if edge connects two components in Kruskal"
generalizations: "Can find maximum spanning tree (negate weights); second-best MST; MST for clustering (remove k-1 largest edges); online MST."
time_complexity: "Kruskal: O(E log E); Prim: O(E log V) with binary heap, O(E + V log V) with Fibonacci heap"
space_complexity: "O(V) for Union-Find/parent arrays, O(E) for edge storage"
time_reasoning: "Kruskal: sorting edges dominates. Prim: heap operations for each edge."
space_reasoning: "Union-Find arrays + edge list or adjacency list + heap."
status: "PUBLISHED"
---
# Minimum Spanning Tree Pattern

## Definition
Finding minimum weight subset of edges connecting all vertices: Kruskal's algorithm (sort edges + Union-Find) and Prim's algorithm (priority queue + greedy).

## When to Use
- Connecting all nodes with minimum cost
- Network design
- Clustering
- Approximate TSP
- Maze generation

## Why It Works
Kruskal: sort edges by weight, add if they don't form cycle (Union-Find). Prim: grow tree from arbitrary start, always add minimum edge connecting tree to outside.

## Intuition
Both are greedy - make locally optimal choice (smallest edge) that maintains connectivity. Cut property: minimum edge across any cut is in MST.

## Invariant
Kruskal: forest gradually becomes single tree. Prim: growing tree always connected, vertices in tree have minimum connection to outside.

## Strengths
- Kruskal: O(E log E) simple with Union-Find; good for sparse graphs
- Prim: O(E log V) with heap; better for dense graphs; easier to reconstruct tree

## Weaknesses
- Only for undirected connected graphs
- MST not unique if equal weights
- Prim needs adjacency list/matrix
- Kruskal needs edge list

## Recognition Signals
- "minimum spanning tree"
- "connect all points"
- "minimum cost to connect"
- "kruskal"
- "prim"
- "network design"
- "clustering"

## Common Failure Modes
- Using on directed graph
- Not handling disconnected graph (no MST exists)
- Union-Find path compression/union by rank missing
- Prim: not updating keys correctly
- Forgetting to check if edge connects two components in Kruskal

## Generalizations
Can find maximum spanning tree (negate weights); second-best MST; MST for clustering (remove k-1 largest edges); online MST.

## Complexity
- Time: Kruskal: O(E log E); Prim: O(E log V) with binary heap, O(E + V log V) with Fibonacci heap
- Space: O(V) for Union-Find/parent arrays, O(E) for edge storage