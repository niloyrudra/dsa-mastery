---
id: "priority-queue-applications"
name: "Priority Queue Applications"
category: "Heap"
definition: "Advanced priority queue applications: task scheduling with cooldowns, median finding with dual heaps, designing systems with priority (Twitter, LFU cache)."
when_to_use: "Task scheduler with cooling period; median of data stream; design Twitter/news feed; LFU cache; event simulation; Huffman coding."
why_it_works: "Dual heaps (max-heap for lower half, min-heap for upper half) maintain median in O(log n). Priority queues model scheduling and priority-based processing."
intuition: "For median: balance two heaps so sizes differ by at most 1. For scheduling: use max-heap by frequency, queue for cooldown."
invariant: "Median heaps: size difference <= 1; all elements in max-heap <= all elements in min-heap."
strengths: "O(log n) for dynamic median; handles streaming data; flexible for complex scheduling constraints."
weaknesses: "More complex than single heap; dual heap rebalancing logic; custom comparators needed for objects."
recognition_signals:
  - "task scheduler"
  - "median finder"
  - "design twitter"
  - "lfu cache"
  - "huffman coding"
  - "event simulation"
  - "priority-based processing"
common_failure_modes:
  - "Incorrect heap rebalancing for median"
  - "Not handling even/odd count for median"
  - "Cooldown queue not processed correctly in task scheduler"
  - "Tie-breaking in priority queue"
  - "Memory growth in design Twitter"
generalizations: "Can extend to percentile finding; indexed priority queue for updates; multi-level priority queues."
time_complexity: "O(log n) per operation"
space_complexity: "O(n)"
time_reasoning: "Each heap operation is O(log n); dual heap adds constant factor."
space_reasoning: "Stores all elements across heaps/queues."
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