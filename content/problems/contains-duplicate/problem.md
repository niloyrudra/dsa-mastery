---
id: "contains-duplicate"
title: "Contains Duplicate"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/contains-duplicate/"
patterns: ["hash-map-lookup"]
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table", "array"]
templates: ["hash-map-template-ts"]
prerequisites: ["array", "hash-table"]
relatedProblems: ["find-the-duplicate-number"]
status: "PUBLISHED"
orderIndex: 2
reasonForSelection: "Fundamental hash set usage; introduces frequency counting pattern; basis for many duplicate detection problems"
---

# Problem Statement

Given an integer array `nums`, return `true` if any value appears **at least twice** in the array, and return `false` if every element is distinct.

## Constraints

- 1 ≤ nums.length ≤ 10⁵
- -10⁹ ≤ nums[i] ≤ 10⁹

## Examples

**Example 1:**
```
Input: nums = [1,2,3,1]
Output: true
```

**Example 2:**
```
Input: nums = [1,2,3,4]
Output: false
```

**Example 3:**
```
Input: nums = [1,1,1,3,3,4,3,2,4,2]
Output: true
```

## Variations

### Variation 1: Contains Duplicate II
**Name**: Contains Duplicate II

**What Changed**: Check if there are duplicates within index distance k.

**Original Approach Works**: Hash set can be extended with sliding window.

**What Breaks**: Simple set doesn't track indices.

**New Constraint**: Duplicates must be within k indices.

**Algorithm Changes**: Yes - use hash map value->index, check distance.

**Complexity Changes**: Time O(n), Space O(min(n, k)).