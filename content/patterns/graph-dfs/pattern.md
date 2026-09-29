---
id: "graph-dfs"
name: "Graph Depth-First Search"
category: "GRAPHS"
patterns: []
algorithms: ["dfs"]
dataStructures: ["graph", "hash-table"]
templates: ["graph-template-ts"]
problems: ["number-of-islands", "clone-graph", "course-schedule", "pacific-atlantic-water-flow", "surrounded-regions"]
prerequisites: ["graph", "recursion"]
relatedPatterns: ["graph-bfs", "graph-union-find"]
status: "PUBLISHED"
---

# Graph Depth-First Search Pattern

## Definition

Exploring a graph by going as deep as possible along each branch before backtracking. Maintains a visited set to handle cycles. Can be recursive (with call stack) or iterative (explicit stack). The recursive call stack naturally enables backtracking and post-order processing.

## When to Use

- **Connected Components**: Count/traverse components (number of islands)
- **Cycle Detection**: Back-edge to ancestor in recursion stack (white-gray-black)
- **Topological Sort**: Post-order on DAG, reverse for ordering
- **Path Finding**: Find any path (not necessarily shortest)
- **Grid as Graph**: Implicit graph where cells are nodes, 4/8 neighbors are edges
- **Clone Graph**: Deep copy with cycle handling via hash map

## Why It Works

DFS explores exhaustively along each path. The visited set prevents infinite loops in cyclic graphs. Recursion stack provides natural path tracking. For grids, 2D coordinates become graph nodes with implicit edges to neighbors.

## Recognition Signals

- **Structural**: "Count connected components" → DFS from each unvisited node
- **Structural**: "Grid with connected regions" → Grid DFS (implicit graph)
- **Structural**: "Detect cycle in directed graph" → 3-color DFS (white=unvisited, gray=visiting, black=done)
- **Structural**: "Topological order" → Post-order DFS, reverse result
- **Structural**: "Clone graph with cycles" → DFS + hash map original→clone
- **Keywords**: "connected", "component", "cycle", "topological", "grid", "island", "region"

## Invariant

**Standard DFS**: All reachable nodes from start are visited exactly once.
**Cycle Detection**: Gray nodes form current recursion path; back-edge to gray = cycle.
**Topological**: Post-order ensures dependencies appear before dependents.

## Strengths

- Natural for connected components and cycle detection
- Post-order enables topological sort
- Low memory for path tracking (recursion stack)
- Grid DFS is compact and intuitive

## Weaknesses

- Stack overflow on deep graphs (iterative or BFS preferred)
- Doesn't find shortest path (use BFS)
- Recursion depth limited by call stack
- 3-color tracking adds complexity for cycle detection

## Common Failure Modes

1. **Not marking visited before recursing**: Causes infinite recursion on cycles
2. **Using visited instead of 3-color for directed cycle detection**: False positives on cross edges
3. **Forgetting to check bounds in grid DFS**: Index out of bounds
4. **Not handling multiple components**: Must loop through all nodes, start DFS if unvisited
5. **Modifying graph during iteration**: Use visited set, don't modify adjacency

## Generalizations

- **DFS with Memoization**: Cache results for DP on DAGs
- **Tarjan's Algorithm**: SCC with single DFS pass (low-link values)
- **Bridge/Articulation Points**: Low-link values in undirected graphs
- **Iterative Deepening**: DFS with depth limit, increase until found

## Complexity

- Time: O(V + E) - visits each vertex and edge once
- Space: O(V) visited set + O(V) recursion stack (O(V) worst case)

## Example Problems

- **Number of Islands**: Grid DFS, mark '1' as '0' or visited
- **Clone Graph**: DFS + hash map original→clone, handle cycles
- **Course Schedule**: Cycle detection in directed graph (3-color)
- **Course Schedule II**: Topological sort (post-order reverse)
- **Pacific Atlantic Water Flow**: Multi-source reverse flow DFS from boundaries
- **Surrounded Regions**: Boundary-first DFS, mark border-connected, flip rest