---
id: "validate-binary-search-tree"
title: "Validate Binary Search Tree"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/validate-binary-search-tree/"
patterns: ["tree-dfs", "bst-fundamentals"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["maximum-depth-of-binary-tree", "lowest-common-ancestor-of-a-binary-search-tree", "kth-smallest-element-in-a-bst", "insert-into-a-binary-search-tree", "binary-search"]
status: "PUBLISHED"
orderIndex: 25
reasonForSelection: "BST validation with bounds; core BST property verification"
---

# Problem Statement

Given the `root` of a binary tree, determine if it is a valid binary search tree (BST).

A **valid BST** is defined as follows:

- The left subtree of a node contains only nodes with keys **less than** the node's key.
- The right subtree of a node contains only nodes with keys **greater than** the node's key.
- Both the left and right subtrees must also be binary search trees.

## Constraints

- The number of nodes in the tree is in the range [0, 10⁴].
- -2³¹ ≤ Node.val ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: root = [2,1,3]
Output: true
```

**Example 2:**
```
Input: root = [5,1,4,null,null,3,6]
Output: false
Explanation: The root node's value is 5 but its right child's value is 4.
```

## Variations

### Variation 1: Lowest Common Ancestor of a BST
**Name**: Lowest Common Ancestor of a Binary Search Tree

**What Changed**: Find LCA using BST property.

**Original Approach Works**: BST property guides traversal.

**What Breaks**: LCA vs validation.

**New Constraint**: Return LCA node.

**Algorithm Changes**: Traverse based on BST property.

**Complexity Changes**: Time O(h), Space O(h).

### Variation 2: Kth Smallest Element in a BST
**Name**: Kth Smallest Element in a BST

**What Changed**: Find kth smallest via inorder.

**Original Approach Works**: Inorder traversal gives sorted order.

**What Breaks**: Selection vs validation.

**New Constraint**: Return kth smallest value.

**Algorithm Changes**: Inorder with counter.

**Complexity Changes**: Time O(k), Space O(h).