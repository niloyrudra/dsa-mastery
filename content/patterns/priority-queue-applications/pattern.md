---
id: "priority-queue-applications"
name: "Priority Queue Applications"
category: "HEAP"
patterns: []
algorithms: []
dataStructures: ["heap", "queue"]
templates: ["heap-template-ts"]
problems: ["task-scheduler", "median-finder", "design-twitter", "last-stone-weight", "kth-largest-element-in-a-stream", "minimum-cost-to-connect-all-points", "network-delay-time"]
prerequisites: ["heap", "queue"]
relatedPatterns: ["heap-fundamentals", "greedy-fundamentals", "graph-bfs"]
status: "PUBLISHED"
---
# Priority Queue Applications Pattern

## Definition
Advanced priority queue applications: task scheduling with cooldowns, median finding with dual heaps, designing systems with priority (Twitter, LFU cache).

## When to Use
- Task scheduler with cooling period
- Median of data stream
- Design Twitter/news feed
- LFU cache
- Event simulation
- Huffman coding

## Why It Works
Dual heaps (max-heap for lower half, min-heap for upper half) maintain median in O(log n). Priority queues model scheduling and priority-based processing.

## Intuition
For median: balance two heaps so sizes differ by at most 1. For scheduling: use max-heap by frequency, queue for cooldown.

## Invariant
Median heaps: size difference <= 1; all elements in max-heap <= all elements in min-heap.

## Strengths
- O(log n) for dynamic median
- Handles streaming data
- Flexible for complex scheduling constraints

## Weaknesses
- More complex than single heap
- Dual heap rebalancing logic
- Custom comparators needed for objects

## Recognition Signals
- "task scheduler"
- "median finder"
- "design twitter"
- "lfu cache"
- "huffman coding"
- "event simulation"
- "priority-based processing"

## Common Failure Modes
- Incorrect heap rebalancing for median
- Not handling even/odd count for median
- Cooldown queue not processed correctly in task scheduler
- Tie-breaking in priority queue
- Memory growth in design Twitter

## Generalizations
Can extend to percentile finding; indexed priority queue for updates; multi-level priority queues.

## Complexity
- Time: O(log n) per operation
- Space: O(n)