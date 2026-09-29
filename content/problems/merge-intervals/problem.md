---
id: "merge-intervals"
title: "Merge Intervals"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/merge-intervals/"
patterns: ["interval-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["insert-interval", "meeting-rooms", "meeting-rooms-ii", "non-overlapping-intervals", "minimum-number-of-arrows-to-burst-balloons"]
status: "PUBLISHED"
orderIndex: 56
reasonForSelection: "Sort by start, then merge overlapping; fundamental interval operation"
---

# Problem Statement

Given an array of intervals where `intervals[i] = [start, end]`, merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.

## Constraints

- 1 ≤ intervals.length ≤ 10⁴
- intervals[i].length == 2
- 0 ≤ start ≤ end ≤ 10⁴

## Examples

**Example 1:**
```
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Intervals [1,3] and [2,6] overlap, merge them into [1,6].
```

**Example 2:**
```
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.
```

## Variations

### Variation 1: Insert Interval
**Name**: Insert Interval

**What Changed**: Insert new interval and merge.

**Original Approach Works**: Three-phase (before, merge, after).

**What Breaks**: New interval insertion.

**New Constraint**: Return merged intervals.

**Algorithm Changes**: Three-phase: add before, merge overlapping, add after.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Non-overlapping Intervals
**Name**: Non-overlapping Intervals

**What Changed**: Remove minimum intervals to make non-overlapping.

**Original Approach Works**: Sort by end, greedy pick earliest end.

**What Breaks**: Remove instead of merge.

**New Constraint**: Return minimum removals.

**Algorithm Changes**: Sort by end, greedy keep earliest end.

**Complexity Changes**: Time O(n log n), Space O(1).