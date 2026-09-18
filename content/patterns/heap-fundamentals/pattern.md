---
id: "heap-fundamentals"
name: "Heap Fundamentals"
category: "Heap"
definition: "Priority queue operations: insert, extract min/max, peek, heapify; maintaining heap property for efficient priority-based access."
when_to_use: "Finding kth largest/smallest; top k elements; streaming data; merge k sorted lists; scheduling; Dijkstra's algorithm."
why_it_works: "Heap maintains partial order - parent is always >= (max-heap) or <= (min-heap) children. Root is always extremum. Operations are O(log n)."
intuition: "Complete binary tree stored in array. Parent at i, children at 2i+1, 2i+2. Bubble up on insert, bubble down on extract."
invariant: "For max-heap: parent >= children. For min-heap: parent <= children. Tree is complete (all levels filled except last, left-aligned)."
strengths: "O(1) peek, O(log n) insert/extract; no need for full sorting; efficient for streaming/top-k; array-based = cache friendly."
weaknesses: "No efficient search (O(n)); not stable; can't update arbitrary element efficiently without index map; no range queries."
recognition_signals:
  - "kth largest element"
  - "kth smallest element"
  - "top k frequent"
  - "merge k sorted lists"
  - "last stone weight"
  - "median finder"
  - "task scheduler"
  - "design twitter"
common_failure_modes:
  - "Using wrong heap type (min vs max)"
  - "Not handling empty heap"
  - "Incorrect heapify implementation"
  - "Forgetting to negate values for max-heap in min-heap languages"
  - "Not using custom comparator for objects"
generalizations: "Can use with custom comparators; indexed heap for update/delete; pairing heap for decrease-key; binomial/fibonacci heap for advanced operations."
time_complexity: "O(log n) insert/extract, O(1) peek, O(n) heapify"
space_complexity: "O(n)"
time_reasoning: "Tree height is log n; bubble up/down traverses height."
space_reasoning: "Array stores n elements."
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