---
id: "balanced-binary-tree"
title: "Balanced Binary Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/balanced-binary-tree/"
patterns: ["tree-dfs"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["maximum-depth-of-binary-tree", "diameter-of-binary-tree", "binary-tree-maximum-path-sum"]
status: "PUBLISHED"
orderIndex: 26
reasonForSelection: "DFS with height checking; returns height and balance in one pass"
---

# Problem Statement

Given a binary tree, determine if it is **height-balanced**.

A height-balanced binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than 1.

## Constraints

- The number of nodes in the tree is in the range [0, 5000].
- -10⁴ ≤ Node.val ≤ 10⁴

## Examples

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: true
```

**Example 2:**
```
Input: root = [1,2,2,3,3,null,null,4,4]
Output: false
```

**Example 3:**
```
Input: root = []
Output: true
```

## Variations

### Variation 1: Maximum Depth of Binary Tree
**Name**: Maximum Depth of Binary Tree

**What Changed**: Find maximum depth instead of checking balance.

**Original Approach Works**: Same post-order DFS, but return height directly.

**What Breaks**: Simpler - no balance checking needed.

**New Constraint**: Return maximum depth.

**Algorithm Changes**: Simpler - return 1 + max(left, right).

**Complexity Changes**: Time O(n), Space O(h).

### Variation 2: Diameter of Binary Tree
**Name**: Diameter of Binary Tree

**What Changed**: Find longest path between any two nodes.

**Original Approach Works**: Post-order DFS returns height, updates global diameter.

**What Breaks**: Tracks diameter globally, not balance.

**New Constraint**: Return diameter (number of edges).

**Algorithm Changes**: Track max(left + right) globally.

**Complexity Changes**: Time O(n), Space O(h).