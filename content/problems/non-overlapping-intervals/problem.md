---
id: "non-overlapping-intervals"
title: "Non-overlapping Intervals"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/non-overlapping-intervals/"
patterns: ["interval-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["merge-intervals", "insert-interval", "meeting-rooms-ii", "minimum-number-of-arrows-to-burst-balloons", "partition-labels"]
status: "PUBLISHED"
orderIndex: 58
reasonForSelection: "Sort by end; greedy keep interval with earliest end"
---

# Problem Statement

Given an array of intervals `intervals` where `intervals[i] = [start, end]`, return the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

## Constraints

- 1 ≤ intervals.length ≤ 10⁵
- intervals[i].length == 2
- -5 × 10⁴ ≤ start < end ≤ 5 × 10⁴

## Examples

**Example 1:**
```
Input: intervals = [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explanation: [1,3] can be removed and the rest of the intervals are non-overlapping.
```

**Example 2:**
```
Input: intervals = [[1,2],[1,2],[1,2]]
Output: 2
Explanation: You need to remove two [1,2] to make the rest non-overlapping.
```

**Example 3:**
```
Input: intervals = [[1,2],[2,3]]
Output: 0
Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
```

## Variations

### Variation 1: Merge Intervals
**Name**: Merge Intervals

**What Changed**: Merge overlapping instead of removing.

**Original Approach Works**: Sort by start, merge overlapping.

**What Breaks**: Merge vs remove.

**New Constraint**: Return merged intervals.

**Algorithm Changes**: Sort by start, merge overlapping.

**Complexity Changes**: Time O(n log n), Space O(n).

### Variation 2: Minimum Number of Arrows to Burst Balloons
**Name**: Minimum Number of Arrows to Burst Balloons

**What Changed**: Shoot arrows at intervals.

**Original Approach Works**: Sort by end, greedy.

**What Breaks**: Arrows vs removals.

**New Constraint**: Return minimum arrows.

**Algorithm Changes**: Sort by end, arrow at end.

**Complexity Changes**: Time O(n log n), Space O(1).