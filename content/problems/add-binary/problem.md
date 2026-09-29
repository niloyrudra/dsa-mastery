---
id: "add-binary"
title: "Add Binary"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/add-binary/"
patterns: ["bit-manipulation-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["add-two-numbers", "single-number", "reverse-bits"]
status: "PUBLISHED"
orderIndex: 61
reasonForSelection: "Binary string addition with carry; fundamental bitwise operation on strings"
---

# Problem Statement

Given two binary strings `a` and `b`, return their sum as a binary string.

## Constraints

- 1 ≤ a.length, b.length ≤ 10⁴
- a and b consist only of '0' or '1' characters
- Each string does not contain leading zeros except for the zero itself.

## Examples

**Example 1:**
```
Input: a = "11", b = "1"
Output: "100"
```

**Example 2:**
```
Input: a = "1010", b = "1011"
Output: "10101"
```

## Variations

### Variation 1: Add Two Numbers (Linked List)
**Name**: Add Two Numbers

**What Changed**: Numbers represented as linked lists instead of strings.

**Original Approach Works**: Same digit-by-digit addition with carry.

**What Breaks**: Linked list traversal vs string indexing.

**New Constraint**: Input as linked lists, output as linked list.

**Algorithm Changes**: Yes - use dummy head and traverse both lists.

**Complexity Changes**: Time O(max(m,n)), Space O(max(m,n)).