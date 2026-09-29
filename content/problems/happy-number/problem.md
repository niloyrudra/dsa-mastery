---
id: "happy-number"
title: "Happy Number"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/happy-number/"
patterns: ["math-fundamentals", "hash-map-lookup", "two-pointer"]
algorithms: []
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts", "two-pointers-template-ts"]
prerequisites: ["hash-table", "math-fundamentals"]
relatedProblems: ["power-of-two", "factorial-trailing-zeroes", "linked-list-cycle", "find-the-duplicate-number"]
status: "PUBLISHED"
orderIndex: 56
reasonForSelection: "Cycle detection with hash set or Floyd's; digit square sum"
---

# Problem Statement

Write an algorithm to determine if a number `n` is happy.

A **happy number** is a number defined by the following process:

- Starting with any positive integer, replace the number by the sum of the squares of its digits.
- Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1.
- Those numbers for which this process ends in 1 are happy.

Return `true` if `n` is a happy number, and `false` if not.

## Constraints

- 1 ≤ n ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: n = 19
Output: true
Explanation:
1² + 9² = 82
8² + 2² = 68
6² + 8² = 100
1² + 0² + 0² = 1
```

**Example 2:**
```
Input: n = 2
Output: false
```

## Variations

### Variation 1: Linked List Cycle
**Name**: Linked List Cycle

**What Changed**: Detect cycle in linked list.

**Original Approach Works**: Floyd's cycle detection (fast/slow pointers).

**What Breaks**: Explicit linked list vs number sequence.

**New Constraint**: Return true if cycle exists.

**Algorithm Changes**: Fast/slow on ListNode.next.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Find the Duplicate Number
**Name**: Find the Duplicate Number

**What Changed**: Find duplicate in array using cycle detection.

**Original Approach Works**: Floyd's on array as implicit linked list.

**What Breaks**: Array indices vs number sequence.

**New Constraint**: Return duplicate value.

**Algorithm Changes**: Fast/slow on nums[nums[i]].

**Complexity Changes**: Time O(n), Space O(1).