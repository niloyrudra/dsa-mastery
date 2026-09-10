---
id: "tree-dfs-recursive"
name: "Tree DFS Recursive Template"
pattern: "tree-dfs"
language: "TYPESCRIPT"
purpose: "Traverse binary tree recursively (pre/in/post-order)"
whenToReachForIt: "Tree problems requiring visiting all nodes, computing subtree properties"
prerequisites: ["binary-tree", "recursion"]
mentalModel: "Visit node, then recursively visit children"
status: "PUBLISHED"
---

# Template: Tree DFS Recursive

## Code
```typescript
class TreeNode {
    val: number;
    left: TreeNode | null;
    right: TreeNode | null;
    constructor(val?: number, left?: TreeNode | null, right?: TreeNode | null) {
        this.val = (val === undefined ? 0 : val);
        this.left = (left === undefined ? null : left);
        this.right = (right === undefined ? null : right);
    }
}

// PREORDER: Process node, then children
function preorder(node: TreeNode | null, result: number[]): void {
    if (!node) return;           // [1] Base case
    result.push(node.val);       // [2] Process current node
    preorder(node.left, result); // [3] Recurse left
    preorder(node.right, result); // [4] Recurse right
}

// INORDER: Left, node, right (sorted for BST)
function inorder(node: TreeNode | null, result: number[]): void {
    if (!node) return;           // [1]
    inorder(node.left, result);  // [2] Left first
    result.push(node.val);       // [3] Then node
    inorder(node.right, result); // [4] Then right
}

// POSTORDER: Children first, then node
function postorder(node: TreeNode | null): number {
    if (!node) return 0;         // [1] Base case returns identity
    const left = postorder(node.left);   // [2] Left result
    const right = postorder(node.right); // [3] Right result
    return combine(left, right, node.val); // [4] Combine with current
}

function combine(left: number, right: number, val: number): number {
    // Problem-specific combination logic
    return 1 + Math.max(left, right); // e.g., max depth
}
```

## Explanation
[1] Base case: null node returns identity value (0, null, empty array)
[2] Preorder: process node BEFORE children (copy tree, serialize)
[3-4] Recurse on left then right subtree
[5] Inorder: left subtree, then node, then right subtree (BST sort)
[6] Postorder: children first, then combine (depth, diameter, validation)

## Invariant
Each recursive call correctly computes the answer for its subtree.

## Common Modifications
- Pass additional state (parent, depth, path, bounds)
- Return multiple values via object/array
- Early termination (short-circuit)
- Iterative version with explicit stack

## Common Mistakes
- Missing base case (stack overflow)
- Not handling null children
- Wrong order of operations for traversal type
- Forgetting to combine results in postorder

## Complexity
- Time: O(n) - visits each node once
- Space: O(h) - recursion stack height (O(n) worst case skewed)

## Example Problems
- Maximum Depth of Binary Tree
- Validate Binary Search Tree
- Diameter of Binary Tree
- Symmetric Tree
- Path Sum