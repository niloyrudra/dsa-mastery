---
id: "reorder-list"
title: "Reorder List"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/reorder-list/"
patterns: ["linked-list-fundamentals", "two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["linked-list"]
templates: ["two-pointers-template-ts"]
prerequisites: ["linked-list", "two-pointers"]
relatedProblems: ["reverse-linked-list", "merge-two-sorted-lists", "remove-nth-node-from-end-of-list", "palindrome-linked-list"]
status: "PUBLISHED"
orderIndex: 10
reasonForSelection: "Find middle, reverse second half, merge alternately"
---

# Problem Statement

You are given the head of a singly linked-list. The list can be represented as:

`L0 → L1 → … → Ln - 1 → Ln`

Reorder the list to be on the following form:

`L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …`

You may not modify the values in the list's nodes. Only nodes themselves may be changed.

## Constraints

- The number of nodes in the list is in the range [1, 5 × 10⁴].
- 1 ≤ Node.val ≤ 1000

## Examples

**Example 1:**
```
Input: head = [1,2,3,4]
Output: [1,4,2,3]
```

**Example 2:**
```
Input: head = [1,2,3,4,5]
Output: [1,5,2,4,3]
```

## Variations

### Variation 1: Reverse Linked List
**Name**: Reverse Linked List

**What Changed**: Reverse entire list.

**Original Approach Works**: Three pointers iterative.

**What Breaks**: Different operation.

**New Constraint**: Return new head.

**Algorithm Changes**: Three pointers.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Palindrome Linked List
**Name**: Palindrome Linked List

**What Changed**: Check if list is palindrome.

**Original Approach Works**: Find middle, reverse second half, compare.

**What Breaks**: Comparison vs reorder.

**New Constraint**: Return true if palindrome.

**Algorithm Changes**: Same first steps, then compare instead of merge.

**Complexity Changes**: Time O(n), Space O(1).