/**
 * Definition for a binary tree node.
 */
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

/**
 * Approach: DFS Recursive (BEST - Simple)
 * Time: O(n), Space: O(h) where h is height
 * 
 * Depth = 1 + max(depth of left, depth of right)
 * Base case: null node has depth 0.
 * 
 * WHY IT WORKS: Recursively computes depth of each subtree.
 * Post-order traversal: compute children first, then combine.
 * 
 * INVARIANT: Function returns correct depth for any subtree root.
 */
function maxDepthDFS(root: TreeNode | null): number {
    if (!root) return 0;
    
    const leftDepth = maxDepthDFS(root.left);
    const rightDepth = maxDepthDFS(root.right);
    
    return 1 + Math.max(leftDepth, rightDepth);
}