---
id: "minimum-number-of-arrows-to-burst-balloons"
title: "Minimum Number of Arrows to Burst Balloons"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/"
patterns: ["interval-fundamentals", "greedy-fundamentals", "binary-search-on-answer"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["merge-intervals", "non-overlapping-intervals", "meeting-rooms-ii", "insert-interval", "cheapest-flights-within-k-stops"]
status: "PUBLISHED"
orderIndex: 57
reasonForSelection: "Sort by end; greedy arrow at end of current interval"
---

# Problem Statement

There are some spherical balloons taped onto a flat wall that represents the XY-plane. The balloons are represented as a 2D integer array `points` where `points[i] = [xstart, xend]` denotes a balloon whose **horizontal diameter** stretches between `xstart` and `xend`. You do not know the exact y-coordinates of the balloons.

You can shoot arrows vertically upwards. A balloon with `xstart` and `xend` is **burst** by an arrow shot at `x` if `xstart ≤ x ≤ xend`. There is no limit to the number of arrows that can be shot. An arrow shot at `x` travels up infinitely.

Return the minimum number of arrows that must be shot to burst all balloons.

## Constraints

- 1 ≤ points.length ≤ 10⁵
- -2³¹ ≤ xstart < xend ≤ 2³¹ - 1

## Examples

**Example 1:**
```
Input: points = [[10,16],[2,8],[1,6],[7,12]]
Output: 2
Explanation: The balloons can be burst by 2 arrows: shoot at x = 6 (bursts [1,6] and [2,8]) and x = 11 (bursts [7,12] and [10,16]).
```

**Example 2:**
```
Input: points = [[1,2],[3,4],[5,6],[7,8]]
Output: 4
```

**Example 3:**
```
Input: points = [[1,2],[2,3],[3,4],[4,5]]
Output: 2
```

## Variations

### Variation 1: Non-overlapping Intervals
**Name**: Non-overlapping Intervals

**What Changed**: Remove minimum intervals to make non-overlapping.

**Original Approach Works**: Sort by end, greedy.

**What Breaks**: Remove vs shoot arrows.

**New Constraint**: Return minimum removals.

**Algorithm Changes**: Sort by end, greedy keep earliest end.

**Complexity Changes**: Time O(n log n), Space O(1).

### Variation 2: Meeting Rooms II
**Name**: Meeting Rooms II

**What Changed**: Find minimum rooms for meetings.

**Original Approach Works**: Min-heap or sweep line.

**What Breaks**: Rooms vs arrows.

**New Constraint**: Return minimum rooms.

**Algorithm Changes**: Min-heap for end times or sweep line.

**Complexity Changes**: Time O(n log n), Space O(n).