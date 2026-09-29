---
id: "car-fleet"
title: "Car Fleet"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/car-fleet/"
patterns: ["monotonic-stack", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array", "stack"]
templates: []
prerequisites: ["array", "stack"]
relatedProblems: ["daily-temperatures", "largest-rectangle-in-histogram", "trapping-rain-water"]
status: "PUBLISHED"
orderIndex: 10
reasonForSelection: "Monotonic stack for collision time; process cars in reverse order"
---

# Problem Statement

There are `n` cars going to the same destination along a one-lane road. The destination is `target` miles away.

You are given two integer arrays `position` and `speed`, both of length `n`, where `position[i]` is the position of the `i`th car and `speed[i]` is the speed of the `i`th car (in miles per hour).

A car can never pass another car ahead of it, but it can catch up to it and drive bumper to bumper **at the same speed**. The faster car will **slow down** to match the slower car's speed. The distance between these two cars is ignored.

A **car fleet** is a set of cars driving at the same position and same speed. Note that a single car is also a car fleet.

If a car catches up to a car fleet right at the destination point, it will still be considered as part of the fleet.

Return the **number of car fleets** that will arrive at the destination.

## Constraints

- n == position.length == speed.length
- 1 ≤ n ≤ 10⁵
- 0 < target ≤ 10⁶
- 0 ≤ position[i] < target
- All values of `position` are **unique**.
- 0 < speed[i] ≤ 10⁶

## Examples

**Example 1:**
```
Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
Output: 3
Explanation:
The cars starting at 10 (speed 2) and 8 (speed 4) become a fleet at position 12.
The cars starting at 0 (speed 1) and 3 (speed 3) become a fleet at position 12.
The car starting at 5 (speed 1) is a fleet by itself.
```

**Example 2:**
```
Input: target = 10, position = [3], speed = [3]
Output: 1
```

**Example 3:**
```
Input: target = 100, position = [0,2,4], speed = [4,2,1]
Output: 1
```

## Variations

### Variation 1: Daily Temperatures
**Name**: Daily Temperatures

**What Changed**: Find days until warmer temperature instead of car fleets.

**Original Approach Works**: Same monotonic stack, process in reverse.

**What Breaks**: Different domain, same algorithm pattern.

**New Constraint**: Return array of days until warmer temperature.

**Algorithm Changes**: Stack stores indices, compare temperatures.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Largest Rectangle in Histogram
**Name**: Largest Rectangle in Histogram

**What Changed**: Find largest rectangle area in histogram.

**Original Approach Works**: Monotonic stack for next/previous smaller.

**What Breaks**: Need to calculate area = height * width.

**New Constraint**: Return maximum area.

**Algorithm Changes**: Track indices in stack, calculate on pop.

**Complexity Changes**: Time O(n), Space O(n).