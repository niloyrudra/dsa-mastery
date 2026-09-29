---
id: "binary-search-boundary"
name: "Binary Search - Boundary / Lower Bound"
category: "SEARCH"
patterns: []
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
problems: ["search-insert-position", "first-bad-version", "find-first-and-last-position-of-element-in-sorted-array", "koko-eating-bananas", "capacity-to-ship-packages"]
prerequisites: ["array"]
relatedPatterns: []
status: "PUBLISHED"
---

# Binary Search - Boundary / Lower Bound Pattern

## Definition

Finding the boundary (first/last position) where a monotonic predicate changes value in a sorted search space. Unlike classic binary search which finds exact matches, boundary binary search finds the insertion point or transition point. This is the foundation for binary search on answer space.

## When to Use

- **Lower Bound**: First index where arr[i] >= target (insertion point)
- **Upper Bound**: First index where arr[i] > target
- **First True**: First index where predicate(i) is true in [false, false, ..., true, true]
- **Last True**: Last index where predicate(i) is true
- Search Insert Position, First Bad Version, Koko Eating Bananas
- Any problem where answer space is sorted and predicate is monotonic

## Why It Works

Binary search maintains the invariant that the answer lies in [left, right). The search space halves each iteration. For lower bound: if arr[mid] < target, answer is in (mid, right]; if arr[mid] >= target, answer is in [left, mid]. This correctly converges to the boundary.

## Recognition Signals

- **Structural**: "Sorted array + find position" → lower/upper bound
- **Structural**: "Insertion point" → lower bound
- **Structural**: "First/last occurrence" → lower/upper bound
- **Structural**: "First true in monotonic predicate" → binary search on predicate
- **Structural**: "Minimize/maximize value satisfying condition" → binary search on answer
- **Keywords**: "insertion point", "lower bound", "upper bound", "first bad", "first true", "boundary"

## Invariant

Answer is always in the search interval [left, right). The interval strictly shrinks each iteration.

## Strengths

- O(log n) time for finding boundaries
- Works on any monotonic predicate, not just array values
- Foundation for binary search on answer space
- Handles duplicates correctly (finds first/last)

## Weaknesses

- Requires sorted data or monotonic predicate
- Easy to get off-by-one errors (left < right vs left <= right)
- mid calculation must avoid overflow (left + (right - left) / 2)
- Off-by-one in boundary condition (right = mid vs right = mid - 1)

## Common Failure Modes

1. **Using `left <= right`**: Causes infinite loop or wrong answer for lower bound
2. **Setting `right = mid - 1`**: Excludes potential answer (mid could be the boundary)
3. **Not handling empty array**: `right = length` handles correctly, `right = length - 1` fails
4. **Wrong predicate direction**: For "first true", if `predicate(mid)` is true, search left; else search right
5. **Integer overflow**: Use `left + (right - left) / 2` not `(left + right) / 2`

## Generalizations

- **Binary Search on Answer**: Search space is answer values, predicate checks feasibility
- **Binary Search on Rotated Arrays**: Determine which half is sorted, then apply standard BS
- **Binary Search on 2D Matrix**: Treat matrix as 1D array or search row then column
- **Fractional Cascading**: Speed up multiple binary searches on related arrays

## Complexity

- Time: O(log n) - search space halves each iteration
- Space: O(1) - constant extra space

## Example Problems

- **Search Insert Position**: Lower bound in sorted array
- **First Bad Version**: First true in [false...false, true...true]
- **Find First and Last Position**: Lower bound and upper bound
- **Koko Eating Bananas**: Binary search on answer (eating speed)
- **Capacity To Ship Packages**: Binary search on answer (capacity)
- **Split Array Largest Sum**: Binary search on answer (max subarray sum)