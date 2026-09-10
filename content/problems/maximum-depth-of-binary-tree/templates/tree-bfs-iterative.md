---
id: "tree-bfs-iterative"
name: "Tree BFS Iterative Template"
pattern: "tree-bfs"
language: "TYPESCRIPT"
purpose: "Level-order traversal of binary tree"
whenToReachForIt: "Tree problems requiring level-by-level processing, shortest path in tree"
prerequisites: ["binary-tree", "queue"]
mentalModel: "Process tree level by level using a queue"
status: "PUBLISHED"
---

# Template: Tree BFS Iterative

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

function levelOrder(root: TreeNode | null): number[][] {
    if (!root) return [];           // [1] Empty tree
    const result: number[][] = [];
    const queue: TreeNode[] = [root]; // [2] Initialize with root
    
    while (queue.length > 0) {      // [3] While nodes to process
        const levelSize = queue.length; // [4] Nodes in current level
        const currentLevel: number[] = [];
        
        for (let i = 0; i < levelSize; i++) { // [5] Process entire level
            const node = queue.shift()!;      // [6] Dequeue
            currentLevel.push(node.val);      // [7] Process node
            
            if (node.left) queue.push(node.left);  // [8] Enqueue children
            if (node.right) queue.push(node.right);
        }
        
        result.push(currentLevel);  // [9] Add level to result
    }
    
    return result;                  // [10]
}

// Alternative: Track depth without storing values
function maxDepth(root: TreeNode | null): number {
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
```

## Explanation
[1] Handle empty tree
[2] Initialize queue with root node
[3] Continue while there are nodes to process
[4] Capture current level size before processing
[5] Process exactly levelSize nodes (all nodes at current depth)
[6] Dequeue front node
[7] Process node (collect value, check condition, etc.)
[8] Enqueue children for next level
[9] Add completed level to result
[10] Return all levels

## Invariant
At start of each while iteration, queue contains exactly all nodes at current depth.

## Common Modifications
- Return depth only (don't store values)
- Right side view (take last element of each level)
- Zigzag traversal (reverse alternate levels)
- Connect nodes at same level (populate next pointers)

## Common Mistakes
- Not capturing levelSize before for loop (queue grows during loop)
- Using queue.length directly in for condition (changes during iteration)
- Forgetting to check null before enqueueing
- Not handling empty tree

## Complexity
- Time: O(n) - visits each node once
- Space: O(w) - max width of tree (O(n) worst case complete tree)

## Example Problems
- Binary Tree Level Order Traversal
- Binary Tree Zigzag Level Order
- Binary Tree Right Side View
- Maximum Depth of Binary Tree
- Minimum Depth of Binary Tree