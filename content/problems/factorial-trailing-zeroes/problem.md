---
id: "factorial-trailing-zeroes"
title: "Factorial Trailing Zeroes"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/factorial-trailing-zeroes/"
patterns: ["math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "math-fundamentals"]
relatedProblems: ["power-of-two", "divide-two-integers", "happy-number", "reverse-integer"]
status: "PUBLISHED"
orderIndex: 57
reasonForSelection: "Count factors of 5; mathematical insight over brute force"
---

# Problem Statement

Given an integer `n`, return the number of trailing zeroes in `n!`.

Note that `n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1`.

## Constraints

- 0 ≤ n ≤ 10⁴

## Examples

**Example 1:**
```
Input: n = 3
Output: 0
Explanation: 3! = 6, no trailing zero.
```

**Example 2:**
```
Input: n = 5
Output: 1
Explanation: 5! = 120, one trailing zero.
```

**Example 3:**
```
Input: n = 0
Output: 0
Explanation: 0! = 1, no trailing zero.
```

## Variations

### Variation 1: Preimage Size of Factorial Zeroes Function
**Name**: Preimage Size of Factorial Zeroes Function

**What Changed**: Find how many n have exactly k trailing zeroes.

**Original Approach Works**: Same factor counting, but binary search on n.

**What Breaks**: Inverse problem - find n given zero count.

**New Constraint**: Return count of n with exactly k trailing zeroes.

**Algorithm Changes**: Binary search on n, check trailing zeroes.

**Complexity Changes**: Time O(log n * log n), Space O(1).

### Variation 2: Power of Two
**Name**: Power of Two

**What Changed**: Check if n is power of two.

**Original Approach Works**: Bit manipulation (n & (n-1) == 0).

**What Breaks**: Different property - single bit set.

**New Constraint**: Return true if power of two.

**Algorithm Changes**: n > 0 && (n & (n-1)) == 0.

**Complexity Changes**: Time O(1), Space O(1).