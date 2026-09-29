---
id: "bst-fundamentals"
name: "BST Fundamentals"
category: "TREES"
patterns: []
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
problems: ["validate-binary-search-tree", "kth-smallest-element-in-a-bst", "lowest-common-ancestor-of-a-binary-search-tree", "construct-binary-tree-from-preorder-and-inorder-traversal", "binary-search"]
prerequisites: ["binary-tree", "recursion"]
relatedPatterns: ["tree-dfs", "tree-bfs"]
status: "PUBLISHED"
---
# BST Fundamentals Pattern

## Definition
Binary Search Tree operations: search, insert, delete, validation, finding min/max, kth smallest, LCA, inorder traversal.

## When to Use
- Any BST operation
- Validating BST property
- Finding kth smallest/largest
- Range queries
- Closest value
- Converting to/from sorted structures

## Why It Works
BST property (left < root < right) enables O(log n) average operations; inorder traversal yields sorted order.

## Intuition
Leverage BST ordering property at each step - go left for smaller, right for larger. Inorder traversal visits nodes in sorted order.

## Invariant
For every node: all values in left subtree < node.value < all values in right subtree.

## Strengths
- O(log n) average for search/insert/delete
- Natural ordering
- Inorder gives sorted sequence
- Efficient range queries

## Weaknesses
- O(n) worst case if unbalanced
- Requires comparable elements
- Not suitable for non-ordered data

## Recognition Signals
- "validate bst"
- "kth smallest in bst"
- "bst insertion/deletion"
- "lowest common ancestor bst"
- "closest value in bst"
- "range sum bst"
- "convert sorted array to bst"

## Common Failure Modes
- Not checking BST property with proper bounds (using only parent value)
- Incorrect handling of duplicate values
- Not considering null nodes in validation
- Stack overflow on skewed trees with recursion

## Generalizations
Can be extended to self-balancing trees (AVL, Red-Black); works with any comparable type; thread-safe variants exist.

## Complexity
- Time: O(log n) average, O(n) worst
- Space: O(log n) average, O(n) worst (recursion stack)