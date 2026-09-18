---
id: "binary-tree"
name: "Binary Tree"
description: "Hierarchical structure with at most two children per node (left and right)."
operations: '{"access": "O(n)", "search": "O(n)", "insert": "O(n)", "delete": "O(n)", "traverse": "O(n)"}'
why_useful_here: "Foundation for BST, heaps, tries, segment trees. Natural for hierarchical data."
tradeoffs: "Unbalanced trees degrade to O(n) linked list. No ordering guarantee. Complex deletion."
alternatives: "AVL Tree, Red-Black Tree, B-Tree, Trie"
status: "PUBLISHED"
---
# Binary Tree

## Overview
Binary tree is a hierarchical data structure where each node has at most two children (left and right).

## Traversals
- **Preorder**: Root -> Left -> Right
- **Inorder**: Left -> Root -> Right (sorted for BST)
- **Postorder**: Left -> Right -> Root
- **Level Order**: Breadth-first by levels

## Properties
- Max nodes at level L: 2^L
- Max nodes in height h: 2^(h+1) - 1
- Min height for n nodes: ⌊log₂(n)⌋
- Full/Complete/Perfect/Balanced variants

## Operations
| Operation | Time Complexity |
|-----------|----------------|
| Search | O(n) |
| Insert | O(n) |
| Delete | O(n) |
| Traverse | O(n) |
| Height | O(n) |

## Types
- **Full**: Every node has 0 or 2 children
- **Complete**: All levels filled except last, left-aligned
- **Perfect**: All internal nodes have 2 children, all leaves same level
- **Balanced**: Height difference ≤ 1 for all nodes
- **BST**: Left < Root < Right

## When to Use
- Hierarchical data representation
- Expression trees
- Decision trees
- File systems
- Basis for BST, Heap, Trie, Segment Tree