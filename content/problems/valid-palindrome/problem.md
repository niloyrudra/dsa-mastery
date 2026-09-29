---
id: "valid-palindrome"
title: "Valid Palindrome"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/valid-palindrome/"
patterns: ["two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["array"]
templates: ["two-pointers-template-ts"]
prerequisites: ["array", "two-pointers"]
relatedProblems: ["longest-palindromic-substring"]
status: "PUBLISHED"
orderIndex: 11
reasonForSelection: "Classic two-pointer string problem; teaches filtering + two pointers"
---

# Problem Statement

A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric characters include letters and numbers.

Given a string `s`, return `true` if it is a palindrome, or `false` otherwise.

## Constraints

- 1 ≤ s.length ≤ 2 × 10⁵
- s consists only of printable ASCII characters

## Examples

**Example 1:**
```
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: "amanaplanacanalpanama" is a palindrome.
```

**Example 2:**
```
Input: s = "race a car"
Output: false
Explanation: "raceacar" is not a palindrome.
```

**Example 3:**
```
Input: s = " "
Output: true
Explanation: Empty string is a palindrome.
```

## Variations

### Variation 1: Valid Palindrome II
**Name**: Valid Palindrome II

**What Changed**: Can delete at most one character.

**Original Approach Works**: Two-pointer still works, but need to check both skip-left and skip-right on mismatch.

**What Breaks**: Simple equality check fails when one deletion allowed.

**New Constraint**: At most one deletion allowed.

**Algorithm Changes**: Yes - on mismatch, recursively check both substrings.

**Complexity Changes**: Time O(n) → O(n), Space O(1) → O(n) for recursion or O(1) iterative.