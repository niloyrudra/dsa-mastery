---
id: "meeting-rooms"
title: "Meeting Rooms"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/meeting-rooms/"
patterns: ["interval-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["meeting-rooms-ii", "insert-interval", "merge-intervals", "non-overlapping-intervals"]
status: "PUBLISHED"
orderIndex: 54
reasonForSelection: "Sort by start; check adjacent for overlap"
---

# Problem Statement

Given an array of meeting time intervals `intervals` where `intervals[i] = [start, end]`, determine if a person could attend all meetings.

## Constraints

- 0 ≤ intervals.length ≤ 10⁴
- intervals[i].length == 2
- 0 ≤ start < end ≤ 10⁶

## Examples

**Example 1:**
```
Input: intervals = [[0,30],[5,10],[15,20]]
Output: false
```

**Example 2:**
```
Input: intervals = [[7,10],[2,4]]
Output: true
```

## Variations

### Variation 1: Meeting Rooms II
**Name**: Meeting Rooms II

**What Changed**: Find minimum number of rooms needed.

**Original Approach Works**: Sort start and end separately, two pointers.

**What Breaks**: Count concurrent meetings, not just overlap.

**New Constraint**: Return minimum rooms.

**Algorithm Changes**: Sort starts and ends separately, two pointers.

**Complexity Changes**: Time O(n log n), Space O(n).

### Variation 2: Insert Interval
**Name**: Insert Interval

**What Changed**: Insert new interval and merge.

**Original Approach Works**: Three-phase (before, merge, after).

**What Breaks**: New interval insertion.

**New Constraint**: Return merged intervals.

**Algorithm Changes**: Three-phase: add before, merge overlapping, add after.

**Complexity Changes**: Time O(n), Space O(n).