---
id: "median-finder"
title: "Find Median from Data Stream"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/median-finder/"
patterns: ["priority-queue-applications", "heap-fundamentals"]
algorithms: []
dataStructures: ["heap"]
templates: ["heap-template-ts"]
prerequisites: ["heap"]
relatedProblems: ["kth-largest-element-in-a-stream", "top-k-frequent-elements", "sliding-window-median", "find-median-from-data-stream"]
status: "PUBLISHED"
orderIndex: 37
reasonForSelection: "Dual heap (max-heap + min-heap); balance sizes for median"
---

# Problem Statement

The **median** is the middle value in an ordered integer list. If the size of the list is even, there is no middle value, and the median is the mean of the two middle values.

Implement the MedianFinder class:

- `MedianFinder()` initializes the `MedianFinder` object.
- `void addNum(int num)` adds the integer `num` from the data stream to the data structure.
- `double findMedian()` returns the median of all elements so far. Answers within `10⁻⁵` of the actual answer will be accepted.

## Constraints

- -10⁵ ≤ num ≤ 10⁵
- There will be at least one element in the data structure before calling `findMedian`.
- At most 5 × 10⁴ calls will be made to `addNum` and `findMedian`.

## Examples

**Example 1:**
```
Input:
["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
[[],[1],[2],[],[3],[]]
Output:
[null,null,null,1.5,null,2.0]
Explanation:
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 ((1 + 2) / 2)
medianFinder.addNum(3);    // arr = [1, 2, 3]
medianFinder.findMedian(); // return 2.0
```

## Variations

### Variation 1: Kth Largest Element in a Stream
**Name**: Kth Largest Element in a Stream

**What Changed**: Find kth largest instead of median.

**Original Approach Works**: Min-heap of size k.

**What Breaks**: Single kth vs median (middle two).

**New Constraint**: Return kth largest.

**Algorithm Changes**: Min-heap of size k.

**Complexity Changes**: Time O(log k) add, O(1) find, Space O(k).

### Variation 2: Sliding Window Median
**Name**: Sliding Window Median

**What Changed**: Median of sliding window.

**Original Approach Works**: Dual heaps with lazy deletion.

**What Breaks**: Need to remove elements from heaps.

**New Constraint**: Return median array for each window.

**Algorithm Changes**: Dual heaps + hash map for lazy deletion.

**Complexity Changes**: Time O(n log k), Space O(k).