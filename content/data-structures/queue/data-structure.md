---
id: "queue"
name: "Queue"
description: "FIFO (First In, First Out) data structure with enqueue/dequeue operations."
operations: '{"enqueue": "O(1)", "dequeue": "O(1)", "peek": "O(1)", "size": "O(1)", "isEmpty": "O(1)"}'
why_useful_here: "Best for BFS, level-order traversal, scheduling, buffering, sliding window."
tradeoffs: "No random access. Only front/back accessible. FIFO constraint."
alternatives: "Array, Linked List, Deque, Priority Queue"
status: "PUBLISHED"
---
# Queue

## Overview
Queue follows First-In-First-Out (FIFO) principle. Elements added at rear, removed from front.

## Operations
| Operation | Time Complexity |
|-----------|----------------|
| Enqueue | O(1) |
| Dequeue | O(1) |
| Peek/Front | O(1) |
| Size | O(1) |
| isEmpty | O(1) |

## Types
- **Simple Queue**: Basic FIFO
- **Circular Queue**: Fixed array, wrap around
- **Deque**: Double-ended queue (both ends)
- **Priority Queue**: Elements by priority (heap-based)

## Applications
- **BFS**: Level-order graph/tree traversal
- **Scheduling**: CPU processes, print queue
- **Buffering**: Producer-consumer, streaming
- **Sliding Window**: Max/min in window
- **Simulation**: Event-driven systems

## Implementation
- **Array-based**: Circular buffer for O(1) both ends
- **Linked List-based**: Head=front, Tail=rear

## When to Use
- FIFO processing needed
- BFS traversal
- Task scheduling
- Rate limiting
- Inter-thread communication