---
id: "two-pointer"
name: "Two Pointer"
category: "ARRAYS_STRINGS"
patterns: ["two-pointer-opposite", "two-pointer-same", "fast-slow"]
algorithms: ["two-pointer-opposite", "two-pointer-same", "fast-slow"]
dataStructures: ["array"]
templates: ["two-pointer-opposite", "two-pointer-same", "fast-slow"]
problems: ["valid-palindrome", "two-sum-ii", "container-with-most-water", "trapping-rain-water", "3sum", "4sum"]
prerequisites: ["arrays"]
relatedPatterns: ["sliding-window", "binary-search"]
status: "PUBLISHED"
---

# Two Pointer Pattern

## Definition

Using two pointers (indices) that traverse a data structure (typically array or string) in a coordinated manner. The pointers can move in opposite directions, same direction, or at different speeds, enabling linear-time solutions for problems that would naively require quadratic time.

## When to Use

- **Opposite Ends**: Sorted array, find pair meeting condition (sum, area, etc.)
- **Same Direction**: Remove duplicates, partition array, sliding window
- **Fast-Slow**: Detect cycles (linked list), find middle, remove nth from end
- String/array symmetry problems (palindromes)
- Problems where answer depends on relationship between two positions

## Why It Works

The coordinated movement of pointers eliminates impossible candidates systematically. For opposite ends on sorted data: if sum is too small, moving left pointer right increases sum; if too large, moving right pointer left decreases sum. Each pointer moves monotonically, guaranteeing O(n) total steps.

## Recognition Signals

- **Structural**: "Sorted array + pair condition" → opposite ends
- **Structural**: "Palindrome / symmetry" → opposite ends on string
- **Structural**: "Find middle / cycle detection" → fast-slow pointers
- **Structural**: "Remove duplicates in-place" → same direction (slow tracks unique, fast scans)
- **Keywords**: "two pointers", "left right", "fast slow", "opposite ends", "converge"

## Invariant

**Opposite Ends**: All pairs outside [left, right] have been evaluated and rejected.
**Same Direction**: Elements before slow pointer satisfy the desired property (unique, partitioned, etc.).
**Fast-Slow**: Fast pointer is always ahead; distance between them relates to problem property (cycle length, middle, etc.).

## Strengths

- O(n) time, O(1) space for most variants
- Eliminates nested loops elegantly
- Works on arrays, strings, linked lists
- Natural fit for sorted data and symmetry problems

## Weaknesses

- Requires sorted data for opposite-ends variant (or loses original indices)
- Not applicable when relationship isn't monotonic
- Fast-slow requires specific structure (linked list cycle, etc.)
- Easy to get pointer movement logic wrong

## Common Failure Modes

1. **Wrong pointer movement**: Moving left++ when sum > target (should move right--)
2. **Off-by-one**: `left <= right` vs `left < right` changes behavior
3. **Forgetting to sort**: Two-pointer on unsorted array gives wrong answer
4. **Not handling duplicates**: In 3Sum, must skip duplicates to avoid duplicate triplets
5. **Infinite loops**: Fast-slow cycle detection must check `fast && fast.next`

## Generalizations

- **Three Pointers**: Dutch National Flag (0,1,2 sort), 3Sum
- **K Pointers**: Generalize to k-sum (recursive or iterative)
- **Sliding Window**: Two pointers with dynamic window size
- **Binary Search**: Can be viewed as two-pointer with logarithmic movement

## Complexity

- Time: O(n) - each pointer moves at most n times
- Space: O(1) - only pointer variables

## Example Problems

- **Valid Palindrome**: Opposite ends on filtered string
- **Two Sum II (Sorted)**: Opposite ends, find sum = target
- **Container With Most Water**: Opposite ends, max area = min(h[left], h[right]) * width
- **Trapping Rain Water**: Opposite ends with max tracking, or prefix/suffix max
- **3Sum**: Sort + fixed element + two-pointer on remainder
- **Linked List Cycle**: Fast-slow pointers (Floyd's algorithm)
- **Find Middle**: Fast-slow, slow at middle when fast reaches end