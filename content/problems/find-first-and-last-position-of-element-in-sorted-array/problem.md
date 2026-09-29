---
id: "find-first-and-last-position-of-element-in-sorted-array"
title: "Find First and Last Position of Element in Sorted Array"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/"
patterns: ["binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["binary-search", "search-insert-position", "first-bad-version", "find-first-last-position-in-sorted-array"]
status: "PUBLISHED"
orderIndex: 22
reasonForSelection: "Two binary searches for lower/upper bound; boundary finding"
---

# Problem Statement

Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value.

If `target` is not found in the array, return `[-1, -1]`.

You must write an algorithm with `O(log n)` runtime complexity.

## Constraints

- 0 ≤ nums.length ≤ 10⁵
- -10⁹ ≤ nums[i] ≤ 10⁹
- nums is a non-decreasing array.
- -10⁹ ≤ target ≤ 10⁹

## Examples

**Example 1:**
```
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]
```

**Example 2:**
```
Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
```

**Example 3:**
```
Input: nums = [], target = 0
Output: [-1,-1]
```

## Variations

### Variation 1: Search Insert Position
**Name**: Search Insert Position

**What Changed**: Return insertion index if target not found.

**Original Approach Works**: Binary search for lower bound.

**What Breaks**: Need to return left index when target not found.

**New Constraint**: Return index where target would be inserted.

**Algorithm Changes**: Return `left` when loop ends (lower bound).

**Complexity Changes**: Time O(log n), Space O(1).

### Variation 2: First Bad Version
**Name**: First Bad Version

**What Changed**: Search on version numbers via API call.

**Original Approach Works**: Binary search on answer space.

**What Breaks**: Cannot access array directly, must use API.

**New Constraint**: Use `isBadVersion(mid)` API.

**Algorithm Changes**: Binary search on range [1, n].

**Complexity Changes**: Time O(log n), Space O(1).