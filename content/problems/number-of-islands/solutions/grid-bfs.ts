/**
 * Approach: Grid BFS (BEST - Iterative)
 * Time: O(m*n), Space: O(min(m,n)) for queue
 * 
 * Same as DFS but uses queue instead of recursion.
 * Better for avoiding stack overflow on large grids.
 * 
 * WHY IT WORKS: BFS explores layer by layer, marks all connected land.
 * 
 * INVARIANT: Queue contains frontier of current island being explored.
 */
function numIslandsBFS(grid: string[][]): number {
    if (!grid.length) return 0;
    
    const rows = grid.length;
    const cols = grid[0].length;
    let count = 0;
    const directions = [[1,0], [-1,0], [0,1], [0,-1]];
    
    for (let r = 0; r < rows; r++) {
        for (let c = 0; c < cols; c++) {
            if (grid[r][c] === '1') {
                count++;
                const queue: [number, number][] = [[r, c]];
                grid[r][c] = '0';
                
                while (queue.length > 0) {
                    const [curR, curC] = queue.shift()!;
                    
                    for (const [dr, dc] of directions) {
                        const nr = curR + dr;
                        const nc = curC + dc;
                        
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] === '1') {
                            grid[nr][nc] = '0';
                            queue.push([nr, nc]);
                        }
                    }
                }
            }
        }
    }
    
    return count;
}