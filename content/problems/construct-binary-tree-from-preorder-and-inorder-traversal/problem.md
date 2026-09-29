---
id: "construct-binary-tree-from-preorder-and-inorder-traversal"
title: "Construct Binary Tree from Preorder and Inorder Traversal"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/"
patterns: ["tree-dfs", "bst-fundamentals"]
algorithms: ["dfs"]
dataStructures: ["binary-tree", "hash-table"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion", "hash-table"]
relatedProblems: ["serialize-and-deserialize-binary-tree", "construct-binary-tree-from-inorder-and-postorder-traversal", "maximum-binary-tree"]
status: "PUBLISHED"
orderIndex: 33
reasonForSelection: "Recursive tree construction using hash map for index lookup"
---

# Problem Statement

Given two integer arrays `preorder` and `inorder` where `preorder` is the preorder traversal of a binary tree and `inorder` is the inorder traversal of the same tree, construct and return the binary tree.

## Constraints

- 1 ≤ preorder.length ≤ 3000
- inorder.length == preorder.length
- -3000 ≤ preorder[i], inorder[i] ≤ 3000
- `preorder` and `inorder` consist of **unique** values.
- Each value of `inorder` also appears in `preorder`.
- `preorder` is guaranteed to be the preorder traversal of the tree.
- `inorder` is guaranteed to be the inorder traversal of the tree.

## Examples

**Example 1:**
```
Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
Output: [3,9,20,null,null,15,7]
```

**Example 2:**
```
Input: preorder = [-1], inorder = [-1]
Output: [-1]
```

## Variations

### Variation 1: Construct Binary Tree from Inorder and Postorder Traversal
**Name**: Construct Binary Tree from Inorder and Postorder Traversal

**What Changed**: Postorder instead of preorder.

**Original Approach Works**: Same recursive approach, but root is last element of postorder.

**What Breaks**: Root index different; right subtree built before left.

**New Constraint**: Postorder traversal given.

**Algorithm Changes**: Root = postorder[postEnd]; build right first, then left.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Serialize and Deserialize Binary Tree
**Name**: Serialize and Deserialize Binary Tree

**What Changed**: Convert tree to string and back.

**Original Approach Works**: Preorder traversal with null markers.

**What Breaks**: String serialization format.

**New Constraint**: String representation with null markers.

**Algorithm Changes**: Serialize with preorder + "null"; deserialize with queue.

**Complexity Changes**: Time O(n), Space O(n).