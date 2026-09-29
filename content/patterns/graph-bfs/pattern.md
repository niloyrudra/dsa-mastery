---
id: "graph-bfs"
name: "Graph Breadth-First Search"
category: "GRAPHS"
patterns: []
algorithms: ["bfs"]
dataStructures: ["graph", "queue"]
templates: ["graph-template-ts"]
problems: ["number-of-islands", "rotting-oranges", "walls-and-gates", "shortest-path", "minimum-height-trees"]
prerequisites: ["graph", "queue"]
relatedPatterns: ["graph-dfs", "graph-union-find"]
status: "PUBLISHED"
---

# Graph Breadth-First Search Pattern

## Definition

Exploring a graph level by level using a queue. Guarantees shortest path in unweighted graphs. Multi-source BFS starts with multiple nodes in the queue simultaneously, useful for distance from nearest source problems.

## When to Use

- **Shortest Path (Unweighted)**: BFS finds minimum edges from source
- **Multi-Source**: Distance from nearest of multiple sources (rotting oranges, walls and gates)
- **Level-by-Level**: All nodes at distance d processed before distance d+1
- **Grid as Graph**: Implicit graph on 2D grid (same as tree BFS but with grid neighbors)
- **Connected Components**: Alternative to DFS (iterative, no recursion limit)

## Why It Works

Queue ensures FIFO: nodes enqueued at distance d are processed before nodes enqueued at distance d+1. First time a node is dequeued is via shortest path from source. Multi-source initializes queue with all sources at distance 0.

## Recognition Signals

- **Structural**: "Shortest path in unweighted graph" → BFS
- **Structural**: "Minimum steps / time to reach" → BFS levels = steps
- **Structural**: "Multi-source / simultaneous spread" → Initialize queue with all sources
- **Structural**: "Grid distance from nearest X" → Multi-source BFS from all X
- **Structural**: "Rotting / spreading / infection" → Multi-source BFS with time tracking
- **Keywords**: "shortest path", "minimum steps", "time", "spread", "rotting", "multi-source", "level"

## Invariant

At start of each level iteration, queue contains exactly all nodes at current distance from source(s).

## Strengths

- Guarantees shortest path in unweighted graphs
- No recursion stack overflow
- Natural for time/step propagation problems
- Multi-source elegantly handles "nearest" queries

## Weaknesses

- O(V) queue space in worst case
- Doesn't work for weighted graphs (use Dijkstra)
- Level-by-level processing requires level-size capture
- Not suitable for deep path finding without visited

## Common Failure Modes

1. **Not capturing level size**: `queue.length` changes during processing
2. **Marking visited after dequeue**: Causes duplicate enqueues
3. **Not checking bounds in grid BFS**: Index errors
3. **Forgetting to mark source visited**: Re-enqueues source from neighbor
4. **Not handling empty graph/queue**: Edge case checks needed

## Generalizations

- **0-1 BFS**: Deque for edges with weight 0 or 1 (push front for 0, back for 1)
- **Bidirectional BFS**: Two frontiers meet in middle (when target known)
- **A* Search**: Priority queue with heuristic (weighted)
- **BFS on State Space**: State = (position, keys, etc.) for complex problems

## Complexity

- Time: O(V + E) - each vertex/edge processed once
- Space: O(V) queue + O(V) visited

## Example Problems

- **Number of Islands**: Grid BFS alternative to DFS
- **Rotting Oranges**: Multi-source BFS, time = levels
- **Walls and Gates**: Multi-source from gates, fill distances
- **Shortest Path in Binary Matrix**: Grid BFS with 8 directions
- **Minimum Height Trees**: Topological BFS (peel leaves layer by layer)
- **Word Ladder**: BFS on implicit graph (word transformations)