---
id: "remove-nth-node-from-end-of-list"
title: "Remove Nth Node From End of List"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/remove-nth-node-from-end-of-list/"
patterns: ["linked-list-fundamentals", "two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["linked-list"]
templates: ["two-pointers-template-ts"]
prerequisites: ["linked-list", "two-pointers"]
relatedProblems: ["reverse-linked-list", "linked-list-cycle", "find-the-duplicate-number", "copy-list-with-random-pointer"]
status: "PUBLISHED"
orderIndex: 9
reasonForSelection: "Two pointers with n gap; dummy head for edge cases"
---

# Problem Statement

Given the `head` of a linked list, remove the `n`th node from the end of the list and return its head.

## Constraints

- The number of nodes in the list is `sz`.
- 1 ≤ sz ≤ 30
- 0 ≤ Node.val ≤ 100
- 1 ≤ n ≤ sz

## Examples

**Example 1:**
```
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]
```

**Example 2:**
```
Input: head = [1], n = 1
Output: []
```

**Example 3:**
```
Input: head = [1,2], n = 1
Output: [1]
```

## Variations

### Variation 1: Reverse Linked List
**Name**: Reverse Linked List

**What Changed**: Reverse entire list.

**Original Approach Works**: Iterative or recursive pointer reversal.

**What Breaks**: Different operation.

**New Constraint**: Return new head.

**Algorithm Changes**: Three pointers (prev, curr, next).

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Find the Duplicate Number
**Name**: Find the Duplicate Number

**What Changed**: Floyd's cycle detection on array.

**Original Approach Works**: Fast/slow pointers on array as implicit list.

**What Breaks**: Array vs linked list.

**New Constraint**: Return duplicate value.

**Algorithm Changes**: Fast/slow on nums[nums[i]].

**Complexity Changes**: Time O(n), Space O(1).