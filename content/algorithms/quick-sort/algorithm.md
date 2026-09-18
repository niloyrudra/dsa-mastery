---
id: "quick-sort"
name: "Quick Sort"
description: "Divide and conquer sorting using pivot partitioning. Average O(n log n), worst O(n²)."
step_by_step_reasoning: "1. Choose pivot (random, first, last, median-of-three). 2. Partition: rearrange so elements < pivot are left, > pivot are right. 3. Recursively sort left and right partitions."
complexity_analysis: "Average case: balanced partitions give log n depth, O(n) per level = O(n log n). Worst case: always pick min/max as pivot, O(n²). Randomized pivot avoids worst case."
time_complexity: "O(n log n) avg, O(n²) worst"
space_complexity: "O(log n) avg"
time_reasoning: "Expected balanced partitions. Each level does O(n) partitioning work."
space_reasoning: "Recursion stack depth. Average log n, worst case n."
status: "PUBLISHED"
---
# Quick Sort

## Overview
Quick Sort is an efficient in-place sorting algorithm with average O(n log n) performance.

## Algorithm Steps
1. **Choose Pivot**: Select an element as pivot (random, median-of-three recommended)
2. **Partition**: Rearrange array so elements < pivot are on left, > pivot on right
3. **Recurse**: Apply to left and right partitions

## Key Insight
Partitioning is done in-place by maintaining two pointers and swapping. The pivot ends up in its final sorted position.

## Complexity
- Time: O(n log n) average, O(n²) worst case
- Space: O(log n) average (recursion stack)

## When to Use
- General purpose in-memory sorting
- When average-case performance matters more than worst-case
- When in-place sorting is required
- Can be optimized with insertion sort for small arrays