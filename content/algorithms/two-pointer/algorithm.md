---
id: "two-pointer"
name: "Two Pointer"
category: "ARRAYS_STRINGS"
description: "Using two pointers (indices) that traverse a data structure in a coordinated manner to find pairs, triplets, or detect patterns in linear time."
timeComplexity: "O(n)"
spaceComplexity: "O(1)"
pseudocode: |
  function twoPointerOpposite(arr, target):
    left = 0
    right = arr.length - 1
    while left < right:
      sum = arr[left] + arr[right]
      if sum == target:
        return [left, right]
      else if sum < target:
        left++
      else:
        right--
    return null

  function twoPointerSameDirection(arr):
    slow = 0
    for fast in 0..arr.length-1:
      if condition(arr[fast]):
        swap(arr[slow], arr[fast])
        slow++
    return slow

  function fastSlow(head):
    slow = head
    fast = head
    while fast and fast.next:
      slow = slow.next
      fast = fast.next.next
      if slow == fast:
        return true  // cycle detected
    return false
---
# Two Pointer Algorithm

## Overview

The Two Pointer technique uses two indices that traverse a data structure (array, string, or linked list) in a coordinated manner. The pointers can move in opposite directions, same direction, or at different speeds, enabling linear-time solutions for problems that would naively require quadratic time.

## Variants

### 1. Opposite Ends (Converging Pointers)
- **Use case**: Sorted array, find pair meeting condition (sum, area, etc.)
- **Movement**: `left` starts at 0, `right` starts at n-1, they move toward each other
- **Invariant**: All pairs outside [left, right] have been evaluated and rejected

### 2. Same Direction (Fast-Slow for Array Processing)
- **Use case**: Remove duplicates, partition array, sliding window
- **Movement**: `slow` tracks the position of valid elements, `fast` scans ahead
- **Invariant**: Elements before `slow` satisfy the desired property

### 3. Fast-Slow (Linked List Cycle Detection)
- **Use case**: Detect cycles, find middle, remove nth from end
- **Movement**: `fast` moves 2 steps, `slow` moves 1 step
- **Invariant**: Fast pointer is always ahead; distance relates to problem property

## When to Use

- Sorted array + pair condition → opposite ends
- Palindrome / symmetry → opposite ends on string
- Find middle / cycle detection → fast-slow pointers
- Remove duplicates in-place → same direction
- Problems where answer depends on relationship between two positions

## Complexity

- **Time**: O(n) - each pointer moves at most n times
- **Space**: O(1) - only pointer variables

## Common Failure Modes

1. **Wrong pointer movement**: Moving left++ when sum > target (should move right--)
2. **Off-by-one**: `left <= right` vs `left < right` changes behavior
3. **Forgetting to sort**: Two-pointer on unsorted array gives wrong answer
4. **Not handling duplicates**: In 3Sum, must skip duplicates to avoid duplicate triplets
5. **Infinite loops**: Fast-slow cycle detection must check `fast && fast.next`

## Example Problems

- **Valid Palindrome**: Opposite ends on filtered string
- **Two Sum II (Sorted)**: Opposite ends, find sum = target
- **Container With Most Water**: Opposite ends, max area = min(h[left], h[right]) * width
- **Trapping Rain Water**: Opposite ends with max tracking
- **3Sum**: Sort + fixed element + two-pointer on remainder
- **Linked List Cycle**: Fast-slow pointers (Floyd's algorithm)
- **Find Middle**: Fast-slow, slow at middle when fast reaches end