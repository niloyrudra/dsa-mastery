---
id: "union-find"
name: "Union-Find (Disjoint Set Union) Template"
pattern: "graph-union-find"
language: "TYPESCRIPT"
purpose: "Dynamic connectivity and connected components"
whenToReachForIt: "Dynamic connectivity, connected components, cycle detection, MST (Kruskal)"
prerequisites: ["arrays", "graphs"]
mentalModel: "Each element points to parent; root represents component; path compression flattens tree"
status: "PUBLISHED"
---

# Template: Union-Find

## Code
```typescript
class UnionFind {
    private parent: number[];  // [1] Parent pointers
    private rank: number[];    // [2] Rank for union by rank
    private count: number;     // [3] Number of components
    
    constructor(n: number) {
        this.parent = new Array(n);
        this.rank = new Array(n).fill(0);
        this.count = n;
        
        for (let i = 0; i < n; i++) {
            this.parent[i] = i; // [4] Each node is its own parent initially
        }
    }
    
    // Find with path compression
    find(x: number): number {
        if (this.parent[x] !== x) {           // [5] Not root
            this.parent[x] = this.find(this.parent[x]); // [6] Path compression
        }
        return this.parent[x];                // [7] Return root
    }
    
    // Union by rank
    union(x: number, y: number): boolean {
        const rootX = this.find(x);           // [8] Find roots
        const rootY = this.find(y);
        
        if (rootX === rootY) return false;    // [9] Already connected
        
        // Union by rank: attach smaller to larger
        if (this.rank[rootX] < this.rank[rootY]) { // [10]
            this.parent[rootX] = rootY;
        } else if (this.rank[rootX] > this.rank[rootY]) {
            this.parent[rootY] = rootX;
        } else {
            this.parent[rootY] = rootX;       // [11] Equal rank, increment
            this.rank[rootX]++;
        }
        
        this.count--;                         // [12] Decrease component count
        return true;
    }
    
    connected(x: number, y: number): boolean {
        return this.find(x) === this.find(y); // [13] Same component?
    }
    
    getCount(): number {
        return this.count;                    // [14] Current component count
    }
}

// Grid-specific: 2D to 1D index mapping
function numIslandsUnionFind(grid: string[][]): number {
    if (!grid.length) return 0;
    
    const rows = grid.length;
    const cols = grid[0].length;
    const uf = new UnionFind(rows * cols);
    let landCount = 0;
    
    const directions = [[1,0], [-1,0], [0,1], [0,-1]];
    
    for (let r = 0; r < rows; r++) {
        for (let c = 0; c < cols; c++) {
            if (grid[r][c] === '1') {
                landCount++;
                const idx = r * cols + c;     // [15] 2D to 1D
                
                for (const [dr, dc] of directions) {
                    const nr = r + dr;
                    const nc = c + dc;
                    
                    if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] === '1') {
                        const nIdx = nr * cols + nc;
                        uf.union(idx, nIdx);  // [16] Union adjacent land
                    }
                }
            }
        }
    }
    
    return uf.getCount(); // [17] Components = islands
}
```

## Explanation
[1] Parent array: parent[i] = parent of i, root has parent[i] = i
[2] Rank array: approximate tree height for union by rank
[3] Count of connected components
[4] Initialize: each element is its own component
[5-6] Path compression: recursively find root, update parent to point directly to root
[7] Return root representative
[8] Find roots of both elements
[9] If same root, already in same component
[10-11] Union by rank: attach shorter tree to taller tree
[12] Decrease component count on successful union
[13] Check if two elements in same component
[14] Get current number of components
[15] Map 2D (r,c) to 1D index: r * cols + c
[16] Union adjacent land cells
[17] Number of components = number of islands

## Invariant
- Each component forms a tree rooted at its representative
- Path compression ensures amortized O(α(n)) find time
- Union by rank keeps tree height O(log n)

## Common Modifications
- Size tracking: maintain component sizes for union by size
- Weighted union: attach smaller component to larger
- Persistent/rollback union-find for offline queries
- 2D grid indexing: index = r * cols + c

## Common Mistakes
- Forgetting path compression (degrades to O(n))
- Not checking if already connected before union
- Wrong 2D to 1D index calculation
- Not initializing parent array correctly

## Complexity
- Time: O(α(n)) amortized per operation (α = inverse Ackermann, < 5)
- Space: O(n) for parent, rank arrays

## Example Problems
- Number of Islands
- Number of Connected Components
- Graph Valid Tree
- Redundant Connection
- Accounts Merge