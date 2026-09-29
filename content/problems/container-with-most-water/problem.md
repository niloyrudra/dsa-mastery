---
id: "container-with-most-water"
title: "Container With Most Water"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/container-with-most-water/"
patterns: ["two-pointer"]
algorithms: ["two-pointer"]
dataStructures: ["array"]
templates: ["two-pointers-template-ts"]
prerequisites: ["array", "two-pointers"]
relatedProblems: ["trapping-rain-water", "two-sum-ii"]
status: "PUBLISHED"
orderIndex: 14
reasonForSelection: "Two pointers with greedy area maximization; classic greedy proof"
---

# Problem Statement

You are given an integer array `height` of length `n`. There are `n` vertical lines drawn such that the two endpoints of the `i`th line are `(i, 0)` and `(i, height[i])`.

Find two lines that together with the x-axis form a container, such that the container contains the most water.

Return *the maximum amount of water a container can store*.

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
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
```

**Example 2:**
```
Input: height = [1,1]
Output: 1
```

## Variations

### Variation 1: Trapping Rain Water
**Name**: Trapping Rain Water

**What Changed**: Instead of two lines forming a container, we have an elevation map where water can be trapped between bars.

**Original Approach Works**: Two-pointer with greedy still applies, but we track water trapped at each position.

**What Breaks**: The area formula changes from min(height[left], height[right]) * width to min(maxLeft, maxRight) - height[i].

**New Constraint**: Water can be trapped at multiple positions simultaneously.

**Algorithm Changes**: Yes - two-pointer tracking max heights from both sides.

**Complexity Changes**: Time O(n), Space O(1) - same as container problem.