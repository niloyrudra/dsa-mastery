---
id: "insert-interval"
title: "Insert Interval"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/insert-interval/"
patterns: ["interval-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["merge-intervals", "meeting-rooms", "meeting-rooms-ii", "non-overlapping-intervals"]
status: "PUBLISHED"
orderIndex: 53
reasonForSelection: "Insert and merge; three-phase (before, merge, after)"
---

# Problem Statement

You are given an array of non-overlapping intervals `intervals` where `intervals[i] = [start, end]` represent the start and the end of the `i`th interval and `intervals` is sorted in ascending order by `start`. You are also given an interval `newInterval = [start, end]` that represents the start and end of another interval.

Insert `newInterval` into `intervals` such that `intervals` is still sorted in ascending order by `start` and `intervals` still does not have any overlapping intervals (merge overlapping intervals if necessary).

Return `intervals` after the insertion.

## Constraints

- 0 ≤ intervals.length ≤ 10⁴
- intervals[i].length == 2
- 0 ≤ start ≤ end ≤ 10⁵
- `intervals` is sorted by `start` in ascending order.
- `newInterval.length == 2`
- 0 ≤ start ≤ end ≤ 10⁵

## Examples

**Example 1:**
```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
```

**Example 2:**
```
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
```

## Variations

### Variation 1: Merge Intervals
**Name**: Merge Intervals

**What Changed**: Merge all overlapping intervals in array.

**Original Approach Works**: Sort and merge overlapping.

**What Breaks**: No new interval to insert, merge all.

**New Constraint**: Return merged intervals.

**Algorithm Changes**: Sort by start, merge overlapping.

**Complexity Changes**: Time O(n log n), Space O(n).

### Variation 2: Meeting Rooms
**Name**: Meeting Rooms

**What Changed**: Check if can attend all meetings (no overlaps).

**Original Approach Works**: Sort, check adjacent for overlap.

**What Breaks**: Boolean return vs merged intervals.

**New Constraint**: Return true if no overlaps.

**Algorithm Changes**: Check if intervals[i][1] > intervals[i+1][0].

**Complexity Changes**: Time O(n log n), Space O(1).