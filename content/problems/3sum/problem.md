---
id: "3sum"
title: "3Sum"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/3sum/"
patterns: ["two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["array"]
templates: ["two-pointers-template-ts"]
prerequisites: ["array", "two-pointers"]
relatedProblems: ["two-sum", "two-sum-ii"]
status: "PUBLISHED"
orderIndex: 13
reasonForSelection: "Classic three-pointer pattern; extends two-sum with duplicate handling"
---

# Problem Statement

Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must not contain duplicate triplets.

## Constraints

- 3 ≤ nums.length ≤ 3000
- -10⁵ ≤ nums[i] ≤ 10⁵

## Examples

**Example 1:**
```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation: nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
             nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
             nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
The distinct triplets are [-1,0,1] and [-1,-1,2].
```

**Example 2:**
```
Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet does not sum to 0.
```

**Example 3:**
```
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only possible triplet sums to 0.
```

## Variations

### Variation 1: 3Sum Closest
**Name**: 3Sum Closest

**What Changed**: Find three integers such that the sum is closest to a given target.

**Original Approach Works**: Sorting + two-pointer still applies, but we track closest sum instead of exact matches.

**What Breaks**: No duplicate skipping needed; must track absolute difference.

**New Constraint**: Return the sum (not triplets) closest to target.

**Algorithm Changes**: Yes - track min absolute difference during two-pointer scan.

**Complexity Changes**: Time O(n²), Space O(1).

### Variation 2: 4Sum
**Name**: 4Sum

**What Changed**: Find all unique quadruplets that sum to target.

**Original Approach Works**: Sort + fix two elements + two-pointer on remainder.

**What Breaks**: More duplicate dimensions to handle; O(n³) time.

**New Constraint**: Four elements instead of three; target can be any integer.

**Algorithm Changes**: Yes - nested loop for two fixed elements + two-pointer.

**Complexity Changes**: Time O(n³), Space O(1) or O(n) for sorting.