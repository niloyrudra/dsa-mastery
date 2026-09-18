---
id: "topological-sort"
name: "Topological Sort (Kahn's Algorithm)"
description: "Linear ordering of vertices in a DAG such that for every edge u->v, u comes before v."
step_by_step_reasoning: "1. Calculate indegree of all vertices. 2. Queue all vertices with indegree 0. 3. While queue not empty: pop vertex, add to result. 4. For each neighbor: decrement indegree, if 0 enqueue. 5. If result size < V, cycle exists."
complexity_analysis: "Each vertex enqueued/dequeued once. Each edge processed once for indegree decrement. Time: O(V + E). Space: O(V) for indegree, queue, result."
time_complexity: "O(V + E)"
space_complexity: "O(V)"
time_reasoning: "V vertices processed, E edges examined for indegree updates."
space_reasoning: "Indegree array O(V), queue O(V), result O(V)."
status: "PUBLISHED"
---
# Topological Sort (Kahn's Algorithm)

## Overview
Topological sort orders vertices in a Directed Acyclic Graph (DAG) so that all dependencies come before dependents.

## Algorithm Steps (Kahn's BFS)
1. Compute indegree of all vertices
2. Enqueue all vertices with indegree 0
3. While queue not empty:
   - Dequeue vertex u, add to result
   - For each neighbor v of u:
     - indegree[v]--
     - If indegree[v] == 0, enqueue v
4. If result.size() < V, graph has a cycle

## Alternative: DFS-based
- Run DFS, add vertices to result in post-order
- Reverse result for topological order
- Also detects cycles (back edges)

## Complexity
- Time: O(V + E)
- Space: O(V)

## When to Use
- Course scheduling / task ordering
- Build systems
- Dependency resolution
- Cycle detection in directed graphs
- Alien dictionary