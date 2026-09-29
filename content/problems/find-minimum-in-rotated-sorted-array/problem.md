---
id: "find-minimum-in-rotated-sorted-array"
title: "Find Minimum in Rotated Sorted Array"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/"
patterns: ["binary-search-on-answer", "binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["search-in-rotated-sorted-array", "find-peak-element", "search-insert-position"]
status: "PUBLISHED"
orderIndex: 23
reasonForSelection: "Binary search on rotated array; compare mid with right"
---

# Problem Statement

Suppose an array of length `n` sorted in ascending order is **rotated** between `1` and `n` times. For example, the array `nums = [0,1,2,4,5,6,7]` might become:

- `[4,5,6,7,0,1,2]` if it was rotated `4` times.
- `[0,1,2,4,5,6,7]` if it was rotated `7` times.

Notice that **rotating** an array `[a[0], a[1], ..., a[n-1]]` 1 time results in the array `[a[n-1], a[0], a[1], ..., a[n-2]]`.

Given the sorted rotated array `nums` of **unique** elements, return the minimum element of this array.

You must write an algorithm that runs in `O(log n)` time.

## Constraints

- n == nums.length
- 1 ≤ n ≤ 5000
- -5000 ≤ nums[i] ≤ 5000
- All the integers of `nums` are **unique**.
- `nums` is sorted and rotated between 1 and n times.

## Examples

**Example 1:**
```
Input: nums = [3,4,5,1,2]
Output: 1
Explanation: The original array was [1,2,3,4,5] rotated 3 times.
```

**Example 2:**
```
Input: nums = [4,5,6,7,0,1,2]
Output: 0
Explanation: The original array was [0,1,2,4,5,6,7] rotated 4 times.
```

**Example 3:**
```
Input: nums = [11,13,15,17]
Output: 11
Explanation: The original array was [11,13,15,17] rotated 4 times.
```

## Variations

### Variation 1: Search in Rotated Sorted Array
**Name**: Search in Rotated Sorted Array

**What Changed**: Search for target in rotated array.

**Original Approach Works**: Binary search with rotation detection.

**What Breaks**: Need to find target, not minimum.

**New Constraint**: Return index of target or -1.

**Algorithm Changes**: Determine which half is sorted, search there.

**Complexity Changes**: Time O(log n), Space O(1).

### Variation 2: Find Peak Element
**Name**: Find Peak Element

**What Changed**: Find any peak element (greater than neighbors).

**Original Approach Works**: Binary search on peak condition.

**What Breaks**: Different predicate - nums[mid] < nums[mid+1].

**New Constraint**: Return index of any peak.

**Algorithm Changes**: Move towards higher neighbor.

**Complexity Changes**: Time O(log n), Space O(1).