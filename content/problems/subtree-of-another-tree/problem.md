---
id: "subtree-of-another-tree"
title: "Subtree of Another Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/subtree-of-another-tree/"
patterns: ["tree-dfs", "tree-bfs"]
algorithms: ["dfs", "bfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts", "graph-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["same-tree", "serialize-and-deserialize-binary-tree", "maximum-depth-of-binary-tree", "construct-binary-tree-from-preorder-and-inorder-traversal", "binary-tree-maximum-path-sum"]
status: "PUBLISHED"
orderIndex: 26
reasonForSelection: "DFS with same-tree check at each node; serialization alternative"
---

# Problem Statement

Given the roots of two binary trees `root` and `subRoot`, return `true` if there is a subtree of `root` with the same structure and node values of `subRoot` and `false` otherwise.

A subtree of a binary tree `tree` is a tree that consists of a node in `tree` and all of this node's descendants. The tree `tree` could also be considered as a subtree of itself.

## Constraints

- The number of nodes in the `root` tree is in the range [1, 2000].
- The number of nodes in the `subRoot` tree is in the range [1, 1000].
- -10⁴ ≤ Node.val ≤ 10⁴

## Examples

**Example 1:**
```
Input: root = [3,4,5,1,2], subRoot = [4,1,2]
Output: true
```

**Example 2:**
```
Input: root = [3,4,5,1,2,null,null,null,null,0], subRoot = [4,1,2]
Output: false
```

## Variations

### Variation 1: Same Tree
**Name**: Same Tree

**What Changed**: Check if two trees are identical.

**Original Approach Works**: Simultaneous DFS on both trees.

**What Breaks**: Full tree equality vs subtree check.

**New Constraint**: Return true if same.

**Algorithm Changes**: Compare both trees simultaneously.

**Complexity Changes**: Time O(n), Space O(h).

### Variation 2: Serialize and Deserialize Binary Tree
**Name**: Serialize and Deserialize Binary Tree

**What Changed**: String serialization instead of subtree check.

**Original Approach Works**: Preorder traversal with null markers.

**What Breaks**: String vs tree comparison.

**New Constraint**: Serialize to string, deserialize back.

**Algorithm Changes**: Preorder with "null" markers.

**Complexity Changes**: Time O(n), Space O(n).