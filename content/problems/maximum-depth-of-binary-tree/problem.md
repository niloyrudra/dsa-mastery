---
id: "maximum-depth-of-binary-tree"
title: "Maximum Depth of Binary Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/maximum-depth-of-binary-tree/"
patterns: ["tree-dfs", "tree-bfs"]
algorithms: ["dfs", "bfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts", "graph-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["diameter-of-binary-tree", "minimum-depth-of-binary-tree", "balanced-binary-tree", "binary-tree-level-order-traversal"]
status: "PUBLISHED"
orderIndex: 24
reasonForSelection: "Fundamental tree DFS; introduces depth calculation pattern"
---

# Problem Statement

Given the `root` of a binary tree, return its maximum depth.

A binary tree's **maximum depth** is the number of nodes along the longest path from the root node down to the farthest leaf node.

## Constraints

- The number of nodes in the tree is in the range [0, 10⁴].
- -100 ≤ Node.val ≤ 100

## Examples

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: 3
```

**Example 2:**
```
Input: root = [1,null,2]
Output: 2
```

**Example 3:**
```
Input: root = []
Output: 0
```

## Variations

### Variation 1: Minimum Depth of Binary Tree
**Name**: Minimum Depth of Binary Tree

**What Changed**: Find minimum depth instead of maximum.

**Original Approach Works**: DFS post-order or BFS level-order.

**What Breaks**: Node with only one child is not a leaf; min depth goes through that child.

**New Constraint**: Leaf = node with no children.

**Algorithm Changes**: BFS finds first leaf (shortest path). DFS must handle single-child nodes.

**Complexity Changes**: Time O(n), Space O(n) worst case.

### Variation 2: Balanced Binary Tree
**Name**: Balanced Binary Tree

**What Changed**: Check if tree is height-balanced (depth difference ≤ 1 for all nodes).

**Original Approach Works**: Post-order DFS returns height and balanced status.

**What Breaks**: Must check balance at every node, not just root.

**New Constraint**: |leftDepth - rightDepth| ≤ 1 for all nodes.

**Algorithm Changes**: Yes - return -1 or custom object to signal imbalance early.

**Complexity Changes**: Time O(n), Space O(h) recursion stack.