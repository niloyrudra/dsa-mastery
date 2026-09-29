---
id: "meeting-rooms-ii"
title: "Meeting Rooms II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/meeting-rooms-ii/"
patterns: ["interval-fundamentals", "heap-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array", "heap"]
templates: ["heap-template-ts"]
prerequisites: ["array", "heap"]
relatedProblems: ["meeting-rooms", "insert-interval", "merge-intervals", "non-overlapping-intervals", "minimum-number-of-arrows-to-burst-balloons"]
status: "PUBLISHED"
orderIndex: 55
reasonForSelection: "Min-heap for end times; sweep line with priority queue"
---

# Problem Statement

Given an array of meeting time intervals `intervals` where `intervals[i] = [start, end]`, return the minimum number of conference rooms required.

## Constraints

- 1 ≤ intervals.length ≤ 10⁴
- 0 ≤ start < end ≤ 10⁶

## Examples

**Example 1:**
```
Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2
```

**Example 2:**
```
Input: intervals = [[7,10],[2,4]]
Output: 1
```

## Variations

### Variation 1: Meeting Rooms
**Name**: Meeting Rooms

**What Changed**: Check if can attend all (boolean).

**Original Approach Works**: Sort, check adjacent for overlap.

**What Breaks**: Boolean vs count.

**New Constraint**: Return true if no overlaps.

**Algorithm Changes**: Check if intervals[i][1] > intervals[i+1][0].

**Complexity Changes**: Time O(n log n), Space O(1).

### Variation 2: Car Fleet
**Name**: Car Fleet

**What Changed**: Car positions/speeds instead of meetings.

**Original Approach Works**: Monotonic stack or sort by position reverse.

**What Breaks**: Different domain.

**New Constraint**: Return number of fleets.

**Algorithm Changes**: Calculate arrival times, process in reverse.

**Complexity Changes**: Time O(n log n), Space O(n).