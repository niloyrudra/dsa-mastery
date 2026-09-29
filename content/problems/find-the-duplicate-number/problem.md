---
id: "find-the-duplicate-number"
title: "Find the Duplicate Number"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/find-the-duplicate-number/"
patterns: ["linked-list-fundamentals", "binary-search-on-answer", "bit-manipulation-fundamentals"]
algorithms: ["binary-search", "two-pointer"]
dataStructures: ["array", "linked-list"]
templates: ["binary-search-template-ts", "two-pointers-template-ts"]
prerequisites: ["array", "binary-search", "two-pointers"]
relatedProblems: ["linked-list-cycle", "contains-duplicate", "single-number", "missing-number"]
status: "PUBLISHED"
orderIndex: 11
reasonForSelection: "Floyd's cycle detection or binary search on answer space"
---

# Problem Statement

Given an array of integers `nums` containing `n + 1` integers where each integer is in the range `[1, n]` inclusive.

There is only **one repeated number** in `nums`, return this repeated number.

You must solve the problem **without** modifying the array `nums` and uses only constant extra space.

## Constraints

- 1 ≤ n ≤ 10⁵
- nums.length == n + 1
- 1 ≤ nums[i] ≤ n
- All the integers in `nums` appear only once except for precisely one integer which appears two or more times.

## Examples

**Example 1:**
```
Input: nums = [1,3,4,2,2]
Output: 2
```

**Example 2:**
```
Input: nums = [3,1,3,4,2]
Output: 3
```

**Example 3:**
```
Input: nums = [3,3,3,3,3]
Output: 3
```

## Variations

### Variation 1: Linked List Cycle
**Name**: Linked List Cycle

**What Changed**: Detect cycle in linked list using fast/slow pointers.

**Original Approach Works**: Floyd's algorithm on array as implicit linked list.

**What Breaks**: Explicit linked list vs array indices.

**New Constraint**: Return true if cycle exists.

**Algorithm Changes**: Fast/slow on ListNode.next.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Contains Duplicate
**Name**: Contains Duplicate

**What Changed**: Check if any duplicate exists (return boolean).

**Original Approach Works**: Hash set or Floyd's on array.

**What Breaks**: Just boolean, not the duplicate value.

**New Constraint**: Return true/false.

**Algorithm Changes**: Hash set O(n) space or sort O(n log n).

**Complexity Changes**: Time O(n), Space O(1) with Floyd's.