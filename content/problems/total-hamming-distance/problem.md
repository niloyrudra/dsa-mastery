---
id: "total-hamming-distance"
title: "Total Hamming Distance"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/total-hamming-distance/"
patterns: ["bit-manipulation-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["hamming-distance", "single-number", "reverse-bits", "power-of-two", "counting-bits"]
status: "PUBLISHED"
orderIndex: 60
reasonForSelection: "Count 1s at each bit position; pairs = count * (n - count)"
---

# Problem Statement

The **Hamming distance** between two integers is the number of positions at which the corresponding bits are different.

Given an integer array `nums`, return the sum of Hamming distances between all pairs of the integers in `nums`.

## Constraints

- 1 ≤ nums.length ≤ 10⁴
- 0 ≤ nums[i] ≤ 10⁹

## Examples

**Example 1:**
```
Input: nums = [4,14,2]
Output: 6
Explanation: In binary, 4 = 0100, 14 = 1110, 2 = 0010.
Pairs:
(4, 14): 2 bits different
(4, 2): 1 bit different
(14, 2): 1 bit different
Total = 6
```

## Variations

### Variation 1: Hamming Distance
**Name**: Hamming Distance

**What Changed**: Distance between two integers.

**Original Approach Works**: XOR + bit count.

**What Breaks**: Single pair vs all pairs.

**New Constraint**: Return distance between two integers.

**Algorithm Changes**: XOR and count bits.

**Complexity Changes**: Time O(1), Space O(1).

### Variation 2: Single Number
**Name**: Single Number

**What Changed**: Find element appearing once.

**Original Approach Works**: XOR all elements.

**What Breaks**: Different problem.

**New Constraint**: Return single element.

**Algorithm Changes**: XOR all numbers.

**Complexity Changes**: Time O(n), Space O(1).