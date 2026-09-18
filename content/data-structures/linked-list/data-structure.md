---
id: "linked-list"
name: "Linked List"
description: "Nodes connected by pointers with O(1) insertion/deletion at known position. No random access."
operations: '{"access": "O(n)", "search": "O(n)", "insert": "O(1)", "delete": "O(1)"}'
why_useful_here: "Best for frequent insertions/deletions. No shifting needed. Can grow/shrink dynamically."
tradeoffs: "No random access (O(n) for index). Poor cache locality. Extra memory for pointers."
alternatives: "Array, Dynamic Array, Skip List"
status: "PUBLISHED"
---
# Linked List

## Overview
Linked list stores elements as nodes connected by pointers. Each node contains data and reference to next node.

## Types
- **Singly Linked List**: Next pointer only
- **Doubly Linked List**: Next and previous pointers
- **Circular Linked List**: Last node points to first

## Operations
| Operation | Time Complexity |
|-----------|----------------|
| Access by index | O(n) |
| Search | O(n) |
| Insert at known position | O(1) |
| Delete at known position | O(1) |
| Insert at head | O(1) |
| Insert at tail | O(1) with tail pointer |

## Strengths
- **Dynamic size**: No capacity limits
- **O(1) insertion/deletion** at known position
- **No shifting** required
- **Memory efficient** for sparse data

## Weaknesses
- **No random access**: O(n) for index access
- **Poor cache locality**: Nodes scattered in memory
- **Extra memory**: Pointers per node
- **Sequential access only**

## When to Use
- Frequent insertions/deletions
- Unknown or changing size
- Implementing stacks, queues, hash tables
- LRU Cache (doubly linked list)