---
id: "container-with-most-water"
title: "Container With Most Water"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/container-with-most-water/"
patterns: ["two-pointer-opposite"]
algorithms: ["two-pointer-opposite"]
dataStructures: ["array"]
templates: ["two-pointer-opposite"]
prerequisites: ["arrays", "two-pointer-opposite"]
relatedProblems: ["trapping-rain-water", "max-area-of-island"]
status: "PUBLISHED"
orderIndex: 13
reasonForSelection: "Greedy two-pointer optimization; proves moving smaller height is optimal"
---

# Problem Statement

You are given an integer array `height` of length `n`. There are `n` vertical lines drawn such that the two endpoints of the `i`th line are `(i, 0)` and `(i, height[i])`.

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return the maximum amount of water a container can store.

**Notice** that you may not slant the container.

## Constraints

- n == height.length
- 2 ≤ n ≤ 10⁵
- 0 ≤ height[i] ≤ 10⁴

## Examples

**Example 1:**
```
Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. 
The max area of water (blue section) the container can contain is 49.
```

**Example 2:**
```
Input: height = [1,1]
Output: 1
```

## Variations

### Variation 1: Trapping Rain Water
**Name**: Trapping Rain Water

**What Changed**: Find total water trapped between bars, not max container.

**Original Approach Works**: Two-pointer still works but computes sum of trapped water.

**What Breaks**: Area calculation changes from min * width to sum of min(left_max, right_max) - height[i].

**New Constraint**: Water trapped above each bar.

**Algorithm Changes**: Yes - same two-pointer but accumulate trapped water.

**Complexity Changes**: Time O(n) → O(n), Space O(1) → O(1).