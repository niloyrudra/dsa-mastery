---
id: "single-number"
title: "Single Number"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/single-number/"
patterns: ["bit-manipulation-fundamentals", "hash-map-lookup", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["single-number-ii", "single-number-iii", "hamming-distance", "reverse-bits", "power-of-two", "find-the-duplicate-number"]
status: "PUBLISHED"
orderIndex: 60
reasonForSelection: "XOR cancels pairs; fundamental bitwise trick"
---

# Problem Statement

Given a **non-empty** array of integers `nums`, every element appears **twice** except for one. Find that single one.

You must implement a solution with a linear runtime complexity and use only constant extra space.

## Constraints

- 1 ≤ nums.length ≤ 3 × 10⁴
- -3 × 10⁴ ≤ nums[i] ≤ 3 × 10⁴
- Each element in the array appears twice except for one element which appears only once.

## Examples

**Example 1:**
```
Input: nums = [2,2,1]
Output: 1
```

**Example 2:**
```
Input: nums = [4,1,2,1,2]
Output: 4
```

**Example 3:**
```
Input: nums = [1]
Output: 1
```

## Variations

### Variation 1: Single Number II
**Name**: Single Number II

**What Changed**: Every element appears three times except one.

**Original Approach Works**: XOR doesn't work for 3x.

**What Breaks**: XOR cancels pairs, not triplets.

**New Constraint**: Elements appear 3 times except one.

**Algorithm Changes**: Bit counting (sum bits % 3) or finite state machine.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Find the Duplicate Number
**Name**: Find the Duplicate Number

**What Changed**: Array as linked list, find duplicate via cycle.

**Original Approach Works**: Floyd's algorithm on array indices.

**What Breaks**: Duplicate detection vs single element.

**New Constraint**: Return duplicate value.

**Algorithm Changes**: Fast/slow on nums[nums[i]].

**Complexity Changes**: Time O(n), Space O(1).