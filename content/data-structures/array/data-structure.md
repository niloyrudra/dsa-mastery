---
id: "array"
name: "Array"
description: "Contiguous memory storage with O(1) index access. Fixed or dynamic size."
operations: '{"access": "O(1)", "search": "O(n)", "insert": "O(n)", "delete": "O(n)", "append": "O(1) amortized"}'
why_useful_here: "Best for random access and cache-friendly iteration. Foundation for many algorithms."
tradeoffs: "Insertion/deletion at beginning/middle is O(n) due to shifting. Fixed size unless dynamic array."
alternatives: "Linked List, Dynamic Array, Hash Table"
status: "PUBLISHED"
---
# Array

## Overview
Array is a fundamental data structure storing elements in contiguous memory locations, enabling O(1) random access by index.

## Operations
| Operation | Time Complexity |
|-----------|----------------|
| Access by index | O(1) |
| Search | O(n) |
| Insert at end | O(1) amortized |
| Insert at beginning/middle | O(n) |
| Delete | O(n) |

## Strengths
- **Cache-friendly**: Contiguous memory = better CPU cache utilization
- **Random access**: Direct index access in O(1)
- **Memory efficient**: No pointer overhead
- **Simple**: Built-in to all languages

## Weaknesses
- **Fixed size** (static arrays) or **costly resize** (dynamic arrays)
- **O(n) insertion/deletion** in middle due to shifting
- **No efficient search** without sorting

## Variants
- **Static Array**: Fixed size, allocated at compile time
- **Dynamic Array**: Resizes automatically (ArrayList, vector, list in Python)
- **Circular Array**: Wraps around for queue implementation

## When to Use
- Need fast random access
- Known or bounded size
- Iterating sequentially (cache friendly)
- Building other data structures