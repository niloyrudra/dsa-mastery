---
id: "maximum-depth-of-binary-tree"
title: "Maximum Depth of Binary Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/maximum-depth-of-binary-tree/"
patterns: ["tree-dfs", "tree-bfs"]
algorithms: ["tree-dfs-recursive", "tree-bfs-iterative"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-recursive", "tree-bfs-iterative"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["minimum-depth-of-binary-tree", "diameter-of-binary-tree", "balanced-binary-tree"]
status: "PUBLISHED"
orderIndex: 45
reasonForSelection: "Simplest tree recursion; teaches both DFS and BFS approaches"
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

**What Changed**: Find shortest path to leaf instead of longest.

**Original Approach Works**: DFS still works, but must handle single-child nodes carefully.

**What Breaks**: For node with only one child, min depth is not 1 + min(left, right).

**New Constraint**: Leaf node = no children.

**Algorithm Changes**: Yes - check for single child, only traverse non-null children.

**Complexity Changes**: Time O(n) → O(n), Space O(h) → O(h).