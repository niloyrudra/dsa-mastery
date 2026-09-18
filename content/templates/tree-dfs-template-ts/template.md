---
id: "tree-dfs-template-ts"
name: "Tree DFS Template"
language: "TYPESCRIPT"
purpose: "Templates for binary tree DFS traversals (preorder, inorder, postorder) and tree problems"
when_to_reach_for_it: "Tree traversal, path problems, LCA, diameter, max path sum, validation"
prerequisites: "Understanding of tree structure, recursion, base cases"
mental_model: "Visit node, recurse left, recurse right. Order depends on problem: pre/in/post-order."
code: |
// Tree Node Definition
class TreeNode {
    val: number;
    left: TreeNode | null;
    right: TreeNode | null;
    constructor(val?: number, left?: TreeNode | null, right?: TreeNode | null) {
        this.val = val === undefined ? 0 : val;
        this.left = left === undefined ? null : left;
        this.right = right === undefined ? null : right;
    }
}

// Preorder: Root -> Left -> Right
function preorderTraversal(root: TreeNode | null): number[] {
    const result: number[] = [];
    function dfs(node: TreeNode | null) {
        if (!node) return;
        result.push(node.val);
        dfs(node.left);
        dfs(node.right);
    }
    dfs(root);
    return result;
}

// Inorder: Left -> Root -> Right (Sorted for BST)
function inorderTraversal(root: TreeNode | null): number[] {
    const result: number[] = [];
    function dfs(node: TreeNode | null) {
        if (!node) return;
        dfs(node.left);
        result.push(node.val);
        dfs(node.right);
    }
    dfs(root);
    return result;
}

// Postorder: Left -> Right -> Root
function postorderTraversal(root: TreeNode | null): number[] {
    const result: number[] = [];
    function dfs(node: TreeNode | null) {
        if (!node) return;
        dfs(node.left);
        dfs(node.right);
        result.push(node.val);
    }
    dfs(root);
    return result;
}

// Maximum Depth
function maxDepth(root: TreeNode | null): number {
    if (!root) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}

// Validate BST
function isValidBST(root: TreeNode | null): boolean {
    function validate(node: TreeNode | null, min: number, max: number): boolean {
        if (!node) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }
    return validate(root, -Infinity, Infinity);
}

// Diameter of Binary Tree
function diameterOfBinaryTree(root: TreeNode | null): number {
    let diameter = 0;
    function depth(node: TreeNode | null): number {
        if (!node) return 0;
        const left = depth(node.left);
        const right = depth(node.right);
        diameter = Math.max(diameter, left + right);
        return 1 + Math.max(left, right);
    }
    depth(root);
    return diameter;
}

// Max Path Sum
function maxPathSum(root: TreeNode | null): number {
    let maxSum = -Infinity;
    function maxGain(node: TreeNode | null): number {
        if (!node) return 0;
        const leftGain = Math.max(maxGain(node.left), 0);
        const rightGain = Math.max(maxGain(node.right), 0);
        const currentPath = node.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, currentPath);
        return node.val + Math.max(leftGain, rightGain);
    }
    maxGain(root);
    return maxSum;
}

explanation: "DFS naturally follows tree structure. Pre/in/post order determined by when node is processed. Return values from children combine for bottom-up problems."
invariant: "Recursive call returns correct value for subtree. Base case: null returns identity (0, [], -Infinity, etc.)"
common_modifications: "Iterative with stack for deep trees. Return tuple for multiple values. Use global variable for diameter/max path."
common_mistakes: "Not handling null base case; integer overflow with -Infinity; wrong order for inorder (BST validation); not using long/min/max bounds for BST."
time_complexity: "O(n)"
space_complexity: "O(h) recursion stack, h = height"
time_reasoning: "Each node visited once."
space_reasoning: "Recursion stack depth equals tree height. O(n) worst case (skewed), O(log n) balanced."
pattern: "tree-dfs"
status: "PUBLISHED"
---
# Tree DFS Templates (TypeScript)

## TreeNode Definition
```typescript
class TreeNode {
    val: number;
    left: TreeNode | null;
    right: TreeNode | null;
    constructor(val?: number, left?: TreeNode | null, right?: TreeNode | null) {
        this.val = val ?? 0;
        this.left = left ?? null;
        this.right = right ?? null;
    }
}
```

## Traversals
```typescript
// Preorder: Root -> Left -> Right
function preorder(root: TreeNode | null): number[] {
    const res: number[] = [];
    const dfs = (node: TreeNode | null) => {
        if (!node) return;
        res.push(node.val);
        dfs(node.left);
        dfs(node.right);
    };
    dfs(root);
    return res;
}

// Inorder: Left -> Root -> Right
function inorder(root: TreeNode | null): number[] {
    const res: number[] = [];
    const dfs = (node: TreeNode | null) => {
        if (!node) return;
        dfs(node.left);
        res.push(node.val);
        dfs(node.right);
    };
    dfs(root);
    return res;
}

// Postorder: Left -> Right -> Root
function postorder(root: TreeNode | null): number[] {
    const res: number[] = [];
    const dfs = (node: TreeNode | null) => {
        if (!node) return;
        dfs(node.left);
        dfs(node.right);
        res.push(node.val);
    };
    dfs(root);
    return res;
}
```

## Max Depth
```typescript
function maxDepth(root: TreeNode | null): number {
    if (!root) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

## Validate BST
```typescript
function isValidBST(root: TreeNode | null): boolean {
    const validate = (node: TreeNode | null, min: number, max: number): boolean => {
        if (!node) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    };
    return validate(root, -Infinity, Infinity);
}
```

## Diameter
```typescript
function diameterOfBinaryTree(root: TreeNode | null): number {
    let diameter = 0;
    const depth = (node: TreeNode | null): number => {
        if (!node) return 0;
        const left = depth(node.left);
        const right = depth(node.right);
        diameter = Math.max(diameter, left + right);
        return 1 + Math.max(left, right);
    };
    depth(root);
    return diameter;
}
```

## Max Path Sum
```typescript
function maxPathSum(root: TreeNode | null): number {
    let maxSum = -Infinity;
    const maxGain = (node: TreeNode | null): number => {
        if (!node) return 0;
        const left = Math.max(maxGain(node.left), 0);
        const right = Math.max(maxGain(node.right), 0);
        maxSum = Math.max(maxSum, node.val + left + right);
        return node.val + Math.max(left, right);
    };
    maxGain(root);
    return maxSum;
}
```