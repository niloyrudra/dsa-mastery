---
id: "invert-binary-tree"
title: "Invert Binary Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/invert-binary-tree/"
patterns: ["tree-dfs"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["maximum-depth-of-binary-tree", "symmetric-tree", "same-tree", "binary-tree-maximum-path-sum"]
status: "PUBLISHED"
orderIndex: 24
reasonForSelection: "Simple DFS swap; mirror tree construction"
---

# Problem Statement

Given the `root` of a binary tree, invert the tree, and return its root.

## Constraints

- The number of nodes in the tree is in the range [0, 100].
- -100 ≤ Node.val ≤ 100

## Examples

**Example 1:**
```
Input: root = [4,2,7,1,3,6,9]
Output: [4,7,2,9,6,3,1]
```

**Example 2:**
```
Input: root = [2,1,3]
Output: [2,3,1]
```

**Example 3:**
```
Input: root = []
Output: []
```

## Variations

### Variation 1: Symmetric Tree
**Name**: Symmetric Tree

**What Changed**: Check if tree is symmetric (mirror of itself).

**Original Approach Works**: Compare left and right subtrees as mirrors.

**What Breaks**: Check symmetry vs invert.

**New Constraint**: Return true if symmetric.

**Algorithm Changes**: Recursive mirror comparison.

**Complexity Changes**: Time O(n), Space O(h).

### Variation 2: Same Tree
**Name**: Same Tree

**What Changed**: Check if two trees are identical.

**Original Approach Works**: Compare both trees simultaneously.

**What Breaks**: Two trees vs one tree inversion.

**New Constraint**: Return true if same.

**Algorithm Changes**: Compare nodes pairwise.

**Complexity Changes**: Time O(n), Space O(h).