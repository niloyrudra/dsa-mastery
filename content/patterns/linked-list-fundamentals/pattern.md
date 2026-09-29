---
id: "linked-list-fundamentals"
name: "Linked List Fundamentals"
category: "LINKED_LIST"
patterns: []
algorithms: []
dataStructures: ["linked-list"]
templates: []
problems: ["reverse-linked-list", "merge-two-sorted-lists", "linked-list-cycle", "remove-nth-node-from-end-of-list", "add-two-numbers", "copy-list-with-random-pointer", "reorder-list", "lru-cache", "reverse-nodes-in-k-group", "find-the-duplicate-number"]
prerequisites: ["linked-list"]
relatedPatterns: ["tree-dfs", "hash-map-lookup"]
status: "PUBLISHED"
---
# Linked List Fundamentals Pattern

## Definition
Core linked list operations: traversal, insertion, deletion, reversal, cycle detection, finding middle, merging sorted lists.

## When to Use
- Any linked list manipulation
- Reversing lists
- Detecting cycles
- Finding middle/kth node
- Merging lists
- Adding numbers represented as lists

## Why It Works
Linked lists allow O(1) insertion/deletion at known positions; two-pointer techniques (fast/slow) solve many problems efficiently.

## Intuition
Manipulate next pointers to restructure the list. Use dummy nodes to simplify head operations. Fast/slow pointers for cycle detection and finding middle.

## Invariant
List structure maintained through next pointers; no memory leaks (in languages with manual memory).

## Strengths
- O(1) space for many operations (in-place)
- Flexible restructuring
- No random access needed

## Weaknesses
- No random access (O(n) for index access)
- Extra memory for pointers
- Cache-unfriendly
- Easy to create cycles or lose nodes

## Recognition Signals
- "reverse linked list"
- "merge two sorted lists"
- "linked list cycle"
- "middle of linked list"
- "remove nth from end"
- "add two numbers"
- "copy list with random pointer"
- "reorder list"
- "LRU cache"

## Common Failure Modes
- Losing reference to rest of list during reversal
- Not handling empty list or single node
- Forgetting to update head pointer
- Infinite loops in cycle detection
- Memory leaks in manual memory languages

## Generalizations
Can use dummy head for simpler code; fast/slow pointers generalize to kth from end; can convert to array for random access if needed.

## Complexity
- Time: O(n) for most operations
- Space: O(1) for in-place, O(n) for recursive or copy