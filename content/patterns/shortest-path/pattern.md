---
id: "shortest-path"
name: "Shortest Path"
category: "Graph"
definition: "Finding shortest path in weighted graphs: Dijkstra's algorithm for non-negative weights, Bellman-Ford for negative weights, SPFA optimization."
when_to_use: "Single-source shortest path; network routing; minimum cost; finding shortest path with constraints; weighted graph traversal."
why_it_works: "Dijkstra: greedily expands closest unvisited vertex; once settled, distance is optimal. Bellman-Ford: relaxes all edges V-1 times to handle negative weights."
intuition: "Maintain distance estimates. Repeatedly pick closest unprocessed vertex, relax its edges. Priority queue gives O((V+E)log V) for Dijkstra."
invariant: "Dijkstra: settled vertices have final shortest distances. Bellman-Ford: after k iterations, distances are optimal for paths with <= k edges."
strengths: "Dijkstra: O((V+E)log V) with heap; optimal for non-negative weights. Bellman-Ford: detects negative cycles; works with negative weights."
weaknesses: "Dijkstra: fails with negative weights. Bellman-Ford: O(VE) slower. Both: no path reconstruction without parent tracking."
recognition_signals:
  - "shortest path"
  - "dijkstra"
  - "network delay time"
  - "cheapest flights within k stops"
  - "minimum cost"
  - "path with minimum effort"
  - "bellman-ford"
  - "negative weights"
common_failure_modes:
  - "Using Dijkstra with negative weights"
  - "Not handling unreachable nodes"
  - "Incorrect priority queue ordering"
  - "Forgetting to check if distance is already optimal before processing"
  - "Not tracking path for reconstruction"
generalizations: "A* for heuristic-guided search; bidirectional Dijkstra; Johnson's algorithm for all-pairs; multi-source multi-target."
time_complexity: "Dijkstra: O((V+E)log V); Bellman-Ford: O(VE); SPFA: O(kE) average"
space_complexity: "O(V) for distances, O(V) for heap/queue"
time_reasoning: "Dijkstra: each vertex extracted once, each edge relaxed once. Bellman-Ford: V-1 passes over all edges."
space_reasoning: "Distance array, priority queue, adjacency list."
status: "PUBLISHED"
---
# Shortest Path Pattern

## Definition
Finding shortest path in weighted graphs: Dijkstra's algorithm for non-negative weights, Bellman-Ford for negative weights, SPFA optimization.

## When to Use
- Single-source shortest path
- Network routing
- Minimum cost
- Finding shortest path with constraints
- Weighted graph traversal

## Why It Works
Dijkstra: greedily expands closest unvisited vertex; once settled, distance is optimal. Bellman-Ford: relaxes all edges V-1 times to handle negative weights.

## Intuition
Maintain distance estimates. Repeatedly pick closest unprocessed vertex, relax its edges. Priority queue gives O((V+E)log V) for Dijkstra.

## Invariant
Dijkstra: settled vertices have final shortest distances. Bellman-Ford: after k iterations, distances are optimal for paths with <= k edges.

## Strengths
- Dijkstra: O((V+E)log V) with heap; optimal for non-negative weights
- Bellman-Ford: detects negative cycles; works with negative weights

## Weaknesses
- Dijkstra: fails with negative weights
- Bellman-Ford: O(VE) slower
- Both: no path reconstruction without parent tracking

## Recognition Signals
- "shortest path"
- "dijkstra"
- "network delay time"
- "cheapest flights within k stops"
- "minimum cost"
- "path with minimum effort"
- "bellman-ford"
- "negative weights"

## Common Failure Modes
- Using Dijkstra with negative weights
- Not handling unreachable nodes
- Incorrect priority queue ordering
- Forgetting to check if distance is already optimal before processing
- Not tracking path for reconstruction

## Generalizations
A* for heuristic-guided search; bidirectional Dijkstra; Johnson's algorithm for all-pairs; multi-source multi-target.

## Complexity
- Time: Dijkstra: O((V+E)log V); Bellman-Ford: O(VE); SPFA: O(kE) average
- Space: O(V) for distances, O(V) for heap/queue