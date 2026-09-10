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
 * Approach: BFS Level Order (BEST - Iterative)
 * Time: O(n), Space: O(w) where w is max width
 * 
 * Count levels by processing level by level.
 * 
 * WHY IT WORKS: BFS naturally processes tree level by level.
 * Number of levels = max depth.
 * 
 * INVARIANT: Queue contains all nodes at current level.
 */
function maxDepthBFS(root: TreeNode | null): number {
    if (!root) return 0;
    
    const queue: TreeNode[] = [root];
    let depth = 0;
    
    while (queue.length > 0) {
        const levelSize = queue.length;
        depth++;
        
        for (let i = 0; i < levelSize; i++) {
            const node = queue.shift()!;
            if (node.left) queue.push(node.left);
            if (node.right) queue.push(node.right);
        }
    }
    
    return depth;
}