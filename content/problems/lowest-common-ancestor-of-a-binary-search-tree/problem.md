---
id: "lowest-common-ancestor-of-a-binary-search-tree"
title: "Lowest Common Ancestor of a Binary Search Tree"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/"
patterns: ["bst-fundamentals", "tree-dfs"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["validate-binary-search-tree", "kth-smallest-element-in-a-bst", "binary-search", "lowest-common-ancestor-of-a-binary-tree"]
status: "PUBLISHED"
orderIndex: 28
reasonForSelection: "BST property guides traversal; LCA between p and q"
---

# Problem Statement

Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.

According to the definition of LCA on Wikipedia: "The lowest common ancestor is defined between two nodes `p` and `q` as the lowest node in `T` that has both `p` and `q` as descendants (where we allow **a node to be a descendant of itself**)."

## Constraints

- The number of nodes in the tree is in the range [2, 10⁵].
- -10⁹ ≤ Node.val ≤ 10⁹
- All Node.val are **unique**.
- p != q
- p and q will exist in the BST.

## Examples

**Example 1:**
```
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
Output: 6
Explanation: The LCA of nodes 2 and 8 is 6.
```

**Example 2:**
```
Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
Output: 2
Explanation: The LCA of nodes 2 and 4 is 2, since a node can be a descendant of itself.
```

## Variations

### Variation 1: Lowest Common Ancestor of a Binary Tree
**Name**: Lowest Common Ancestor of a Binary Tree

**What Changed**: General binary tree (not BST).

**Original Approach Works**: Post-order DFS returns found status from children.

**What Breaks**: No BST property to guide traversal.

**New Constraint**: Return LCA in general binary tree.

**Algorithm Changes**: Post-order: if node is p or q, return node; else return left || right.

**Complexity Changes**: Time O(n), Space O(h).

### Variation 2: Validate Binary Search Tree
**Name**: Validate Binary Search Tree

**What Changed**: Check BST property instead of finding LCA.

**Original Approach Works**: BST property guides traversal.

**What Breaks**: Validation vs search.

**New Constraint**: Return true if valid BST.

**Algorithm Changes**: Bounds passing or inorder with prev tracking.

**Complexity Changes**: Time O(n), Space O(h).