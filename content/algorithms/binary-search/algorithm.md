---
id: "binary-search"
name: "Binary Search"
description: "Divide and conquer search on sorted data by repeatedly halving the search space."
step_by_step_reasoning: "1. Set left=0, right=n-1. 2. While left <= right: mid = (left+right)/2. 3. If target == arr[mid], return mid. 4. If target < arr[mid], right = mid-1. 5. If target > arr[mid], left = mid+1. 6. Return -1 if not found."
complexity_analysis: "Search space halved each iteration. Time: O(log n). Space: O(1) iterative, O(log n) recursive."
time_complexity: "O(log n)"
space_complexity: "O(1)"
time_reasoning: "Each iteration eliminates half the search space. log₂(n) iterations."
space_reasoning: "Only stores left, right, mid indices. Constant space."
status: "PUBLISHED"
---
# Binary Search

## Overview
Binary search efficiently finds an element in a sorted array by repeatedly dividing the search space in half.

## Algorithm Steps
1. Initialize left = 0, right = n-1
2. While left <= right:
   - mid = left + (right - left) / 2 (avoids overflow)
   - If target == arr[mid], return mid
   - If target < arr[mid], right = mid - 1
   - If target > arr[mid], left = mid + 1
3. Return -1 if not found

## Variants
- **Lower bound**: First element >= target
- **Upper bound**: First element > target
- **First/Last occurrence**: For duplicates
- **Binary search on answer**: Search value space, not array indices

## Complexity
- Time: O(log n)
- Space: O(1) iterative

## When to Use
- Searching in sorted arrays
- Finding boundaries (first/last occurrence)
- Optimization problems with monotonic property (binary search on answer)