---
id: "linked-list-fundamentals"
name: "Linked List Fundamentals"
category: "Linked List"
definition: "Core linked list operations: traversal, insertion, deletion, reversal, cycle detection, finding middle, merging sorted lists."
when_to_use: "Any linked list manipulation; reversing lists; detecting cycles; finding middle/kth node; merging lists; adding numbers represented as lists."
why_it_works: "Linked lists allow O(1) insertion/deletion at known positions; two-pointer techniques (fast/slow) solve many problems efficiently."
intuition: "Manipulate next pointers to restructure the list. Use dummy nodes to simplify head operations. Fast/slow pointers for cycle detection and finding middle."
invariant: "List structure maintained through next pointers; no memory leaks (in languages with manual memory)."
strengths: "O(1) space for many operations (in-place); flexible restructuring; no random access needed."
weaknesses: "No random access (O(n) for index access); extra memory for pointers; cache-unfriendly; easy to create cycles or lose nodes."
recognition_signals:
  - "reverse linked list"
  - "merge two sorted lists"
  - "linked list cycle"
  - "middle of linked list"
  - "remove nth from end"
  - "add two numbers"
  - "copy list with random pointer"
  - "reorder list"
  - "LRU cache"
common_failure_modes:
  - "Losing reference to rest of list during reversal"
  - "Not handling empty list or single node"
  - "Forgetting to update head pointer"
  - "Infinite loops in cycle detection"
  - "Memory leaks in manual memory languages"
generalizations: "Can use dummy head for simpler code; fast/slow pointers generalize to kth from end; can convert to array for random access if needed."
time_complexity: "O(n) for most operations"
space_complexity: "O(1) for in-place, O(n) for recursive or copy"
time_reasoning: "Single or double pass through list."
space_reasoning: "In-place uses constant extra space; recursive uses O(n) call stack."
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