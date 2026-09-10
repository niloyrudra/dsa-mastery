---
id: "grid-dfs"
name: "Grid DFS Template"
pattern: "graph-dfs"
language: "TYPESCRIPT"
purpose: "Traverse connected components in 2D grid"
whenToReachForIt: "Grid problems with connected components (islands, flood fill, etc.)"
prerequisites: ["grid", "dfs", "recursion"]
mentalModel: "Explore in 4 directions from each unvisited land cell"
status: "PUBLISHED"
---

# Template: Grid DFS

## Code
```typescript
function gridDFS(grid: string[][]): number {
    if (!grid.length) return 0;
    
    const rows = grid.length;       // [1] Grid dimensions
    const cols = grid[0].length;
    const visited = new boolean[rows][cols]; // [2] Visited tracking (or modify grid)
    let count = 0;
    
    const directions = [           // [3] 4-directional movement
        [1, 0], [-1, 0], [0, 1], [0, -1]
    ];
    
    const dfs = (r: number, c: number): void => {
        // Boundary and validity checks
        if (r < 0 || r >= rows || c < 0 || c >= cols) return; // [4] Bounds
        if (visited[r][c] || grid[r][c] === '0') return;      // [5] Skip visited/water
        
        visited[r][c] = true;      // [6] Mark visited
        
        // Explore 4 neighbors
        for (const [dr, dc] of directions) { // [7]
            dfs(r + dr, c + dc);   // [8] Recurse
        }
    };
    
    for (let r = 0; r < rows; r++) { // [9] Iterate all cells
        for (let c = 0; c < cols; c++) {
            if (!visited[r][c] && grid[r][c] === '1') { // [10] Unvisited land
                count++;           // [11] New component found
                dfs(r, c);         // [12] Explore entire component
            }
        }
    }
    
    return count;                  // [13]
}
```

## Explanation
[1] Get grid dimensions
[2] Track visited cells (or modify grid in-place by setting '0')
[3] 4-directional moves: down, up, right, left
[4] Check row bounds
[5] Check column bounds and if cell is water/visited
[6] Mark current cell as visited
[7] Iterate through 4 directions
[8] Recursively explore neighbor
[9-10] Scan entire grid for unvisited land
[11] Found new island/component
[12] DFS marks all connected land as visited
[13] Return total count

## Invariant
After DFS(r,c) returns, all cells in the connected component containing (r,c) are marked visited.

## Common Modifications
- Return component size (area) instead of count
- Track component properties (perimeter, shape, etc.)
- 8-directional (add diagonals)
- In-place modification: grid[r][c] = '0' instead of visited array

## Common Mistakes
- Missing bounds check (index out of range)
- Not checking visited before recursing (infinite recursion)
- Wrong direction vectors
- Stack overflow on large grids (use BFS instead)

## Complexity
- Time: O(m*n) - each cell visited at most once
- Space: O(m*n) visited array + O(m*n) recursion stack worst case

## Example Problems
- Number of Islands
- Max Area of Island
- Surrounded Regions
- Flood Fill