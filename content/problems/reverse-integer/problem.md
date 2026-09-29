---
id: "reverse-integer"
title: "Reverse Integer"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/reverse-integer/"
patterns: ["bit-manipulation-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["reverse-bits", "power-of-two", "divide-two-integers", "fraction-to-recurring-decimal", "palindrome-number"]
status: "PUBLISHED"
orderIndex: 59
reasonForSelection: "Digit extraction with overflow check; 32-bit signed boundary"
---

# Problem Statement

Given a signed 32-bit integer `x`, return `x` with its digits reversed. If reversing `x` causes the value to go outside the signed 32-bit integer range `[-2³¹, 2³¹ - 1]`, then return `0`.

**Assume the environment does not allow you to store 64-bit integers (signed or unsigned).**

## Constraints

- -2³¹ ≤ x ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: x = 123
Output: 321
```

**Example 2:**
```
Input: x = -123
Output: -321
```

**Example 3:**
```
Input: x = 120
Output: 21
```

## Variations

### Variation 1: Reverse Bits
**Name**: Reverse Bits

**What Changed**: Reverse bits of 32-bit unsigned integer.

**Original Approach Works**: Bit shifting and masking.

**What Breaks**: Bits vs decimal digits.

**New Constraint**: Reverse bits.

**Algorithm Changes**: Shift and mask bits.

**Complexity Changes**: Time O(1), Space O(1).

### Variation 2: Palindrome Number
**Name**: Palindrome Number

**What Changed**: Check if integer is palindrome.

**Original Approach Works**: Reverse half or full and compare.

**What Breaks**: Check vs reverse.

**New Constraint**: Return true if palindrome.

**Algorithm Changes**: Reverse half, compare.

**Complexity Changes**: Time O(log n), Space O(1).