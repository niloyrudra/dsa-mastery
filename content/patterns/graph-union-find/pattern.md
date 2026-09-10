---
id: "graph-union-find"
name: "Union-Find (Disjoint Set Union)"
category: "GRAPHS"
patterns: []
algorithms: ["union-find", "union-find-path-compression", "union-find-union-by-rank"]
dataStructures: ["array", "union-find"]
templates: ["union-find"]
problems: ["number-of-islands", "number-of-connected-components", "graph-valid-tree", "redundant-connection", "accounts-merge"]
prerequisites: ["arrays", "graphs"]
relatedPatterns: ["graph-dfs", "graph-bfs", "graph-kruskal"]
status: "PUBLISHED"
---

# Union-Find (Disjoint Set Union) Pattern

## Definition

Data structure for maintaining a collection of disjoint sets, supporting two operations: `find(x)` returns the representative of x's set, `union(x, y)` merges the sets containing x and y. With path compression and union by rank, both operations run in amortized O(α(n)) time (inverse Ackermann, < 5).

## When to Use

- **Dynamic Connectivity**: Check if two nodes connected, merge components
- **Connected Components**: Count components, find which component each node belongs to
- **Cycle Detection (Undirected)**: If union(x,y) finds same root, cycle exists
- **Kruskal's MST**: Sort edges, add if they connect different components
- **Grid Connectivity**: 2D to 1D index mapping for grid problems
- **Offline Queries**: Process queries in batches when structure doesn't change

## Why It Works

Each component forms a tree rooted at its representative. `find` follows parent pointers to root. Path compression flattens tree by making all visited nodes point directly to root. Union by rank attaches shorter tree to taller tree, keeping height logarithmic.

## Recognition Signals

- **Structural**: "Are two nodes connected?" → find(x) == find(y)
- **Structural**: "Merge two groups / components" → union(x, y)
- **Structural**: "Count connected components" → track component count, decrement on successful union
- **Structural**: "Detect cycle in undirected graph" → union returns false if same root
- **Structural**: "Dynamic connectivity" → online union/find operations
- **Keywords**: "connected components", "union find", "disjoint set", "merge", "cycle detection", "Kruskal"

## Invariant

- Each component is a tree rooted at its representative (parent[root] = root)
- Path compression ensures amortized O(α(n)) find
- Union by rank ensures tree height O(log n)

## Strengths

- Near-constant time per operation (amortized)
- Excellent for dynamic connectivity
- Natural for cycle detection in undirected graphs
- Works well with offline query processing
- Simple implementation, small memory footprint

## Weaknesses

- Not suitable for directed graphs (use DFS 3-color)
- Hard to support deletion/splitting (fully dynamic needs complex structures)
- No path information (just connectivity, not actual path)
- Requires known universe size upfront (or dynamic arrays)

## Common Failure Modes

1. **Missing path compression**: Degrades to O(n) find
2. **Not checking if already connected**: Wasted union, wrong cycle detection
3. **Wrong 2D to 1D index**: `r * cols + c` not `r * rows + c`
4. **Not initializing parent array**: All nodes must start as own parent
5. **Using for directed cycle detection**: Union-Find only works for undirected

## Generalizations

- **Union by Size**: Track component sizes, attach smaller to larger
- **Weighted Union-Find**: Maintain weights/distances to root
- **Rollback Union-Find**: Persistent/undo operations for divide-and-conquer
- **2D Grid Union-Find**: Index = r * cols + c, union adjacent cells

## Complexity

- Time: O(α(n)) amortized per operation (α = inverse Ackermann, < 5 for all practical n)
- Space: O(n) for parent and rank/size arrays

## Example Problems

- **Number of Islands**: Union adjacent land cells, count final components
- **Number of Connected Components**: Union all edges, count components
- **Graph Valid Tree**: Union all edges; valid if no cycles (union returns false) and connected (1 component)
- **Redundant Connection**: First edge where union returns false
- **Accounts Merge**: Union emails in same account, group by root
- **Swim in Rising Water**: Binary search + Union-Find (connect cells ≤ threshold)
- **Min Cost to Connect All Points**: Kruskal's MST with Union-Find