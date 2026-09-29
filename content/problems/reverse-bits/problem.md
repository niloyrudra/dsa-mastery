---
id: "reverse-bits"
title: "Reverse Bits"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/reverse-bits/"
patterns: ["bit-manipulation-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["single-number", "power-of-two", "hamming-distance", "divide-two-integers", "fraction-to-recurring-decimal"]
status: "PUBLISHED"
orderIndex: 59
reasonForSelection: "Bit reversal with shifting and masking; divide and conquer"
---

# Problem Statement

Reverse bits of a given 32 bits unsigned integer.

## Constraints

- The input must be a **binary string** of length 32.

## Examples

**Example 1:**
```
Input: n = 00000010100101000001111010011100
Output: 964176192 (00111001011110000010100101000000)
Explanation: The input binary string represents the unsigned integer 43261596, so return 964176192 which is 00111001011110000010100101000000.
```

**Example 2:**
```
Input: n = 11111111111111111111111111111101
Output: 3221225471 (10111111111111111111111111111111)
Explanation: The input binary string represents the unsigned integer 4294967293, so return 3221225471 which is 10111111111111111111111111111111.
```

## Variations

### Variation 1: Single Number
**Name**: Single Number

**What Changed**: Find element appearing once.

**Original Approach Works**: XOR all elements.

**What Breaks**: Different problem.

**New Constraint**: Return single element.

**Algorithm Changes**: XOR all numbers.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Power of Two
**Name**: Power of Two

**What Changed**: Check if power of two.

**Original Approach Works**: n & (n-1) == 0.

**What Breaks**: Single bit check vs reversal.

**New Constraint**: Return true if power of two.

**Algorithm Changes**: n > 0 && (n & (n-1)) == 0.

**Complexity Changes**: Time O(1), Space O(1).