---
id: "merge-k-sorted-lists"
title: "Merge K Sorted Lists"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/merge-k-sorted-lists/"
patterns: ["heap-fundamentals", "priority-queue-applications", "linked-list-fundamentals"]
algorithms: []
dataStructures: ["heap", "linked-list"]
templates: ["heap-template-ts", "two-pointers-template-ts"]
prerequisites: ["heap", "linked-list"]
relatedProblems: ["merge-two-sorted-lists", "kth-largest-element-in-a-stream", "top-k-frequent-elements", "last-stone-weight"]
status: "PUBLISHED"
orderIndex: 36
reasonForSelection: "Heap for multi-way merge; priority queue with custom comparator"
---

# Problem Statement

You are given an array of `k` linked-lists `lists`, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.

## Constraints

- k == lists.length
- 0 ≤ k ≤ 10⁴
- 0 ≤ lists[i].length ≤ 500
- -10⁴ ≤ lists[i][j] ≤ 10⁴
- `lists[i]` is sorted in **ascending order**.
- The sum of `lists[i].length` will not exceed 10⁴.

## Examples

**Example 1:**
```
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
```

**Example 2:**
```
Input: lists = []
Output: []
```

**Example 3:**
```
Input: lists = [[]]
Output: []
```

## Variations

### Variation 1: Merge Two Sorted Lists
**Name**: Merge Two Sorted Lists

**What Changed**: Only two lists to merge.

**Original Approach Works**: Simple two-pointer merge.

**What Breaks**: O(k) lists vs 2 lists.

**New Constraint**: Return merged list of two.

**Algorithm Changes**: Two pointers, dummy head.

**Complexity Changes**: Time O(n+m), Space O(1).

### Variation 2: Kth Largest Element in a Stream
**Name**: Kth Largest Element in a Stream

**What Changed**: Streaming kth largest instead of merge.

**Original Approach Works**: Min-heap of size k.

**What Breaks**: Different problem.

**New Constraint**: Return kth largest.

**Algorithm Changes**: Min-heap of size k.

**Complexity Changes**: Time O(log k) add, O(1) find, Space O(k).