---
id: "grid-bfs"
name: "Grid BFS Template"
pattern: "graph-bfs"
language: "TYPESCRIPT"
purpose: "Traverse connected components in 2D grid using queue"
whenToReachForIt: "Grid problems with connected components; avoids recursion stack overflow"
prerequisites: ["grid", "bfs", "queue"]
mentalModel: "Expand frontier layer by layer from each unvisited land cell"
status: "PUBLISHED"
---

# Template: Grid BFS

## Code
```typescript
function gridBFS(grid: string[][]): number {
    if (!grid.length) return 0;
    
    const rows = grid.length;       // [1] Grid dimensions
    const cols = grid[0].length;
    const visited = new boolean[rows][cols]; // [2] Visited tracking
    let count = 0;
    
    const directions = [           // [3] 4-directional movement
        [1, 0], [-1, 0], [0, 1], [0, -1]
    ];
    
    for (let r = 0; r < rows; r++) { // [4] Scan all cells
        for (let c = 0; c < cols; c++) {
            if (!visited[r][c] && grid[r][c] === '1') { // [5] Unvisited land
                count++;           // [6] New component
                
                const queue: [number, number][] = [[r, c]]; // [7] Initialize queue
                visited[r][c] = true;
                
                while (queue.length > 0) { // [8] Process frontier
                    const [curR, curC] = queue.shift()!;   // [9] Dequeue
                    
                    for (const [dr, dc] of directions) { // [10] 4 neighbors
                        const nr = curR + dr;
                        const nc = curC + dc;
                        
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) { // [11] Bounds
                            if (!visited[nr][nc] && grid[nr][nc] === '1') { // [12] Unvisited land
                                visited[nr][nc] = true; // [13] Mark before enqueue
                                queue.push([nr, nc]);   // [14] Enqueue
                            }
                        }
                    }
                }
            }
        }
    }
    
    return count;                  // [15]
}
```

## Explanation
[1] Get grid dimensions
[2] Track visited cells
[3] 4-directional moves
[4-5] Find unvisited land cell to start new component
[6] Increment component count
[7] Initialize queue with starting cell
[8] Process until frontier exhausted
[9] Dequeue current cell
[10] Check 4 neighbors
[11] Bounds check
[12] Skip visited or water
[13] Mark visited BEFORE enqueueing (prevents duplicates)
[14] Add to frontier
[15] Return total components

## Invariant
Queue contains exactly the frontier of the current component being explored.
All visited cells in component are marked.

## Common Modifications
- Return component area (increment counter in while loop)
- Multi-source BFS (initialize queue with multiple starting points)
- Track distance from source (level-order with distance)
- 8-directional (add diagonal directions)

## Common Mistakes
- Marking visited after dequeue (causes duplicates in queue)
- Not checking bounds before accessing grid
- Forgetting to mark start cell visited
- Using recursion (use queue for BFS)

## Complexity
- Time: O(m*n) - each cell enqueued/dequeued at most once
- Space: O(min(m,n)) queue size + O(m*n) visited array

## Example Problems
- Number of Islands
- Max Area of Island
- Rotting Oranges (multi-source)
- Walls and Gates (multi-source)
- Shortest Path in Binary Matrix