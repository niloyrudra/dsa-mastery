---
id: "binary-tree-level-order-traversal"
title: "Binary Tree Level Order Traversal"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/binary-tree-level-order-traversal/"
patterns: ["tree-bfs"]
algorithms: ["bfs"]
dataStructures: ["binary-tree", "queue"]
templates: ["graph-template-ts"]
prerequisites: ["binary-tree", "queue"]
relatedProblems: ["binary-tree-right-side-view", "binary-tree-zigzag-level-order", "maximum-depth-of-binary-tree", "minimum-depth-of-binary-tree"]
status: "PUBLISHED"
orderIndex: 27
reasonForSelection: "BFS with level tracking; queue-based tree traversal"
---

# Problem Statement

Given the `root` of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).

## Constraints

- The number of nodes in the tree is in the range [0, 2000].
- -1000 ≤ Node.val ≤ 1000

## Examples

**Example 1:**
```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]
```

**Example 2:**
```
Input: root = [1]
Output: [[1]]
```

**Example 3:**
```
Input: root = []
Output: []
```

## Variations

### Variation 1: Binary Tree Zigzag Level Order Traversal
**Name**: Binary Tree Zigzag Level Order Traversal

**What Changed**: Alternate level order direction (left-to-right, then right-to-left).

**Original Approach Works**: BFS with level tracking, reverse alternate levels.

**What Breaks**: Need to track level parity and reverse.

**New Constraint**: Level 0: left-to-right, Level 1: right-to-left, etc.

**Algorithm Changes**: Yes - use deque or reverse list for alternate levels.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Binary Tree Right Side View
**Name**: Binary Tree Right Side View

**What Changed**: Return only rightmost node at each level.

**Original Approach Works**: BFS level order, take last element of each level.

**What Breaks**: Only need last node per level.

**New Constraint**: Return list of rightmost values.

**Algorithm Changes**: Simpler - track last node in each level.

**Complexity Changes**: Time O(n), Space O(n).