---
id: "tree-dfs"
name: "Tree Depth-First Search"
category: "TREES"
patterns: []
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
problems: ["maximum-depth-of-binary-tree", "validate-binary-search-tree", "diameter-of-binary-tree", "invert-binary-tree", "lowest-common-ancestor-of-a-binary-search-tree", "minimum-path-sum"]
prerequisites: ["binary-tree", "recursion"]
relatedPatterns: ["tree-bfs"]
status: "PUBLISHED"
---

# Tree Depth-First Search Pattern

## Definition

Recursively or iteratively traversing a tree by exploring as far as possible along each branch before backtracking. The three classic orders (preorder, inorder, postorder) correspond to when the node is processed relative to its children. Post-order is especially powerful for computing subtree properties bottom-up.

## When to Use

- **Preorder**: Copy tree, serialize, path-based problems (process node before children)
- **Inorder**: BST operations (process left, node, right → sorted order)
- **Postorder**: Compute subtree properties, validate, diameter (process children before node)
- **Bounds/Context Passing**: Validate BST (pass min/max down), path sum (pass accumulated sum)
- Any problem requiring visiting all nodes or computing properties from subtrees

## Why It Works

Trees have natural recursive structure. Each node's subtree is itself a tree. DFS exploits this: solve for children, then combine for parent. Post-order computes bottom-up, enabling global properties (max depth, diameter, validity) from local subtree information.

## Recognition Signals

- **Structural**: "Tree property from subtrees" → postorder (depth, diameter, balance)
- **Structural**: "Path from root to leaf" → preorder with path tracking
- **Structural**: "BST validation / sorted order" → inorder or bounds-passing
- **Structural**: "All paths / combinations" → preorder with backtracking
- **Structural**: "Ancestor / LCA" → postorder returns found status from children
- **Keywords**: "depth", "diameter", "validate", "path", "ancestor", "subtree", "bottom-up"

## Invariant

**Postorder**: After processing node, its return value correctly represents the computed property for the entire subtree rooted at that node.

**Bounds-passing**: All nodes in left subtree are < current node, all in right subtree are > current node (for BST).

## Strengths

- Natural fit for recursive tree structure
- Post-order enables bottom-up computation of global properties
- O(h) space (recursion stack) where h is height
- Can pass context down (bounds, path, accumulated values)

## Weaknesses

- Stack overflow risk on skewed trees (O(n) recursion depth)
- Iterative version requires explicit stack (more complex)
- Not ideal for level-by-level processing (use BFS)
- Can recompute same values if not memoized (use DP on trees)

## Common Failure Modes

1. **Wrong traversal order**: Using preorder when postorder needed (e.g., depth, diameter)
2. **Missing base case**: `if (!node) return ...` causes null pointer or wrong values
3. **Not handling single-child nodes**: In min depth, node with only one child ≠ leaf
4. **Wrong bounds in BST validation**: Must use strict < and > with proper bounds propagation
5. **Stack overflow**: Deep skewed trees; consider iterative or increase stack

## Generalizations

- **DP on Trees**: Memoize subtree results for overlapping subproblems
- **Rerooting DP**: Compute answer for all roots using two passes
- **Tree Isomorphism**: Canonical form via sorted child representations
- **Heavy-Light Decomposition**: Path queries on trees

## Complexity

- Time: O(n) - visits each node once
- Space: O(h) recursion stack (O(n) worst case skewed, O(log n) balanced)

## Example Problems

- **Maximum Depth**: Postorder, 1 + max(left, right)
- **Validate BST**: Bounds-passing (min, max) or inorder with prev
- **Diameter**: Postorder with global max, return max depth
- **Symmetric Tree**: Mirror comparison (two trees simultaneously)
- **Invert Tree**: Preorder swap left/right
- **LCA**: Postorder, return node if found in left/right or is target
- **Path Sum**: Preorder with running sum, check at leaf