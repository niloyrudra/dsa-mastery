---
id: "first-bad-version"
title: "First Bad Version"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/first-bad-version/"
patterns: ["binary-search-boundary", "binary-search-on-answer"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["binary-search", "search-insert-position", "find-first-and-last-position-of-element-in-sorted-array"]
status: "PUBLISHED"
orderIndex: 21
reasonForSelection: "Binary search on version space; API-based search"
---

# Problem Statement

You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.

Suppose you have `n` versions `[1, 2, ..., n]` and you want to find out the first bad one, which causes all the following ones to be bad.

You are given an API `bool isBadVersion(version)` which returns whether `version` is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.

## Constraints

- 1 ≤ bad ≤ n ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: n = 5, bad = 4
Output: 4
Explanation:
isBadVersion(3) -> false
isBadVersion(4) -> true
isBadVersion(5) -> true
```

**Example 2:**
```
Input: n = 1, bad = 1
Output: 1
```

## Variations

### Variation 1: Binary Search
**Name**: Binary Search

**What Changed**: Search in array instead of API.

**Original Approach Works**: Same binary search logic.

**What Breaks**: Direct array access vs API call.

**New Constraint**: Return index of target in array.

**Algorithm Changes**: Use nums[mid] instead of isBadVersion(mid).

**Complexity Changes**: Time O(log n), Space O(1).

### Variation 2: Search Insert Position
**Name**: Search Insert Position

**What Changed**: Return insertion index if not found.

**Original Approach Works**: Binary search for lower bound.

**What Breaks**: Need to return left index when target not found.

**New Constraint**: Return index where target would be inserted.

**Algorithm Changes**: Return `left` when loop ends.

**Complexity Changes**: Time O(log n), Space O(1).