---
id: "binary-tree-maximum-path-sum"
title: "Binary Tree Maximum Path Sum"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/binary-tree-maximum-path-sum/"
patterns: ["tree-dfs"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["diameter-of-binary-tree", "maximum-depth-of-binary-tree", "balanced-binary-tree", "path-sum-iii"]
status: "PUBLISHED"
orderIndex: 28
reasonForSelection: "DFS with global max tracking; path can go through root or not"
---

# Problem Statement

A **path** in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence **at most once**. Note that the path does not need to pass through the root.

The **path sum** of a path is the sum of the node's values in the path.

Given the `root` of a binary tree, return the maximum path sum of any non-empty path.

## Constraints

- The number of nodes in the tree is in the range [1, 3 × 10⁴].
- -1000 ≤ Node.val ≤ 1000

## Examples

**Example 1:**
```
Input: root = [1,2,3]
Output: 6
Explanation: The optimal path is 2 -> 1 -> 3 with a sum of 2 + 1 + 3 = 6.
```

**Example 2:**
```
Input: root = [-10,9,20,null,null,15,7]
Output: 42
Explanation: The optimal path is 15 -> 20 -> 7 with a sum of 15 + 20 + 7 = 42.
```

## Variations

### Variation 1: Diameter of Binary Tree
**Name**: Diameter of Binary Tree

**What Changed**: Find longest path (by edges) instead of maximum sum.

**Original Approach Works**: Same post-order DFS, track max edges instead of max sum.

**What Breaks**: No negative values in diameter (edges are always positive).

**New Constraint**: Return number of edges in longest path.

**Algorithm Changes**: Track `leftDepth + rightDepth` globally.

**Complexity Changes**: Time O(n), Space O(h).

### Variation 2: Path Sum III
**Name**: Path Sum III

**What Changed**: Count paths that sum to target (can start/end anywhere).

**Original Approach Works**: Prefix sum + hash map on tree paths.

**What Breaks**: Path doesn't need to go through root or leaf.

**New Constraint**: Path can start and end at any nodes.

**Algorithm Changes**: DFS with prefix sum hash map, backtrack on return.

**Complexity Changes**: Time O(n), Space O(n).