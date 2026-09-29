---
id: "heap-fundamentals"
name: "Heap Fundamentals"
category: "HEAP"
patterns: []
algorithms: ["dijkstra", "kruskal", "prim"]
dataStructures: ["heap", "array"]
templates: ["heap-template-ts"]
problems: ["kth-largest-element-in-an-array", "kth-largest-element-in-a-stream", "top-k-frequent-elements", "last-stone-weight", "median-finder", "merge-k-sorted-lists", "task-scheduler", "design-twitter", "minimum-cost-to-connect-all-points", "network-delay-time"]
prerequisites: ["heap", "array"]
relatedPatterns: ["greedy-fundamentals", "graph-bfs", "graph-union-find"]
status: "PUBLISHED"
---
# Heap Fundamentals Pattern

## Definition
Priority queue operations: insert, extract min/max, peek, heapify; maintaining heap property for efficient priority-based access.

## When to Use
- Finding kth largest/smallest
- Top k elements
- Streaming data
- Merge k sorted lists
- Scheduling
- Dijkstra's algorithm

## Why It Works
Heap maintains partial order - parent is always >= (max-heap) or <= (min-heap) children. Root is always extremum. Operations are O(log n).

## Intuition
Complete binary tree stored in array. Parent at i, children at 2i+1, 2i+2. Bubble up on insert, bubble down on extract.

## Invariant
For max-heap: parent >= children. For min-heap: parent <= children. Tree is complete (all levels filled except last, left-aligned).

## Strengths
- O(1) peek, O(log n) insert/extract
- No need for full sorting
- Efficient for streaming/top-k
- Array-based = cache friendly

## Weaknesses
- No efficient search (O(n))
- Not stable
- Can't update arbitrary element efficiently without index map
- No range queries

## Recognition Signals
- "kth largest element"
- "kth smallest element"
- "top k frequent"
- "merge k sorted lists"
- "last stone weight"
- "median finder"
- "task scheduler"
- "design twitter"

## Common Failure Modes
- Using wrong heap type (min vs max)
- Not handling empty heap
- Incorrect heapify implementation
- Forgetting to negate values for max-heap in min-heap languages
- Not using custom comparator for objects

## Generalizations
Can use with custom comparators; indexed heap for update/delete; pairing heap for decrease-key; binomial/fibonacci heap for advanced operations.

## Complexity
- Time: O(log n) insert/extract, O(1) peek, O(n) heapify
- Space: O(n)