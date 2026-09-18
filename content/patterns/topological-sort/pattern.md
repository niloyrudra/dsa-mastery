---
id: "topological-sort"
name: "Topological Sort"
category: "Graph"
definition: "Linear ordering of vertices in a DAG such that for every directed edge u->v, u comes before v. Kahn's algorithm (BFS with indegree) or DFS with post-order."
when_to_use: "Course scheduling; task ordering with dependencies; build systems; detecting cycles in directed graphs; alien dictionary; reconstructing sequences."
why_it_works: "DAG has at least one vertex with indegree 0. Kahn's: repeatedly remove indegree-0 vertices. DFS: post-order of DFS gives reverse topological order."
intuition: "Vertices with no prerequisites go first. Process them, remove their outgoing edges, new vertices may become available. If cycle exists, no valid ordering."
invariant: "Kahn's: processed vertices form valid prefix of topological order; remaining graph maintains DAG property. DFS: post-order visit ensures dependencies come before dependents."
strengths: "O(V+E) linear time; detects cycles naturally; multiple valid orderings possible; simple implementation."
weaknesses: "Only works on DAGs (no cycles); ordering not unique; can't handle undirected graphs; need all vertices reachable from sources."
recognition_signals:
  - "topological sort"
  - "course schedule"
  - "course schedule ii"
  - "alien dictionary"
  - "reconstruct itinerary"
  - "task ordering"
  - "build order"
  - "sequence reconstruction"
common_failure_modes:
  - "Not detecting cycles (Kahn's: processed count < V)"
  - "DFS: not handling multiple connected components"
  - "Incorrect indegree calculation"
  - "Using on graph with cycles without detection"
  - "Not handling isolated vertices"
generalizations: "Can find all topological orderings; can find lexicographically smallest (priority queue); parallel topological sort; dynamic topological sort."
time_complexity: "O(V + E)"
space_complexity: "O(V) for indegree/visited/order, O(V) for queue/stack"
time_reasoning: "Each vertex and edge processed exactly once."
space_reasoning: "Indegree array, visited array, result array, queue/stack for processing."
status: "PUBLISHED"
---
# Topological Sort Pattern

## Definition
Linear ordering of vertices in a DAG such that for every directed edge u->v, u comes before v. Kahn's algorithm (BFS with indegree) or DFS with post-order.

## When to Use
- Course scheduling
- Task ordering with dependencies
- Build systems
- Detecting cycles in directed graphs
- Alien dictionary
- Reconstructing sequences

## Why It Works
DAG has at least one vertex with indegree 0. Kahn's: repeatedly remove indegree-0 vertices. DFS: post-order of DFS gives reverse topological order.

## Intuition
Vertices with no prerequisites go first. Process them, remove their outgoing edges, new vertices may become available. If cycle exists, no valid ordering.

## Invariant
Kahn's: processed vertices form valid prefix of topological order; remaining graph maintains DAG property. DFS: post-order visit ensures dependencies come before dependents.

## Strengths
- O(V+E) linear time
- Detects cycles naturally
- Multiple valid orderings possible
- Simple implementation

## Weaknesses
- Only works on DAGs (no cycles)
- Ordering not unique
- Can't handle undirected graphs
- Need all vertices reachable from sources

## Recognition Signals
- "topological sort"
- "course schedule"
- "course schedule ii"
- "alien dictionary"
- "reconstruct itinerary"
- "task ordering"
- "build order"
- "sequence reconstruction"

## Common Failure Modes
- Not detecting cycles (Kahn's: processed count < V)
- DFS: not handling multiple connected components
- Incorrect indegree calculation
- Using on graph with cycles without detection
- Not handling isolated vertices

## Generalizations
Can find all topological orderings; can find lexicographically smallest (priority queue); parallel topological sort; dynamic topological sort.

## Complexity
- Time: O(V + E)
- Space: O(V) for indegree/visited/order, O(V) for queue/stack