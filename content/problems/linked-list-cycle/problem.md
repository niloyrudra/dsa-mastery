---
id: "linked-list-cycle"
title: "Linked List Cycle"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/linked-list-cycle/"
patterns: ["linked-list-fundamentals", "two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["linked-list"]
templates: ["two-pointers-template-ts"]
prerequisites: ["linked-list", "two-pointers"]
relatedProblems: ["find-the-duplicate-number", "happy-number", "remove-nth-node-from-end-of-list", "reverse-linked-list"]
status: "PUBLISHED"
orderIndex: 7
reasonForSelection: "Floyd's tortoise and hare; fast/slow pointer cycle detection"
---

# Problem Statement

Given `head`, the head of a linked list, determine if the linked list has a cycle in it.

There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the `next` pointer. Internally, `pos` is used to denote the index of the node that tail's `next` pointer is connected to. **Note that `pos` is not passed as a parameter**.

Return `true` if there is a cycle in the linked list. Otherwise, return `false`.

## Constraints

- The number of nodes in the list is in the range [0, 10⁴].
- -10⁵ ≤ Node.val ≤ 10⁵
- `pos` is `-1` or a valid index in the linked list.

## Examples

**Example 1:**
```
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
```

**Example 2:**
```
Input: head = [1,2], pos = 0
Output: true
```

**Example 3:**
```
Input: head = [1], pos = -1
Output: false
```

## Variations

### Variation 1: Find the Duplicate Number
**Name**: Find the Duplicate Number

**What Changed**: Find duplicate in array using Floyd's algorithm.

**Original Approach Works**: Fast/slow on array as implicit linked list.

**What Breaks**: Array indices vs linked list nodes.

**New Constraint**: Return duplicate value.

**Algorithm Changes**: Fast/slow on nums[nums[i]].

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Happy Number
**Name**: Happy Number

**What Changed**: Detect cycle in number sequence.

**Original Approach Works**: Floyd's on digit square sum sequence.

**What Breaks**: Number sequence vs linked list.

**New Constraint**: Return true if happy (reaches 1).

**Algorithm Changes**: Fast/slow on sum of squares of digits.

**Complexity Changes**: Time O(log n) expected, Space O(1).