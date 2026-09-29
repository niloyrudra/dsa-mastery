---
id: "target-sum"
title: "Target Sum"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/target-sum/"
patterns: ["dp-1d-fundamentals", "dp-2d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["partition-equal-subset-sum", "coin-change", "find-target-sum-ways", "last-stone-weight-ii"]
status: "PUBLISHED"
orderIndex: 44
reasonForSelection: "Subset sum transformation; P - N = target, P + N = sum"
---

# Problem Statement

You are given an integer array `nums` and an integer `target`.

You want to build an **expression** out of `nums` by adding one of the symbols `'+'` and `'-'` before each integer in `nums` and then concatenate all the integers.

Return the number of different **expressions** that you can build, which evaluates to `target`.

## Constraints

- 1 ≤ nums.length ≤ 20
- 0 ≤ nums[i] ≤ 1000
- 0 ≤ sum(nums[i]) ≤ 1000
- -1000 ≤ target ≤ 1000

## Examples

**Example 1:**
```
Input: nums = [1,1,1,1,1], target = 3
Output: 5
Explanation:
-1 + 1 + 1 + 1 + 1 = 3
+1 - 1 + 1 + 1 + 1 = 3
+1 + 1 - 1 + 1 + 1 = 3
+1 + 1 + 1 - 1 + 1 = 3
+1 + 1 + 1 + 1 - 1 = 3
```

**Example 2:**
```
Input: nums = [1], target = 1
Output: 1
```

## Variations

### Variation 1: Partition Equal Subset Sum
**Name**: Partition Equal Subset Sum

**What Changed**: Check if can partition into equal sum.

**Original Approach Works**: Subset sum to sum/2.

**What Breaks**: Boolean vs count.

**New Constraint**: Return true if partition possible.

**Algorithm Changes**: Subset sum to sum/2, return boolean.

**Complexity Changes**: Time O(n * sum), Space O(sum).

### Variation 2: Find Target Sum Ways
**Name**: Find Target Sum Ways (same problem, different framing)

**What Changed**: Same problem.

**Original Approach Works**: Same DP.

**What Breaks**: N/A.

**New Constraint**: N/A.

**Algorithm Changes**: N/A.

**Complexity Changes**: N/A.