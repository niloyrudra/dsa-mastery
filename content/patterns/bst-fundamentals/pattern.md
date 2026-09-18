---
id: "bst-fundamentals"
name: "BST Fundamentals"
category: "Tree"
definition: "Binary Search Tree operations: search, insert, delete, validation, finding min/max, kth smallest, LCA, inorder traversal."
when_to_use: "Any BST operation; validating BST property; finding kth smallest/largest; range queries; closest value; converting to/from sorted structures."
why_it_works: "BST property (left < root < right) enables O(log n) average operations; inorder traversal yields sorted order."
intuition: "Leverage BST ordering property at each step - go left for smaller, right for larger. Inorder traversal visits nodes in sorted order."
invariant: "For every node: all values in left subtree < node.value < all values in right subtree."
strengths: "O(log n) average for search/insert/delete; natural ordering; inorder gives sorted sequence; efficient range queries."
weaknesses: "O(n) worst case if unbalanced; requires comparable elements; not suitable for non-ordered data."
recognition_signals:
  - "validate bst"
  - "kth smallest in bst"
  - "bst insertion/deletion"
  - "lowest common ancestor bst"
  - "closest value in bst"
  - "range sum bst"
  - "convert sorted array to bst"
common_failure_modes:
  - "Not checking BST property with proper bounds (using only parent value)"
  - "Incorrect handling of duplicate values"
  - "Not considering null nodes in validation"
  - "Stack overflow on skewed trees with recursion"
generalizations: "Can be extended to self-balancing trees (AVL, Red-Black); works with any comparable type; thread-safe variants exist."
time_complexity: "O(log n) average, O(n) worst"
space_complexity: "O(log n) average, O(n) worst (recursion stack)"
time_reasoning: "Height of tree determines operations; balanced = log n, skewed = n."
space_reasoning: "Recursion stack depth equals tree height."
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