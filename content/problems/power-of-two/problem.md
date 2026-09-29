---
id: "power-of-two"
title: "Power of Two"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/power-of-two/"
patterns: ["bit-manipulation-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["single-number", "reverse-bits", "hamming-distance", "factorial-trailing-zeroes", "counting-bits"]
status: "PUBLISHED"
orderIndex: 58
reasonForSelection: "Bit trick: n & (n-1) == 0; single bit set"
---

# Problem Statement

Given an integer `n`, return `true` if it is a power of two. Otherwise, return `false`.

An integer `n` is a power of two, if there exists an integer `x` such that `n == 2ˣ`.

## Constraints

- -2³¹ ≤ n ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: n = 1
Output: true
Explanation: 2⁰ = 1
```

**Example 2:**
```
Input: n = 16
Output: true
Explanation: 2⁴ = 16
```

**Example 3:**
```
Input: n = 3
Output: false
```

## Variations

### Variation 1: Single Number
**Name**: Single Number

**What Changed**: Find element appearing once (others twice).

**Original Approach Works**: XOR all elements.

**What Breaks**: Different problem.

**New Constraint**: Return single element.

**Algorithm Changes**: XOR all numbers.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Counting Bits
**Name**: Counting Bits

**What Changed**: Count set bits for all numbers 0 to n.

**Original Approach Works**: DP: bits[i] = bits[i >> 1] + (i & 1).

**What Breaks**: Count all vs check single.

**New Constraint**: Return array of bit counts.

**Algorithm Changes**: DP with lowest set bit.

**Complexity Changes**: Time O(n), Space O(n).