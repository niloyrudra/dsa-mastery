---
id: "kth-smallest-element-in-a-bst"
title: "Kth Smallest Element in a BST"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/kth-smallest-element-in-a-bst/"
patterns: ["bst-fundamentals", "tree-dfs"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["validate-binary-search-tree", "kth-largest-element-in-an-array", "lowest-common-ancestor-of-a-binary-search-tree", "binary-search"]
status: "PUBLISHED"
orderIndex: 27
reasonForSelection: "Inorder traversal with counter; BST property gives sorted order"
---

# Problem Statement

Given the `root` of a binary search tree, and an integer `k`, return the `k`th smallest value (1-indexed) of all the values of the nodes in the tree.

## Constraints

- The number of nodes in the tree is `n`.
- 1 ≤ k ≤ n ≤ 10⁴
- 0 ≤ Node.val ≤ 10⁴

## Examples

**Example 1:**
```
Input: root = [3,1,4,null,2], k = 1
Output: 1
```

**Example 2:**
```
Input: root = [5,3,6,2,4,null,null,1], k = 3
Output: 3
```

## Variations

### Variation 1: Kth Largest Element in an Array
**Name**: Kth Largest Element in an Array

**What Changed**: Array instead of BST; heap or QuickSelect.

**Original Approach Works**: Inorder traversal of BST gives sorted order.

**What Breaks**: Array not sorted.

**New Constraint**: Return kth largest from unsorted array.

**Algorithm Changes**: Min-heap of size k or QuickSelect.

**Complexity Changes**: Time O(n log k) or O(n), Space O(k) or O(1).

### Variation 2: Validate Binary Search Tree
**Name**: Validate Binary Search Tree

**What Changed**: Check BST property instead of finding kth.

**Original Approach Works**: Inorder traversal for validation.

**What Breaks**: Validation vs selection.

**New Constraint**: Return true if valid BST.

**Algorithm Changes**: Bounds passing or inorder with prev tracking.

**Complexity Changes**: Time O(n), Space O(h).