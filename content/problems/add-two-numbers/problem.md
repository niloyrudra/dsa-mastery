---
id: "add-two-numbers"
title: "Add Two Numbers"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/add-two-numbers/"
patterns: ["linked-list-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["linked-list"]
templates: []
prerequisites: ["linked-list", "math-fundamentals"]
relatedProblems: ["add-binary", "reverse-linked-list", "multiply-strings"]
status: "PUBLISHED"
orderIndex: 8
reasonForSelection: "Linked list addition with carry; fundamental list manipulation"
---

# Problem Statement

You are given two **non-empty** linked lists representing two non-negative integers. The digits are stored in **reverse order**, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

## Constraints

- The number of nodes in each linked list is in the range [1, 100].
- 0 ≤ Node.val ≤ 9
- It is guaranteed that the list represents a number that does not have leading zeros.

## Examples

**Example 1:**
```
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.
```

**Example 2:**
```
Input: l1 = [0], l2 = [0]
Output: [0]
```

**Example 3:**
```
Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]
```

## Variations

### Variation 1: Add Binary
**Name**: Add Binary

**What Changed**: Binary strings instead of linked lists.

**Original Approach Works**: Same digit-by-digit addition with carry.

**What Breaks**: String indexing vs linked list traversal.

**New Constraint**: Input as binary strings.

**Algorithm Changes**: Yes - iterate from end of strings.

**Complexity Changes**: Time O(max(m,n)), Space O(max(m,n)).

### Variation 2: Add Two Numbers II
**Name**: Add Two Numbers II

**What Changed**: Digits stored in forward order (most significant digit first).

**Original Approach Works**: Reverse lists first, then add, then reverse result.

**What Breaks**: Cannot process from least significant digit directly.

**New Constraint**: Cannot modify input lists (or must handle forward order).

**Algorithm Changes**: Use stacks or reverse lists.

**Complexity Changes**: Time O(m+n), Space O(m+n).