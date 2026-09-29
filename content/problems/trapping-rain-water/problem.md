---
id: "trapping-rain-water"
title: "Trapping Rain Water"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/trapping-rain-water/"
patterns: ["two-pointer", "monotonic-stack"]
algorithms: ["two-pointer"]
dataStructures: ["array"]
templates: ["two-pointers-template-ts"]
prerequisites: ["array", "two-pointers"]
relatedProblems: ["container-with-most-water", "daily-temperatures", "largest-rectangle-in-histogram"]
status: "PUBLISHED"
orderIndex: 15
reasonForSelection: "Two pointers or monotonic stack; water = min(left_max, right_max) - height"
---

# Problem Statement

Given `n` non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.

## Constraints

- n == height.length
- 1 ≤ n ≤ 2 × 10⁴
- 0 ≤ height[i] ≤ 10⁵

## Examples

**Example 1:**
```
Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
```

**Example 2:**
```
Input: height = [4,2,0,3,2,5]
Output: 9
```

## Variations

### Variation 1: Container With Most Water
**Name**: Container With Most Water

**What Changed**: Instead of trapping water at multiple positions, find two lines forming max container.

**Original Approach Works**: Two-pointer still applies with different formula.

**What Breaks**: The area is between two lines, not accumulated over many positions.

**New Constraint**: Only two lines form the container.

**Algorithm Changes**: Yes - max area = min(height[left], height[right]) * (right - left).

**Complexity Changes**: Time O(n), Space O(1) - same complexity.