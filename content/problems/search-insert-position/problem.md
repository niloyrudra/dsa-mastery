---
id: "search-insert-position"
title: "Search Insert Position"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/search-insert-position/"
patterns: ["binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["binary-search", "first-bad-version", "find-first-and-last-position-of-element-in-sorted-array", "search-a-2d-matrix"]
status: "PUBLISHED"
orderIndex: 21
reasonForSelection: "Binary search variant for lower bound; introduces boundary concepts"
---

# Problem Statement

Given a sorted array of distinct integers and a target value, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with `O(log n)` runtime complexity.

## Constraints

- 1 ≤ nums.length ≤ 10⁴
- -10⁴ ≤ nums[i] ≤ 10⁴
- nums contains **distinct** values sorted in **ascending order**.
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

### Variation 1: Binary Search
**Name**: Binary Search

**What Changed**: Return index if found, -1 if not.

**Original Approach Works**: Binary search for exact match.

**What Breaks**: Lower bound vs exact match.

**New Constraint**: Return index or -1.

**Algorithm Changes**: Return mid if found, -1 if loop ends.

**Complexity Changes**: Time O(log n), Space O(1).

### Variation 2: First Bad Version
**Name**: First Bad Version

**What Changed**: Search on version numbers via API.

**Original Approach Works**: Binary search for lower bound.

**What Breaks**: API instead of array.

**New Constraint**: Use isBadVersion(mid).

**Algorithm Changes**: Binary search on [1, n].

**Complexity Changes**: Time O(log n), Space O(1).