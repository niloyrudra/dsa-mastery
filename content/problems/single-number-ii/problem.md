---
id: "single-number-ii"
title: "Single Number II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/single-number-ii/"
patterns: ["bit-manipulation-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["single-number", "single-number-iii", "find-the-duplicate-number", "missing-number"]
status: "PUBLISHED"
orderIndex: 60
reasonForSelection: "Bitwise state machine; count bits modulo 3"
---

# Problem Statement

Given an integer array `nums` where every element appears **three times** except for one, which appears **exactly once**. Find the single element.

You must implement a solution with a linear runtime complexity and use only constant extra space.

## Constraints

- 1 ≤ nums.length ≤ 3 × 10⁴
- -2³¹ ≤ nums[i] ≤ 2³¹ - 1
- Each element in `nums` appears exactly three times except for one element which appears once.

## Examples

**Example 1:**
```
Input: nums = [2,2,3,2]
Output: 3
```

**Example 2:**
```
Input: nums = [0,1,0,1,0,1,99]
Output: 99
```

## Variations

### Variation 1: Single Number
**Name**: Single Number

**What Changed**: Elements appear twice instead of three times.

**Original Approach Works**: XOR cancels pairs.

**What Breaks**: Three occurrences don't cancel with XOR.

**New Constraint**: Elements appear twice except one.

**Algorithm Changes**: XOR all elements.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Find the Duplicate Number
**Name**: Find the Duplicate Number

**What Changed**: Find duplicate in array of n+1 elements from 1..n.

**Original Approach Works**: Floyd's cycle detection on array indices.

**What Breaks**: Duplicate vs single element.

**New Constraint**: Return duplicate value.

**Algorithm Changes**: Fast/slow on nums[nums[i]].

**Complexity Changes**: Time O(n), Space O(1).