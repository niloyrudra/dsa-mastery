---
id: "divide-two-integers"
title: "Divide Two Integers"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/divide-two-integers/"
patterns: ["math-fundamentals", "bit-manipulation-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["reverse-bits", "reverse-integer", "power-of-two", "fraction-to-recurring-decimal"]
status: "PUBLISHED"
orderIndex: 58
reasonForSelection: "Bit manipulation for fast division; subtract multiples"
---

# Problem Statement

Given two integers `dividend` and `divisor`, divide two integers **without** using multiplication, division, and mod operator.

The integer division should truncate toward zero, which means losing its fractional part. For example, `8.345` would be truncated to `8`, and `-2.7335` would be truncated to `-2`.

Return the **quotient** after dividing `dividend` by `divisor`.

Note: Assume we are dealing with an environment that could only store integers within the **32-bit** signed integer range: `[-2³¹, 2³¹ - 1]`. For this problem, if the quotient is **strictly greater than 2³¹ - 1**, then return `2³¹ - 1`, and if the quotient is **strictly less than -2³¹**, then return `-2³¹`.

## Constraints

- -2³¹ ≤ dividend, divisor ≤ 2³¹ - 1
- divisor != 0

## Examples

**Example 1:**
```
Input: dividend = 10, divisor = 3
Output: 3
Explanation: 10/3 = 3.33333.. which is truncated to 3.
```

**Example 2:**
```
Input: dividend = 7, divisor = -3
Output: -2
Explanation: 7/-3 = -2.33333.. which is truncated to -2.
```

## Variations

### Variation 1: Reverse Bits
**Name**: Reverse Bits

**What Changed**: Reverse bits of 32-bit unsigned integer.

**Original Approach Works**: Bit manipulation with shifts.

**What Breaks**: Bit reversal vs division.

**New Constraint**: Return reversed bits.

**Algorithm Changes**: Shift and accumulate bits.

**Complexity Changes**: Time O(1), Space O(1).

### Variation 2: Fraction to Recurring Decimal
**Name**: Fraction to Recurring Decimal

**What Changed**: Convert fraction to decimal string with recurring part.

**Original Approach Works**: Long division with remainder tracking.

**What Breaks**: String output with parentheses for recurring.

**New Constraint**: Return decimal string.

**Algorithm Changes**: Hash map for remainder -> position, build string.

**Complexity Changes**: Time O(denominator), Space O(denominator).