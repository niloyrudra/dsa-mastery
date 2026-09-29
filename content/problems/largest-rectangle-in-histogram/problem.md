---
id: "largest-rectangle-in-histogram"
title: "Largest Rectangle in Histogram"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/largest-rectangle-in-histogram/"
patterns: ["monotonic-stack", "stack-fundamentals"]
algorithms: []
dataStructures: ["array", "stack"]
templates: []
prerequisites: ["array", "stack"]
relatedProblems: ["daily-temperatures", "trapping-rain-water", "sliding-window-maximum", "largest-rectangle-in-matrix"]
status: "PUBLISHED"
orderIndex: 13
reasonForSelection: "Monotonic increasing stack; area = height * width"
---

# Problem Statement

Given an array of integers `heights` representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.

## Constraints

- 1 ≤ heights.length ≤ 10⁵
- 0 ≤ heights[i] ≤ 10⁴

## Examples

**Example 1:**
```
Input: heights = [2,1,5,6,2,3]
Output: 10
Explanation: The above is a histogram where width of each bar is 1.
The largest rectangle is shown in the red area, which has an area = 10 units.
```

**Example 2:**
```
Input: heights = [2,4]
Output: 4
```

## Variations

### Variation 1: Daily Temperatures
**Name**: Daily Temperatures

**What Changed**: Find days until warmer temperature.

**Original Approach Works**: Same monotonic stack.

**What Breaks**: Different output - days vs area.

**New Constraint**: Return days until warmer.

**Algorithm Changes**: Store indices, calculate i - stack.top on pop.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Trapping Rain Water
**Name**: Trapping Rain Water

**What Changed**: Water trapped between bars.

**Original Approach Works**: Monotonic stack or two-pointer.

**What Breaks**: Area calculation different.

**New Constraint**: Return total trapped water.

**Algorithm Changes**: Stack of indices, calculate on pop.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 3: Sliding Window Maximum
**Name**: Sliding Window Maximum

**What Changed**: Max in sliding window of size k.

**Original Approach Works**: Deque (monotonic queue) for max.

**What Breaks**: Window size constraint.

**New Constraint**: Return max for each window.

**Algorithm Changes**: Deque store indices, remove out-of-window, maintain decreasing.

**Complexity Changes**: Time O(n), Space O(k).