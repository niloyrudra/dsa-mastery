---
id: "bfs"
name: "Breadth First Search"
description: "Graph traversal exploring all neighbors at current depth before moving deeper. Uses queue."
step_by_step_reasoning: "1. Enqueue source vertex, mark visited. 2. While queue not empty: dequeue vertex, process it. 3. For each unvisited neighbor: mark visited, enqueue. 4. Processes level by level."
complexity_analysis: "Visits each vertex and edge once. Time: O(V + E). Space: O(V) for visited set and queue (max one level)."
time_complexity: "O(V + E)"
space_complexity: "O(V)"
time_reasoning: "Each vertex enqueued/dequeued once, each edge examined once."
space_reasoning: "Visited array O(V), queue stores at most one level of vertices O(V)."
status: "PUBLISHED"
---
# Breadth First Search (BFS)

## Overview
BFS explores a graph level by level, finding shortest paths in unweighted graphs.

## Algorithm Steps
1. Enqueue source, mark visited
2. While queue not empty:
   - Dequeue vertex
   - Process vertex
   - Enqueue all unvisited neighbors, mark visited

## Key Insight
BFS guarantees shortest path (in edges) from source to all reachable vertices in unweighted graphs.

## Applications
- Shortest path in unweighted graphs
- Level order traversal
- Minimum steps problems
- Bipartite checking
- Finding connected components
- Multi-source BFS (rotten oranges, walls and gates)

## Complexity
- Time: O(V + E)
- Space: O(V)

## When to Use
- Shortest path in unweighted graph
- Level-by-level processing needed
- Multi-source problems
- When graph is wide rather than deep