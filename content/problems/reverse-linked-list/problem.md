---
id: "reverse-linked-list"
title: "Reverse Linked List"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/reverse-linked-list/"
patterns: ["linked-list-fundamentals", "two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["linked-list"]
templates: ["two-pointers-template-ts"]
prerequisites: ["linked-list", "two-pointers"]
relatedProblems: ["merge-two-sorted-lists", "reorder-list", "remove-nth-node-from-end-of-list", "palindrome-linked-list", "reverse-nodes-in-k-group"]
status: "PUBLISHED"
orderIndex: 7
reasonForSelection: "Iterative three-pointer reversal; O(1) space"
---

# Problem Statement

Given the `head` of a singly linked list, reverse the list, and return the reversed list.

## Constraints

- The number of nodes in the list is in the range [0, 5000].
- -5000 ≤ Node.val ≤ 5000

## Examples

**Example 1:**
```
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]
```

**Example 2:**
```
Input: head = [1,2]
Output: [2,1]
```

**Example 3:**
```
Input: head = []
Output: []
```

## Variations

### Variation 1: Reverse Nodes in K-Group
**Name**: Reverse Nodes in K-Group

**What Changed**: Reverse in groups of k.

**Original Approach Works**: Reverse each group, connect.

**What Breaks**: Group boundaries.

**New Constraint**: Reverse every k nodes.

**Algorithm Changes**: Reverse k nodes, recurse/iterate for next.

**Complexity Changes**: Time O(n), Space O(1) iterative.

### Variation 2: Palindrome Linked List
**Name**: Palindrome Linked List

**What Changed**: Check if palindrome.

**Original Approach Works**: Find middle, reverse second half, compare.

**What Breaks**: Comparison vs reversal.

**New Constraint**: Return true if palindrome.

**Algorithm Changes**: Same first steps, then compare.

**Complexity Changes**: Time O(n), Space O(1).