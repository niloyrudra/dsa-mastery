/**
 * Approach: Grid DFS (BEST - Simple)
 * Time: O(m*n), Space: O(m*n) for visited or modify grid in-place
 * 
 * Iterate through grid, when land found, increment count and DFS to mark all connected.
 * 
 * WHY IT WORKS: DFS explores entire connected component. Each land cell visited once.
 * 
 * INVARIANT: After DFS from (r,c), all connected '1's are marked as '0'.
 * 
 * INSIGHT TO NEXT: Can use Union-Find for dynamic connectivity queries.
 */
function numIslandsDFS(grid: string[][]): number {
    if (!grid.length) return 0;
    
    const rows = grid.length;
    const cols = grid[0].length;
    let count = 0;
    
    const dfs = (r: number, c: number): void => {
        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] === '0') {
            return;
        }
        
        grid[r][c] = '0';  // Mark visited
        dfs(r + 1, c);
        dfs(r - 1, c);
        dfs(r, c + 1);
        dfs(r, c - 1);
    };
    
    for (let r = 0; r < rows; r++) {
        for (let c = 0; c < cols; c++) {
            if (grid[r][c] === '1') {
                count++;
                dfs(r, c);
            }
        }
    }
    
    return count;
}