---
id: "partition-labels"
title: "Partition Labels"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/partition-labels/"
patterns: ["greedy-fundamentals", "interval-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["merge-intervals", "non-overlapping-intervals", "insert-interval", "meeting-rooms-ii", "minimum-number-of-arrows-to-burst-balloons"]
status: "PUBLISHED"
orderIndex: 51
reasonForSelection: "Greedy last occurrence; extend partition to max last index"
---

# Problem Statement

You are given a string `s`. We want to partition the string into as many parts as possible so that each letter appears in at most one part.

Note that the partition is done so that after concatenating all the parts in order, the resultant string is `s`.

Return a list of integers representing the size of these parts.

## Constraints

- 1 ≤ s.length ≤ 500
- `s` consists of lowercase English letters.

## Examples

**Example 1:**
```
Input: s = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation: The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect because it splits 'a' and 'b' into two parts.
```

**Example 2:**
```
Input: s = "eccbbbbdec"
Output: [10]
```

## Variations

### Variation 1: Merge Intervals
**Name**: Merge Intervals

**What Changed**: Merge overlapping intervals.

**Original Approach Works**: Sort by start, merge overlapping.

**What Breaks**: Intervals vs character partitions.

**New Constraint**: Return merged intervals.

**Algorithm Changes**: Sort by start, merge overlapping.

**Complexity Changes**: Time O(n log n), Space O(n).

### Variation 2: Non-overlapping Intervals
**Name**: Non-overlapping Intervals

**What Changed**: Remove minimum intervals.

**Original Approach Works**: Sort by end, greedy.

**What Breaks**: Remove vs partition.

**New Constraint**: Return minimum removals.

**Algorithm Changes**: Sort by end, greedy keep earliest end.

**Complexity Changes**: Time O(n log n), Space O(1).