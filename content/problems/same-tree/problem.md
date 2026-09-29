---
id: "same-tree"
title: "Same Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/same-tree/"
patterns: ["tree-dfs", "tree-bfs"]
algorithms: ["dfs", "bfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts", "graph-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["invert-binary-tree", "symmetric-tree", "subtree-of-another-tree", "serialize-and-deserialize-binary-tree", "validate-binary-search-tree"]
status: "PUBLISHED"
orderIndex: 25
reasonForSelection: "Simultaneous DFS on both trees; structural equality"
---

# Problem Statement

Given the roots of two binary trees `p` and `q`, write a function to check if they are the same or not.

Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.

## Constraints

- The number of nodes in both trees is in the range [0, 100].
- -10⁴ ≤ Node.val ≤ 10⁴

## Examples

**Example 1:**
```
Input: p = [1,2,3], q = [1,2,3]
Output: true
```

**Example 2:**
```
Input: p = [1,2], q = [1,null,2]
Output: false
```

**Example 3:**
```
Input: p = [1,2,1], q = [1,1,2]
Output: false
```

## Variations

### Variation 1: Symmetric Tree
**Name**: Symmetric Tree

**What Changed**: Check if tree is mirror of itself.

**Original Approach Works**: Compare left and right subtrees as mirrors.

**What Breaks**: Symmetry vs equality.

**New Constraint**: Return true if symmetric.

**Algorithm Changes**: Compare p.left with q.right, p.right with q.left.

**Complexity Changes**: Time O(n), Space O(h).

### Variation 2: Subtree of Another Tree
**Name**: Subtree of Another Tree

**What Changed**: Check if tree t is subtree of s.

**Original Approach Works**: Traverse s, check if any node matches t.

**What Breaks**: Subtree check vs equality.

**New Constraint**: Return true if t is subtree of s.

**Algorithm Changes**: Traverse s, call isSameTree at each node.

**Complexity Changes**: Time O(m*n), Space O(h).