---
id: "binary-tree-right-side-view"
title: "Binary Tree Right Side View"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/binary-tree-right-side-view/"
patterns: ["tree-bfs"]
algorithms: ["bfs"]
dataStructures: ["binary-tree", "queue"]
templates: ["graph-template-ts"]
prerequisites: ["binary-tree", "queue"]
relatedProblems: ["binary-tree-level-order-traversal", "binary-tree-zigzag-level-order", "maximum-depth-of-binary-tree"]
status: "PUBLISHED"
orderIndex: 29
reasonForSelection: "BFS level traversal; take last node at each level"
---

# Problem Statement

Given the `root` of a binary tree, imagine yourself standing on the **right side** of it, return the values of the nodes you can see ordered from top to bottom.

## Constraints

- The number of nodes in the tree is in the range [0, 100].
- -100 ≤ Node.val ≤ 100

## Examples

**Example 1:**
```
Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]
```

**Example 2:**
```
Input: root = [1,null,3]
Output: [1,3]
```

**Example 3:**
```
Input: root = []
Output: []
```

## Variations

### Variation 1: Binary Tree Level Order Traversal
**Name**: Binary Tree Level Order Traversal

**What Changed**: Return all nodes at each level, not just rightmost.

**Original Approach Works**: Same BFS with level tracking.

**What Breaks**: Need to collect all nodes per level.

**New Constraint**: Return list of lists for each level.

**Algorithm Changes**: Collect all nodes in current level.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Binary Tree Zigzag Level Order Traversal
**Name**: Binary Tree Zigzag Level Order Traversal

**What Changed**: Alternate direction per level.

**Original Approach Works**: BFS with level tracking, reverse alternate levels.

**What Breaks**: Need to track level parity.

**New Constraint**: Level 0: left-to-right, Level 1: right-to-left.

**Algorithm Changes**: Reverse odd levels or use deque.

**Complexity Changes**: Time O(n), Space O(n).