---
id: "merge-two-sorted-lists"
title: "Merge Two Sorted Lists"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/merge-two-sorted-lists/"
patterns: ["linked-list-fundamentals"]
algorithms: []
dataStructures: ["linked-list"]
templates: ["two-pointers-template-ts"]
prerequisites: ["linked-list"]
relatedProblems: ["merge-k-sorted-lists", "add-two-numbers", "reverse-linked-list", "copy-list-with-random-pointer"]
status: "PUBLISHED"
orderIndex: 8
reasonForSelection: "Dummy head + two pointers; classic merge pattern"
---

# Problem Statement

You are given the heads of two sorted linked lists `list1` and `list2`.

Merge the two lists into one **sorted** list. The list should be made by splicing together the nodes of the first two lists.

Return the head of the merged linked list.

## Constraints

- The number of nodes in both lists is in the range [0, 50].
- -100 ≤ Node.val ≤ 100
- Both `list1` and `list2` are sorted in **non-decreasing order**.

## Examples

**Example 1:**
```
Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]
```

**Example 2:**
```
Input: list1 = [], list2 = []
Output: []
```

**Example 3:**
```
Input: list1 = [], list2 = [0]
Output: [0]
```

## Variations

### Variation 1: Merge K Sorted Lists
**Name**: Merge K Sorted Lists

**What Changed**: K lists instead of 2.

**Original Approach Works**: Two-pointer merge repeatedly.

**What Breaks**: O(k) lists, need efficient merge.

**New Constraint**: Merge k lists.

**Algorithm Changes**: Min-heap of list heads, extract min, push next.

**Complexity Changes**: Time O(n log k), Space O(k).

### Variation 2: Add Two Numbers
**Name**: Add Two Numbers

**What Changed**: Add digits with carry instead of merge.

**Original Approach Works**: Two pointers traverse both lists.

**What Breaks**: Addition with carry vs merge.

**New Constraint**: Return sum as linked list.

**Algorithm Changes**: Track carry, create new nodes.

**Complexity Changes**: Time O(max(m,n)), Space O(max(m,n)).