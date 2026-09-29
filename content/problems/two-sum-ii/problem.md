---
id: "two-sum-ii"
title: "Two Sum II"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/two-sum-ii/"
patterns: ["two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["array"]
templates: ["two-pointers-template-ts"]
prerequisites: ["array", "two-pointers"]
relatedProblems: ["two-sum", "3sum"]
status: "PUBLISHED"
orderIndex: 12
reasonForSelection: "Sorted array enables O(1) space two-pointer solution"
---

# Problem Statement

Given a **1-indexed** array of integers `numbers` that is already **sorted in non-decreasing order**, find two numbers such that they add up to a specific `target` number. Let these two numbers be `numbers[index1]` and `numbers[index2]` where `1 <= index1 < index2 <= numbers.length`.

Return the indices of the two numbers, `index1` and `index2`, **added by one** as an integer array `[index1, index2]` of length 2.

The tests are generated such that there is **exactly one solution**. You **may not** use the same element twice.

Your solution must use only constant extra space.

## Constraints

- 2 ≤ numbers.length ≤ 3 × 10⁴
- -1000 ≤ numbers[i] ≤ 1000
- numbers is sorted in **non-decreasing order**
- -1000 ≤ target ≤ 1000
- The tests are generated such that there is exactly one solution.

## Examples

**Example 1:**
```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We return [1, 2].
```

**Example 2:**
```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We return [1, 3].
```

**Example 3:**
```
Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We return [1, 2].
```

## Variations

### Variation 1: Two Sum (Unsorted)
**Name**: Two Sum

**What Changed**: Input array is not sorted.

**Original Approach Works**: Two-pointer requires sorted input.

**What Breaks**: Two-pointer gives wrong answer on unsorted array.

**New Constraint**: No sorting guarantee; must handle any order.

**Algorithm Changes**: Yes - hash map for O(n) time, O(n) space.

**Complexity Changes**: Time O(n) → O(n), Space O(1) → O(n).