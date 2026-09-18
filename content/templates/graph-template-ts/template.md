---
id: "graph-template-ts"
name: "Graph Template"
language: "TYPESCRIPT"
purpose: "Templates for graph representation, DFS, BFS, Dijkstra, Union-Find"
when_to_reach_for_it: "Graph problems: islands, shortest path, MST, topological sort, cycle detection"
prerequisites: "Understanding of adjacency list/matrix, queue/stack, priority queue"
mental_model: "Graph = vertices + edges. Traverse with DFS (stack) or BFS (queue). Dijkstra for weighted shortest path."
code: |
// Graph Representation - Adjacency List
type Graph = Map<number, number[][]>; // node -> [[neighbor, weight], ...]

function buildGraph(n: number, edges: number[][], directed = false): Graph {
    const graph = new Map<number, number[][]>();
    for (let i = 0; i < n; i++) graph.set(i, []);
    for (const [u, v, w] of edges) {
        graph.get(u)!.push([v, w ?? 1]);
        if (!directed) graph.get(v)!.push([u, w ?? 1]);
    }
    return graph;
}

// DFS - Recursive
function dfs(graph: Graph, start: number, visited: Set<number> = new Set()): void {
    visited.add(start);
    for (const [neighbor] of graph.get(start) || []) {
        if (!visited.has(neighbor)) dfs(graph, neighbor, visited);
    }
}

// BFS - Shortest Path (Unweighted)
function bfs(graph: Graph, start: number): number[] {
    const dist = new Map<number, number>();
    const queue: number[] = [start];
    dist.set(start, 0);

    while (queue.length) {
        const u = queue.shift()!;
        for (const [v] of graph.get(u) || []) {
            if (!dist.has(v)) {
                dist.set(v, dist.get(u)! + 1);
                queue.push(v);
            }
        }
    }
    return Array.from(dist.values());
}

// Number of Islands (Grid DFS)
function numIslands(grid: string[][]): number {
    if (!grid.length) return 0;
    const m = grid.length, n = grid[0].length;
    let count = 0;

    function dfs(r: number, c: number) {
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] !== '1') return;
        grid[r][c] = '0';
        dfs(r + 1, c); dfs(r - 1, c); dfs(r, c + 1); dfs(r, c - 1);
    }

    for (let r = 0; r < m; r++) {
        for (let c = 0; c < n; c++) {
            if (grid[r][c] === '1') {
                count++;
                dfs(r, c);
            }
        }
    }
    return count;
}

// Dijkstra - Single Source Shortest Path
function dijkstra(graph: Graph, start: number): Map<number, number> {
    const dist = new Map<number, number>();
    const pq: [number, number][] = [[0, start]]; // [dist, node]
    dist.set(start, 0);

    while (pq.length) {
        pq.sort((a, b) => a[0] - b[0]);
        const [d, u] = pq.shift()!;
        if (d > (dist.get(u) ?? Infinity)) continue;

        for (const [v, w] of graph.get(u) || []) {
            const nd = d + w;
            if (nd < (dist.get(v) ?? Infinity)) {
                dist.set(v, nd);
                pq.push([nd, v]);
            }
        }
    }
    return dist;
}

// Union-Find
class UnionFind {
    parent: number[];
    rank: number[];
    constructor(n: number) {
        this.parent = Array.from({ length: n }, (_, i) => i);
        this.rank = new Array(n).fill(0);
    }
    find(x: number): number {
        if (this.parent[x] !== x) this.parent[x] = this.find(this.parent[x]);
        return this.parent[x];
    }
    union(x: number, y: number): boolean {
        const px = this.find(x), py = this.find(y);
        if (px === py) return false;
        if (this.rank[px] < this.rank[py]) this.parent[px] = py;
        else if (this.rank[px] > this.rank[py]) this.parent[py] = px;
        else { this.parent[py] = px; this.rank[px]++; }
        return true;
    }
}

explanation: "Adjacency list for sparse graphs. DFS for traversal/cycles. BFS for shortest unweighted paths. Dijkstra for weighted. Union-Find for dynamic connectivity."
invariant: "BFS: queue processes level by level. Dijkstra: settled nodes have final distance. Union-Find: path compression flattens tree."
common_modifications: "Matrix instead of grid for islands. Topological sort with indegree. Prim's for MST. A* for heuristic search."
common_mistakes: "Not checking visited in BFS/DFS; using recursion for deep graphs; Dijkstra with negative weights; forgetting path compression in Union-Find."
time_complexity: "DFS/BFS: O(V+E), Dijkstra: O((V+E)log V), Union-Find: O(α(V))"
space_complexity: "O(V+E) for graph, O(V) for visited/dist/queue"
pattern: "graph-dfs"
status: "PUBLISHED"
---
# Graph Templates (TypeScript)

## Graph Representation
```typescript
type Graph = Map<number, number[][]>; // node -> [[neighbor, weight], ...]

function buildGraph(n: number, edges: number[][], directed = false): Graph {
    const graph = new Map<number, number[][]>();
    for (let i = 0; i < n; i++) graph.set(i, []);
    for (const [u, v, w] of edges) {
        graph.get(u)!.push([v, w ?? 1]);
        if (!directed) graph.get(v)!.push([u, w ?? 1]);
    }
    return graph;
}
```

## DFS
```typescript
function dfs(graph: Graph, start: number, visited = new Set<number>()): void {
    visited.add(start);
    for (const [neighbor] of graph.get(start) || []) {
        if (!visited.has(neighbor)) dfs(graph, neighbor, visited);
    }
}
```

## BFS - Shortest Path (Unweighted)
```typescript
function bfs(graph: Graph, start: number): number[] {
    const dist = new Map<number, number>();
    const queue = [start];
    dist.set(start, 0);
    while (queue.length) {
        const u = queue.shift()!;
        for (const [v] of graph.get(u) || []) {
            if (!dist.has(v)) {
                dist.set(v, dist.get(u)! + 1);
                queue.push(v);
            }
        }
    }
    return Array.from(dist.values());
}
```

## Number of Islands (Grid)
```typescript
function numIslands(grid: string[][]): number {
    if (!grid.length) return 0;
    const m = grid.length, n = grid[0].length;
    let count = 0;
    const dfs = (r: number, c: number) => {
        if (r < 0 || r >= m || c < 0 || c >= n || grid[r][c] !== '1') return;
        grid[r][c] = '0';
        dfs(r+1, c); dfs(r-1, c); dfs(r, c+1); dfs(r, c-1);
    };
    for (let r = 0; r < m; r++) for (let c = 0; c < n; c++)
        if (grid[r][c] === '1') { count++; dfs(r, c); }
    return count;
}
```

## Dijkstra
```typescript
function dijkstra(graph: Graph, start: number): Map<number, number> {
    const dist = new Map<number, number>();
    const pq: [number, number][] = [[0, start]];
    dist.set(start, 0);
    while (pq.length) {
        pq.sort((a, b) => a[0] - b[0]);
        const [d, u] = pq.shift()!;
        if (d > (dist.get(u) ?? Infinity)) continue;
        for (const [v, w] of graph.get(u) || []) {
            const nd = d + w;
            if (nd < (dist.get(v) ?? Infinity)) {
                dist.set(v, nd);
                pq.push([nd, v]);
            }
        }
    }
    return dist;
}
```

## Union-Find
```typescript
class UnionFind {
    parent: number[]; rank: number[];
    constructor(n: number) { this.parent = Array.from({length:n}, (_,i)=>i); this.rank = new Array(n).fill(0); }
    find(x: number): number { if (this.parent[x] !== x) this.parent[x] = this.find(this.parent[x]); return this.parent[x]; }
    union(x: number, y: number): boolean { const px=this.find(x), py=this.find(y); if(px===py) return false; if(this.rank[px]<this.rank[py]) this.parent[px]=py; else if(this.rank[px]>this.rank[py]) this.parent[py]=px; else { this.parent[py]=px; this.rank[px]++; } return true; }
}
```