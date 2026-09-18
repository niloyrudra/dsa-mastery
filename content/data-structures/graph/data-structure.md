---
id: "graph"
name: "Graph"
description: "Vertices (nodes) connected by edges. Can be directed/undirected, weighted/unweighted."
operations: '{"add_vertex": "O(1)", "add_edge": "O(1)", "traverse": "O(V+E)", "shortest_path": "O((V+E)log V)", "mst": "O(E log V)"}'
why_useful_here: "Models relationships and networks. Social networks, maps, web pages, dependencies."
tradeoffs: "Complex algorithms needed. Multiple representations. Cycles complicate traversal. Memory for dense graphs."
alternatives: "Adjacency Matrix, Edge List, Implicit Graph"
status: "PUBLISHED"
---
# Graph

## Overview
Graph consists of vertices (nodes) connected by edges. Models pairwise relationships between objects.

## Representations
- **Adjacency List**: Array of lists. O(V+E) space. Best for sparse graphs.
- **Adjacency Matrix**: V×V matrix. O(V²) space. Best for dense graphs, edge existence check O(1).
- **Edge List**: List of (u, v, w). O(E) space. Good for edge iteration (Kruskal).

## Types
- **Undirected**: Edges bidirectional
- **Directed**: Edges one-way
- **Weighted**: Edges have costs
- **Unweighted**: All edges equal
- **Cyclic/Acyclic**: Contains cycles or not
- **Connected/Disconnected**: All vertices reachable

## Operations
| Operation | Adj List | Adj Matrix |
|-----------|----------|------------|
| Add Vertex | O(1) | O(V²) |
| Add Edge | O(1) | O(1) |
| Check Edge | O(deg) | O(1) |
| Traverse | O(V+E) | O(V²) |

## Key Algorithms
- **DFS/BFS**: Traversal, connected components
- **Dijkstra**: Single-source shortest path (non-negative)
- **Bellman-Ford**: Single-source (handles negative)
- **Floyd-Warshall**: All-pairs shortest path
- **Kruskal/Prim**: Minimum Spanning Tree
- **Topological Sort**: DAG ordering
- **Union-Find**: Dynamic connectivity

## When to Use
- Network modeling
- Social networks
- Routing/navigation
- Dependency resolution
- State space search