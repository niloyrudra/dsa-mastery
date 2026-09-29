---
id: "diameter-of-binary-tree"
title: "Diameter of Binary Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/diameter-of-binary-tree/"
patterns: ["tree-dfs"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["maximum-depth-of-binary-tree", "binary-tree-maximum-path-sum", "balanced-binary-tree"]
status: "PUBLISHED"
orderIndex: 25
reasonForSelection: "DFS returns depth, updates global diameter; path through node"
---

# Problem Statement

Given the `root` of a binary tree, return the length of the **diameter** of the tree.

The **diameter** of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.

The length of a path between two nodes is represented by the number of edges between them.

## Constraints

- The number of nodes in the tree is in the range [1, 10⁴].
- -100 ≤ Node.val ≤ 100

## Examples

**Example 1:**
```
Input: root = [1,2,3,4,5]
Output: 3
Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].
```

**Example 2:**
```
Input: root = [1,2]
Output: 1
```

## Variations

### Variation 1: Binary Tree Maximum Path Sum
**Name**: Binary Tree Maximum Path Sum

**What Changed**: Path can have negative values; find maximum sum instead of longest path.

**Original Approach Works**: Post-order DFS, but track max sum instead of max length.

**What Breaks**: Path can be just a single node if all negative; must handle negative values.

**New Constraint**: Path sum = sum of node values; return maximum.

**Algorithm Changes**: Yes - track max sum globally, return max gain to parent.

**Complexity Changes**: Time O(n), Space O(h) recursion stack.

### Variation 2: Maximum Depth of Binary Tree
**Name**: Maximum Depth of Binary Tree

**What Changed**: Find maximum depth instead of diameter.

**Original Approach Works**: Same post-order DFS, but return 1 + max(left, right).

**What Breaks**: Simpler - just depth, not diameter.

**New Constraint**: Return depth (number of nodes on longest path).

**Algorithm Changes**: Simpler - no global variable needed.

**Complexity Changes**: Time O(n), Space O(h).