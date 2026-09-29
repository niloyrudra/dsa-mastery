---
id: "fraction-to-recurring-decimal"
title: "Fraction to Recurring Decimal"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/fraction-to-recurring-decimal/"
patterns: ["math-fundamentals", "hash-map-lookup", "bit-manipulation-fundamentals"]
algorithms: []
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts"]
prerequisites: ["hash-table", "math-fundamentals"]
relatedProblems: ["divide-two-integers", "reverse-integer", "power-of-two", "factorial-trailing-zeroes"]
status: "PUBLISHED"
orderIndex: 59
reasonForSelection: "Long division with remainder tracking; hash map for cycle detection"
---

# Problem Statement

Given two integers representing the `numerator` and `denominator` of a fraction, return the fraction in string format.

If the fractional part is repeating, enclose the repeating part in parentheses.

If multiple answers are possible, return **any of them**.

It is **guaranteed** that the length of the answer string is less than 10⁴ for all the given inputs.

## Constraints

- -2³¹ ≤ numerator, denominator ≤ 2³¹ - 1
- denominator != 0

## Examples

**Example 1:**
```
Input: numerator = 1, denominator = 2
Output: "0.5"
```

**Example 2:**
```
Input: numerator = 2, denominator = 1
Output: "2"
```

**Example 3:**
```
Input: numerator = 4, denominator = 333
Output: "0.(012)"
Explanation: 4/333 = 0.012012012... The repeating part is "012".
```

## Variations

### Variation 1: Divide Two Integers
**Name**: Divide Two Integers

**What Changed**: Integer division without multiplication/division/mod.

**Original Approach Works**: Bit manipulation for fast division.

**What Breaks**: No decimal output, just integer quotient.

**New Constraint**: Return truncated integer quotient.

**Algorithm Changes**: Subtract multiples using bit shifts.

**Complexity Changes**: Time O(log n), Space O(1).

### Variation 2: Reverse Integer
**Name**: Reverse Integer

**What Changed**: Reverse digits of 32-bit integer.

**Original Approach Works**: Modulo and division for digit extraction.

**What Breaks**: Digit reversal vs decimal conversion.

**New Constraint**: Return reversed integer (0 if overflow).

**Algorithm Changes**: Extract digits with % 10, build result.

**Complexity Changes**: Time O(log n), Space O(1).