---
id: "tree-bfs"
name: "Tree Breadth-First Search"
category: "TREES"
patterns: []
algorithms: ["bfs"]
dataStructures: ["binary-tree", "queue"]
templates: ["graph-template-ts"]
problems: ["binary-tree-level-order-traversal", "binary-tree-right-side-view"]
prerequisites: ["binary-tree", "queue"]
relatedPatterns: ["tree-dfs", "graph-bfs"]
status: "PUBLISHED"
---

# Tree Breadth-First Search Pattern

## Definition

Level-order traversal of a tree using a queue. Processes all nodes at depth d before any nodes at depth d+1. Naturally tracks depth and enables level-specific operations (rightmost node, zigzag reversal, level sums).

## When to Use

- **Level Order**: Need nodes grouped by depth
- **Right Side View**: Last node at each level
- **Zigzag**: Alternate level direction
- **Minimum Depth**: First leaf encountered (BFS finds shortest path)
- **Cousins / Level Properties**: Need sibling relationships or level aggregates
- When shortest path in tree is needed (unweighted)

## Why It Works

Queue ensures FIFO processing: nodes enqueued at depth d are processed before nodes enqueued at depth d+1. By capturing queue size at start of level, we can process exactly one level per iteration.

## Recognition Signals

- **Structural**: "Level by level" → BFS with level size capture
- **Structural**: "Rightmost / leftmost at each level" → BFS, track last/first in level
- **Structural**: "Minimum depth / shortest path in tree" → BFS finds first leaf
- **Structural**: "Zigzag / alternate levels" → BFS with level parity flag
- **Structural**: "Connect nodes at same level" → BFS with prev pointer
- **Keywords**: "level order", "breadth first", "level", "depth", "right side", "zigzag"

## Invariant

At start of each while iteration, queue contains exactly all nodes at current depth.

## Strengths

- Naturally finds minimum depth (first leaf)
- Processes level-by-level without recursion
- Easy to track level number and nodes per level
- No stack overflow risk (uses explicit queue)

## Weaknesses

- O(w) space where w is max width (can be O(n) for complete trees)
- Not suitable for bottom-up computation (use DFS postorder)
- Requires explicit queue management
- Less natural for path-from-root problems

## Common Failure Modes

1. **Not capturing level size**: Using `queue.length` directly in for loop condition (grows during loop)
2. **Forgetting to check null**: Enqueueing null children pollutes queue
3. **Wrong level order**: Processing children before recording current level values
4. **Not handling empty tree**: `if (!root) return []` needed

## Generalizations

- **Multi-source BFS**: Start with multiple nodes in queue (rotting oranges, walls and gates)
- **0-1 BFS**: Deque for edges with weight 0 or 1
- **Bidirectional BFS**: Meet in middle for shortest path
- **BFS on Graph**: Same pattern, adjacency list instead of left/right

## Complexity

- Time: O(n) - visits each node once
- Space: O(w) queue (O(n) worst case complete tree, O(1) skewed)

## Example Problems

- **Binary Tree Level Order Traversal**: Standard level order
- **Binary Tree Zigzag Level Order**: Reverse alternate levels
- **Binary Tree Right Side View**: Last element of each level
- **Maximum Depth**: Count levels
- **Minimum Depth**: Return depth when first leaf found
- **Populating Next Right Pointers**: Connect during level traversal