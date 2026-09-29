---
id: "daily-temperatures"
title: "Daily Temperatures"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/daily-temperatures/"
patterns: ["monotonic-stack", "stack-fundamentals"]
algorithms: []
dataStructures: ["array", "stack"]
templates: []
prerequisites: ["array", "stack"]
relatedProblems: ["car-fleet", "largest-rectangle-in-histogram", "trapping-rain-water", "next-greater-element"]
status: "PUBLISHED"
orderIndex: 11
reasonForSelection: "Monotonic decreasing stack for next greater element; classic pattern"
---

# Problem Statement

Given an array of integers `temperatures` represents the daily temperatures, return an array `answer` such that `answer[i]` is the number of days you have to wait after the `i`th day to get a warmer temperature. If there is no future day for which this is possible, keep `answer[i] == 0` instead.

## Constraints

- 1 ≤ temperatures.length ≤ 10⁵
- 30 ≤ temperatures[i] ≤ 100

## Examples

**Example 1:**
```
Input: temperatures = [73,74,75,71,69,72,76,73]
Output: [1,1,4,2,1,1,0,0]
```

**Example 2:**
```
Input: temperatures = [30,40,50,60]
Output: [1,1,1,0]
```

**Example 3:**
```
Input: temperatures = [30,60,90]
Output: [1,1,0]
```

## Variations

### Variation 1: Car Fleet
**Name**: Car Fleet

**What Changed**: Car positions and speeds instead of temperatures.

**Original Approach Works**: Monotonic stack processing in reverse.

**What Breaks**: Different domain, same algorithm.

**New Constraint**: Return number of car fleets.

**Algorithm Changes**: Calculate arrival times, process in reverse with stack.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Next Greater Element
**Name**: Next Greater Element

**What Changed**: Find next greater element value instead of days.

**Original Approach Works**: Same monotonic stack.

**What Breaks**: Return element value, not index difference.

**New Constraint**: Return next greater element for each position.

**Algorithm Changes**: Store values in stack, assign on pop.

**Complexity Changes**: Time O(n), Space O(n).