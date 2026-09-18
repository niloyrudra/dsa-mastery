---
id: "dijkstra"
name: "Dijkstra's Algorithm"
description: "Shortest path from single source in weighted graph with non-negative edges. Uses priority queue."
step_by_step_reasoning: "1. Initialize distances: source=0, others=∞. 2. Priority queue with (distance, vertex). 3. Extract min distance vertex. 4. Relax all outgoing edges. 5. Repeat until queue empty or target reached."
complexity_analysis: "With binary heap: O((V + E) log V). Each vertex extracted once, each edge relaxed once. Fibonacci heap: O(E + V log V)."
time_complexity: "O((V + E) log V)"
space_complexity: "O(V)"
time_reasoning: "V extractions from heap (log V each), E relaxations (log V each for heap update)."
space_reasoning: "Distance array O(V), priority queue O(V), adjacency list O(V + E)."
status: "PUBLISHED"
---
# Dijkstra's Algorithm

## Overview
Dijkstra finds shortest paths from a single source in graphs with non-negative edge weights.

## Algorithm Steps
1. Initialize: distance[source] = 0, all others = ∞
2. Priority queue with (distance, vertex), initially (0, source)
3. While queue not empty:
   - Extract vertex u with minimum distance
   - If u already processed, continue
   - For each neighbor v of u:
     - If distance[u] + weight(u,v) < distance[v]:
       - distance[v] = distance[u] + weight(u,v)
       - Push (distance[v], v) to queue

## Key Insight
Once a vertex is extracted from the priority queue, its distance is finalized (greedy property).

## Complexity
- Time: O((V + E) log V) with binary heap
- Space: O(V)

## When to Use
- Single-source shortest path with non-negative weights
- Network routing
- Minimum cost problems
- When you need actual path (track predecessors)

## Limitations
- Fails with negative edge weights
- Not for all-pairs (use Floyd-Warshall or Johnson's)