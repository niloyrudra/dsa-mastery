---
id: "serialize-and-deserialize-binary-tree"
title: "Serialize and Deserialize Binary Tree"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/serialize-and-deserialize-binary-tree/"
patterns: ["tree-dfs", "tree-bfs"]
algorithms: ["dfs", "bfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts", "graph-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["construct-binary-tree-from-preorder-and-inorder-traversal", "encode-and-decode-strings", "same-tree", "binary-tree-level-order-traversal", "recover-binary-search-tree"]
status: "PUBLISHED"
orderIndex: 33
reasonForSelection: "Preorder with null markers (DFS) or level order (BFS); string serialization"
---

# Problem Statement

Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

## Constraints

- The number of nodes in the tree is in the range [0, 10⁴].
- -1000 ≤ Node.val ≤ 1000

## Examples

**Example 1:**
```
Input: root = [1,2,3,null,null,4,5]
Output: [1,2,3,null,null,4,5]
```

**Example 2:**
```
Input: root = []
Output: []
```

**Example 3:**
```
Input: root = [1]
Output: [1]
```

## Variations

### Variation 1: Construct Binary Tree from Preorder and Inorder Traversal
**Name**: Construct Binary Tree from Preorder and Inorder Traversal

**What Changed**: Build tree from two traversals.

**Original Approach Works**: Preorder gives root, inorder gives left/right.

**What Breaks**: Two arrays vs single string.

**New Constraint**: Build tree from preorder and inorder.

**Algorithm Changes**: Preorder root, find in inorder, recurse.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Encode and Decode Strings
**Name**: Encode and Decode Strings

**What Changed**: String array instead of binary tree.

**Original Approach Works**: Length-prefixed encoding.

**What Breaks**: Flat array vs tree structure.

**New Constraint**: Encode/decode string array.

**Algorithm Changes**: Prefix each string with length.

**Complexity Changes**: Time O(n), Space O(n).