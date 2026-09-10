---
id: "two-sum"
title: "Two Sum"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/two-sum/"
patterns: ["hash-map-lookup", "two-pointer-opposite"]
algorithms: ["hash-map-lookup", "two-pointer-opposite"]
dataStructures: ["hash-map", "array"]
templates: ["hash-map-lookup", "two-pointer-opposite"]
prerequisites: ["arrays", "hash-map"]
relatedProblems: ["three-sum", "two-sum-ii", "4sum", "two-sum-iv"]
status: "PUBLISHED"
orderIndex: 1
reasonForSelection: "Foundational complement-finding pattern; introduces hash map trade-off; appears in 3Sum, 4Sum, subarray sum problems"
---

# Problem Statement

Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.

## Constraints

- 2 ≤ nums.length ≤ 10⁴
- -10⁹ ≤ nums[i] ≤ 10⁹
- -10⁹ ≤ target ≤ 10⁹
- Only one valid answer exists

## Examples

**Example 1:**
```
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
```

**Example 2:**
```
Input: nums = [3,2,4], target = 6
Output: [1,2]
```

**Example 3:**
```
Input: nums = [3,3], target = 6
Output: [0,1]
```

## Variations

### Variation 1: Sorted Input (Two Sum II)
**Name**: Two Sum II - Input Array Is Sorted

**What Changed**: Input array is guaranteed sorted in ascending order.

**Original Approach Works**: Hash map still works but wastes sorted property.

**What Breaks**: Nothing breaks, but O(n) space is suboptimal.

**New Constraint**: Sorted input enables two-pointer O(1) space.

**Algorithm Changes**: Yes - two-pointer opposite ends replaces hash map.

**Complexity Changes**: Time O(n) → O(n), Space O(n) → O(1).

### Variation 2: Multiple Pairs (3Sum)
**Name**: 3Sum

**What Changed**: Find all unique triplets that sum to zero.

**Original Approach Works**: No - two-sum finds one pair, 3Sum needs all triplets.

**What Breaks**: Duplicate handling becomes critical.

**New Constraint**: Must return all unique triplets, no duplicates.

**Algorithm Changes**: Yes - sort + two-pointer for each fixed element.

**Complexity Changes**: Time O(n²), Space O(1) or O(n) for sorting.