---
id: "reverse-nodes-in-k-group"
title: "Reverse Nodes in k-Group"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/reverse-nodes-in-k-group/"
patterns: ["linked-list-fundamentals", "two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["linked-list"]
templates: ["two-pointers-template-ts"]
prerequisites: ["linked-list", "two-pointers"]
relatedProblems: ["reverse-linked-list", "reorder-list", "swap-nodes-in-pairs", "reverse-linked-list-ii"]
status: "PUBLISHED"
orderIndex: 11
reasonForSelection: "Recursive group reversal; reverse k nodes then connect"
---

# Problem Statement

Given the `head` of a linked list, reverse the nodes of the list `k` at a time, and return the modified list.

`k` is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of `k` then left-out nodes, in the end, should remain as it is.

You may not alter the values in the list's nodes, only nodes themselves may be changed.

## Constraints

- The number of nodes in the list is `n`.
- 1 ≤ k ≤ n ≤ 5000
- 0 ≤ Node.val ≤ 1000

## Examples

**Example 1:**
```
Input: head = [1,2,3,4,5], k = 2
Output: [2,1,4,3,5]
```

**Example 2:**
```
Input: head = [1,2,3,4,5], k = 3
Output: [3,2,1,4,5]
```

## Variations

### Variation 1: Reverse Linked List
**Name**: Reverse Linked List

**What Changed**: Reverse entire list.

**Original Approach Works**: Iterative three-pointer.

**What Breaks**: Entire list vs groups.

**New Constraint**: Reverse all.

**Algorithm Changes**: Three pointers iterative.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Swap Nodes in Pairs
**Name**: Swap Nodes in Pairs

**What Changed**: Swap adjacent pairs (k=2).

**Original Approach Works**: Same group reversal with k=2.

**What Breaks**: Fixed k=2.

**New Constraint**: Swap every two nodes.

**Algorithm Changes**: Pair swap with dummy head.

**Complexity Changes**: Time O(n), Space O(1).