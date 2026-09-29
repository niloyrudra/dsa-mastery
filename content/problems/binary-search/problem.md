---
id: "binary-search"
title: "Binary Search"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/binary-search/"
patterns: ["binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["search-insert-position", "first-bad-version", "find-first-and-last-position-of-element-in-sorted-array"]
status: "PUBLISHED"
orderIndex: 20
reasonForSelection: "Fundamental binary search implementation; basis for boundary search"
---

# Problem Statement

Given an array of integers `nums` which is sorted in ascending order, and an integer `target`, write a function to search `target` in `nums`. If `target` exists, then return its index. Otherwise, return `-1`.

You must write an algorithm with `O(log n)` runtime complexity.

## Constraints

- 1 ≤ nums.length ≤ 10⁴
- -10⁴ < nums[i], target < 10⁴
- All the integers in nums are **unique**.
- nums is sorted in ascending order.

## Examples

**Example 1:**
```
Input: nums = [-1,0,3,5,9,12], target = 9
Output: 4
Explanation: 9 exists in nums and its index is 4
```

**Example 2:**
```
Input: nums = [-1,0,3,5,9,12], target = 2
Output: -1
Explanation: 2 does not exist in nums so return -1
```

## Variations

### Variation 1: Search Insert Position
**Name**: Search Insert Position

**What Changed**: Return insertion index if target not found.

**Original Approach Works**: Binary search finds lower bound.

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