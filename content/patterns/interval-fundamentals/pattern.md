---
id: "interval-fundamentals"
name: "Interval Fundamentals"
category: "ARRAYS_STRINGS"
patterns: []
algorithms: []
dataStructures: ["array", "heap"]
templates: ["heap-template-ts"]
problems: ["merge-intervals", "insert-interval", "meeting-rooms", "meeting-rooms-ii", "non-overlapping-intervals", "minimum-number-of-arrows-to-burst-balloons", "partition-labels", "interval-intersection"]
prerequisites: ["array", "heap"]
relatedPatterns: ["greedy-fundamentals", "heap-fundamentals"]
status: "PUBLISHED"
---
# Interval Fundamentals Pattern

## Definition
Operations on intervals: merging overlapping, finding intersections, inserting new intervals, checking overlaps, meeting rooms scheduling.

## When to Use
- Merging intervals
- Inserting intervals
- Meeting rooms
- Finding free time
- Interval intersection
- Removing covered intervals
- Partitioning labels
- Non-overlapping intervals

## Why It Works
Sort intervals by start time. Process sequentially: if current overlaps with previous, merge; otherwise add to result. Overlap condition: current.start <= previous.end.

## Intuition
After sorting, overlapping intervals are adjacent. Single pass merges all overlaps. For meeting rooms: track active meetings with min-heap of end times.

## Invariant
Result intervals are non-overlapping and sorted; all input intervals represented in result (possibly merged).

## Strengths
- O(n log n) from sorting
- O(n) single pass after sort
- Intuitive
- Handles all interval operations

## Weaknesses
- Requires sorting (can't do better than O(n log n) comparison-based)
- Merging loses individual interval identity
- Not suitable for dynamic interval sets

## Recognition Signals
- "merge intervals"
- "insert interval"
- "meeting rooms"
- "meeting rooms ii"
- "non-overlapping intervals"
- "interval intersection"
- "partition labels"
- "minimum arrows to burst balloons"
- "employee free time"

## Common Failure Modes
- Not sorting by start time
- Incorrect overlap condition (strict vs non-strict)
- Not handling contained intervals
- Forgetting to update end time when merging
- Meeting rooms: not using min-heap correctly

## Generalizations
Can use segment tree for dynamic intervals; sweep line for complex interval problems; interval tree for stabbing queries.

## Complexity
- Time: O(n log n) for sorting + O(n) for processing
- Space: O(n) for result, O(n) for heap in meeting rooms