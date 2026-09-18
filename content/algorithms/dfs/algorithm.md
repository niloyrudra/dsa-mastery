---
id: "dfs"
name: "Depth First Search"
description: "Graph traversal exploring as far as possible along each branch before backtracking."
step_by_step_reasoning: "1. Start at source vertex. 2. Mark as visited. 3. For each unvisited neighbor, recursively call DFS. 4. Backtrack when no unvisited neighbors. 5. Use stack explicitly for iterative version."
complexity_analysis: "Visits each vertex and edge exactly once. Time: O(V + E). Space: O(V) for visited set and recursion stack (or explicit stack)."
time_complexity: "O(V + E)"
space_complexity: "O(V)"
time_reasoning: "Each vertex processed once, each edge examined once (twice for undirected)."
space_reasoning: "Visited array O(V), recursion stack O(V) worst case (deep path)."
status: "PUBLISHED"
---
# Depth First Search (DFS)

## Overview
DFS explores a graph by going as deep as possible before backtracking. Fundamental for many graph algorithms.

## Algorithm Steps
1. Mark current vertex as visited
2. For each unvisited neighbor, recursively call DFS
3. Backtrack when all neighbors visited

## Variants
- **Recursive**: Simple, uses call stack
- **Iterative**: Explicit stack, avoids stack overflow
- **With timestamps**: Pre/post order for topological sort, cycle detection

## Applications
- Cycle detection
- Topological sort
- Connected components
- Path finding
- Maze solving
- Tree traversals (pre/in/post-order)

## Complexity
- Time: O(V + E)
- Space: O(V)

## When to Use
- Need to explore all paths
- Finding connected components
- Topological sorting
- Cycle detection
- When graph is deep rather than wide