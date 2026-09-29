---
id: "letter-combinations-of-a-phone-number"
title: "Letter Combinations of a Phone Number"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/letter-combinations-of-a-phone-number/"
patterns: ["backtracking-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion"]
relatedProblems: ["generate-parentheses", "combinations", "permutations", "subsets"]
status: "PUBLISHED"
orderIndex: 57
reasonForSelection: "Backtracking with digit-to-letters mapping; classic phone keypad"
---

# Problem Statement

Given a string containing digits from `2-9` inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.

```
2: abc
3: def
4: ghi
5: jkl
6: mno
7: pqrs
8: tuv
9: wxyz
```

## Constraints

- 0 ≤ digits.length ≤ 4
- `digits[i]` is a digit in the range `['2', '9']`.

## Examples

**Example 1:**
```
Input: digits = "23"
Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**Example 2:**
```
Input: digits = ""
Output: []
```

**Example 3:**
```
Input: digits = "2"
Output: ["a","b","c"]
```

## Variations

### Variation 1: Generate Parentheses
**Name**: Generate Parentheses

**What Changed**: Generate valid parentheses instead of phone letters.

**Original Approach Works**: Backtracking with open/close count.

**What Breaks**: Different generation rules.

**New Constraint**: Return valid parentheses combinations.

**Algorithm Changes**: Track open/close count, add when valid.

**Complexity Changes**: Time O(4ⁿ/√n), Space O(n).

### Variation 2: Permutations
**Name**: Permutations

**What Changed**: Generate all permutations of array.

**Original Approach Works**: Backtracking with used array.

**What Breaks**: Different input and generation.

**New Constraint**: Return all permutations.

**Algorithm Changes**: Track used elements, swap or used array.

**Complexity Changes**: Time O(n!), Space O(n).