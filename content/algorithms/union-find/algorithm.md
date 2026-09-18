---
id: "union-find"
name: "Union-Find (Disjoint Set Union)"
description: "Data structure for maintaining disjoint sets with union and find operations. Used for cycle detection, connected components, Kruskal's MST."
step_by_step_reasoning: "1. Initialize each element as its own parent. 2. Find with path compression: recursively find root, set parent to root. 3. Union by rank/size: attach smaller tree to larger root. 4. Check if two elements in same set: find(x) == find(y)."
complexity_analysis: "With path compression + union by rank: O(α(n)) amortized per operation, where α is inverse Ackermann function (effectively constant). O(n) space."
time_complexity: "O(α(n)) amortized"
space_complexity: "O(n)"
time_reasoning: "Path compression flattens tree, union by rank keeps depth minimal. Inverse Ackermann grows extremely slowly."
space_reasoning: "Parent array and rank/size array of size n."
status: "PUBLISHED"
---
# Union-Find (Disjoint Set Union)

## Overview
Union-Find maintains a collection of disjoint sets, supporting union (merge) and find (which set) operations efficiently.

## Operations
- **Find(x)**: Return representative (root) of set containing x
- **Union(x, y)**: Merge sets containing x and y
- **Connected(x, y)**: Check if x and y are in same set

## Optimizations
1. **Path Compression**: During find, make every node point directly to root
2. **Union by Rank/Size**: Attach smaller tree to larger tree's root

## Complexity
- Time: O(α(n)) amortized per operation (effectively constant)
- Space: O(n)

## Applications
- Cycle detection in undirected graphs
- Connected components
- Kruskal's MST algorithm
- Dynamic connectivity
- Percolation problems
- Image segmentation

## When to Use
- Need to track connectivity dynamically
- Processing edges offline (Kruskal)
- Finding connected components in graphs