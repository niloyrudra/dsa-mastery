---
id: "merge-sort"
name: "Merge Sort"
description: "Divide and conquer sorting algorithm that divides array into halves, sorts them recursively, and merges sorted halves."
step_by_step_reasoning: "1. Divide: Split array into two halves until single elements. 2. Conquer: Recursively sort each half. 3. Merge: Combine two sorted halves by comparing elements sequentially."
complexity_analysis: "Divides array into log n levels. Each level does O(n) work merging. Total: O(n log n) time. Requires O(n) auxiliary space for merging."
time_complexity: "O(n log n)"
space_complexity: "O(n)"
time_reasoning: "log n levels of recursion, each level processes all n elements during merge."
space_reasoning: "Auxiliary array of size n for merging; recursion stack O(log n)."
status: "PUBLISHED"
---
# Merge Sort

## Overview
Merge Sort is a classic divide-and-conquer sorting algorithm with guaranteed O(n log n) time complexity.

## Algorithm Steps
1. **Divide**: Split the array into two halves recursively until each subarray has one element
2. **Conquer**: Recursively sort each half
3. **Merge**: Combine two sorted halves by comparing elements from each

## Key Insight
The merge step is where the actual sorting happens. Since both halves are already sorted, we can merge them in O(n) time by always picking the smaller front element.

## Complexity
- Time: O(n log n) - guaranteed, regardless of input
- Space: O(n) - auxiliary array for merging

## When to Use
- When stable sort is required
- When guaranteed O(n log n) is needed
- External sorting (data doesn't fit in memory)
- Linked lists (can be done in O(1) space)