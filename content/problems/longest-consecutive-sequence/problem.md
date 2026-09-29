---
id: "longest-consecutive-sequence"
title: "Longest Consecutive Sequence"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/longest-consecutive-sequence/"
patterns: ["hash-map-lookup"]
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts"]
prerequisites: ["hash-table"]
relatedProblems: ["contains-duplicate", "valid-anagram", "find-the-duplicate-number", "missing-number"]
status: "PUBLISHED"
orderIndex: 5
reasonForSelection: "Hash set for O(1) lookups; only start sequences from minimum"
---

# Problem Statement

Given an unsorted array of integers `nums`, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in `O(n)` time.

## Constraints

- 0 ≤ nums.length ≤ 10⁵
- -10⁹ ≤ nums[i] ≤ 10⁹

## Examples

**Example 1:**
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

**Example 2:**
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
```

## Variations

### Variation 1: Contains Duplicate
**Name**: Contains Duplicate

**What Changed**: Check if any duplicate exists.

**Original Approach Works**: Hash set for O(1) lookup.

**What Breaks**: Boolean vs sequence length.

**New Constraint**: Return true if duplicate exists.

**Algorithm Changes**: Check if element in set, add if not.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Find the Duplicate Number
**Name**: Find the Duplicate Number

**What Changed**: Find the duplicate value in array.

**Original Approach Works**: Floyd's cycle detection or hash set.

**What Breaks**: Single duplicate value vs sequence.

**New Constraint**: Return duplicate value.

**Algorithm Changes**: Hash set or Floyd's on array indices.

**Complexity Changes**: Time O(n), Space O(1) with Floyd's.