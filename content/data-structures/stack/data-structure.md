---
id: "stack"
name: "Stack"
description: "LIFO (Last In, First Out) data structure with push/pop operations."
operations: '{"push": "O(1)", "pop": "O(1)", "peek": "O(1)", "size": "O(1)", "isEmpty": "O(1)"}'
why_useful_here: "Best for backtracking, expression evaluation, undo operations, DFS simulation."
tradeoffs: "No random access. Only top element accessible. LIFO constraint."
alternatives: "Array, Linked List, Queue"
status: "PUBLISHED"
---
# Stack

## Overview
Stack follows Last-In-First-Out (LIFO) principle. Elements added and removed from the same end (top).

## Operations
| Operation | Time Complexity |
|-----------|----------------|
| Push | O(1) |
| Pop | O(1) |
| Peek/Top | O(1) |
| Size | O(1) |
| isEmpty | O(1) |

## Implementation
- **Array-based**: Dynamic array, resize when full
- **Linked List-based**: Head as top, O(1) push/pop

## Applications
- **Function call stack**: Recursion, execution context
- **Expression evaluation**: RPN, infix to postfix
- **Backtracking**: Maze solving, N-Queens
- **Undo/Redo**: Text editors
- **DFS**: Explicit stack for iterative
- **Monotonic stack**: Next greater element
- **Matching**: Parentheses, brackets

## Variations
- **Min Stack**: Track minimum in O(1) with auxiliary stack
- **Max Stack**: Track maximum
- **Persistent Stack**: Immutable versions

## When to Use
- LIFO processing needed
- Backtracking algorithms
- Expression parsing
- Reversing sequences
- Memory management