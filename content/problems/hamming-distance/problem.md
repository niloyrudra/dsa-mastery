---
id: "hamming-distance"
title: "Hamming Distance"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/hamming-distance/"
patterns: ["bit-manipulation-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["single-number", "total-hamming-distance", "reverse-bits", "power-of-two"]
status: "PUBLISHED"
orderIndex: 60
reasonForSelection: "XOR + bit count; Brian Kernighan's algorithm for population count"
---

# Problem Statement

The **Hamming distance** between two integers is the number of positions at which the corresponding bits are different.

Given two integers `x` and `y`, return the Hamming distance between them.

## Constraints

- 0 ≤ x, y ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: x = 1, y = 4
Output: 2
Explanation:
1   (0 0 0 1)
4   (0 1 0 0)
       ↑   ↑
```

**Example 2:**
```
Input: x = 3, y = 1
Output: 1
```

## Variations

### Variation 1: Total Hamming Distance
**Name**: Total Hamming Distance

**What Changed**: Sum of Hamming distances between all pairs.

**Original Approach Works**: Bit counting per position across all numbers.

**What Breaks**: Sum over all pairs instead of single pair.

**New Constraint**: Return sum of Hamming distances for all pairs.

**Algorithm Changes**: For each bit position, count set bits, add count * (n - count).

**Complexity Changes**: Time O(32 * n), Space O(1).

### Variation 2: Single Number
**Name**: Single Number

**What Changed**: Find element appearing once (others twice).

**Original Approach Works**: XOR all elements.

**What Breaks**: Different problem.

**New Constraint**: Return single element.

**Algorithm Changes**: XOR all numbers.

**Complexity Changes**: Time O(n), Space O(1).