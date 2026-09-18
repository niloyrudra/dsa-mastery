---
id: "heap"
name: "Heap (Priority Queue)"
description: "Complete binary tree maintaining heap property. Parent >= children (max-heap) or <= children (min-heap)."
operations: '{"insert": "O(log n)", "extract": "O(log n)", "peek": "O(1)", "heapify": "O(n)", "size": "O(1)"}'
why_useful_here: "Best for priority-based processing. Extract min/max efficiently. No full sorting needed."
tradeoffs: "No efficient search (O(n)). Not stable. Can't update arbitrary element without index map. No range queries."
alternatives: "Balanced BST, Sorted Array, Skip List"
status: "PUBLISHED"
---
# Heap (Priority Queue)

## Overview
Heap is a complete binary tree where each parent satisfies heap property relative to children. Stored efficiently in array.

## Types
- **Max-Heap**: Parent >= children (root = maximum)
- **Min-Heap**: Parent <= children (root = minimum)

## Operations
| Operation | Time Complexity |
|-----------|----------------|
| Insert | O(log n) |
| Extract Min/Max | O(log n) |
| Peek | O(1) |
| Heapify | O(n) |
| Increase/Decrease Key | O(log n) |

## Array Representation
- Root at index 0 (or 1)
- Left child: 2*i + 1
- Right child: 2*i + 2
- Parent: (i-1) / 2

## Applications
- **Priority Queue**: Task scheduling, Dijkstra, Prim
- **Top K**: K largest/smallest elements
- **Median Finder**: Dual heap
- **Heap Sort**: In-place sorting
- **Streaming**: Running median, top-K

## Variants
- **Binary Heap**: Standard array-based
- **Fibonacci Heap**: O(1) amortized decrease-key
- **Pairing Heap**: Simpler, good practical performance
- **Indexed Heap**: Support update/delete by key

## When to Use
- Need efficient priority operations
- Top-K problems
- Graph algorithms (Dijkstra, Prim)
- Scheduling with priorities
- Streaming algorithms