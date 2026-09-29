---
id: "count-good-nodes-in-binary-tree"
title: "Count Good Nodes in Binary Tree"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/count-good-nodes-in-binary-tree/"
patterns: ["tree-dfs"]
algorithms: ["dfs"]
dataStructures: ["binary-tree"]
templates: ["tree-dfs-template-ts"]
prerequisites: ["binary-tree", "recursion"]
relatedProblems: ["maximum-depth-of-binary-tree", "validate-binary-search-tree", "binary-tree-maximum-path-sum", "path-sum-iii"]
status: "PUBLISHED"
orderIndex: 30
reasonForSelection: "DFS with max-so-far tracking; path-based condition"
---

# Problem Statement

Given a binary tree `root`, a node `X` in the tree is named **good** if in the path from root to `X` there are no nodes with a value greater than `X`.

Return the number of **good** nodes in the binary tree.

## Constraints

- The number of nodes in the binary tree is in the range [1, 10⁵].
- -10⁴ ≤ Node.val ≤ 10⁴

## Examples

**Example 1:**
```
Input: root = [3,1,4,3,null,1,5]
Output: 4
Explanation: Nodes in blue are good.
Root Node (3) is always a good node.
Node 4 -> (3,4) is the maximum value in path starting from root.
Node 5 -> (3,4,5) is the maximum value in path starting from root.
Node 3 -> (3,1,3) is the maximum value in path starting from root.
```

**Example 2:**
```
Input: root = [3,3,null,4,2]
Output: 3
Explanation: Node 2 -> (3,3,2) is not good, because "3" is higher than it.
```

**Example 3:**
```
Input: root = [1]
Output: 1
Explanation: Root is considered as good.
```

## Variations

### Variation 1: Validate Binary Search Tree
**Name**: Validate Binary Search Tree

**What Changed**: Check if tree satisfies BST property.

**Original Approach Works**: DFS with bounds (min, max) passed down.

**What Breaks**: Different condition - bounds vs max-so-far.

**New Constraint**: Left subtree < node < right subtree.

**Algorithm Changes**: Pass min/max bounds instead of max-so-far.

**Complexity Changes**: Time O(n), Space O(h).

### Variation 2: Path Sum III
**Name**: Path Sum III

**What Changed**: Count paths summing to target.

**Original Approach Works**: DFS with prefix sum hash map.

**What Breaks**: Path can start/end anywhere, not just root-to-node.

**New Constraint**: Path sum equals target.

**Algorithm Changes**: Prefix sum + hash map, backtrack on return.

**Complexity Changes**: Time O(n), Space O(n).