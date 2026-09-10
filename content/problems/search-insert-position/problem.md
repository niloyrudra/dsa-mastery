---
id: "search-insert-position"
title: "Search Insert Position"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/search-insert-position/"
patterns: ["binary-search-boundary"]
algorithms: ["binary-search-boundary"]
dataStructures: ["array"]
templates: ["binary-search-lower-bound"]
prerequisites: ["arrays", "binary-search"]
relatedProblems: ["binary-search", "first-bad-version"]
status: "PUBLISHED"
orderIndex: 25
reasonForSelection: "Teaches lower bound binary search; returns insertion point when not found"
---

# Problem Statement

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with O(log n) runtime complexity.

## Constraints

- 1 ≤ nums.length ≤ 10⁴
- -10⁴ ≤ nums[i] ≤ 10⁴
- nums contains distinct values sorted in ascending order.
- -10⁴ ≤ target ≤ 10⁴

## Examples

**Example 1:**
```
Input: nums = [1,3,5,6], target = 5
Output: 2
```

**Example 2:**
```
Input: nums = [1,3,5,6], target = 2
Output: 1
```

**Example 3:**
```
Input: nums = [1,3,5,6], target = 7
Output: 4
```

**Example 4:**
```
Input: nums = [1,3,5,6], target = 0
Output: 0
```

## Variations

### Variation 1: First Bad Version
**Name**: First Bad Version

**What Changed**: API-based search instead of array access.

**Original Approach Works**: Lower bound binary search works identically.

**What Breaks**: Direct array access replaced with API call.

**New Constraint**: isBadVersion(version) API instead of array.

**Algorithm Changes**: No - same lower bound logic.

**Complexity Changes**: Time O(log n) → O(log n), Space O(1) → O(1).