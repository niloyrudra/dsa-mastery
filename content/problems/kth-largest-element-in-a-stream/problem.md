---
id: "kth-largest-element-in-a-stream"
title: "Kth Largest Element in a Stream"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/kth-largest-element-in-a-stream/"
patterns: ["heap-fundamentals", "priority-queue-applications"]
algorithms: []
dataStructures: ["heap"]
templates: ["heap-template-ts"]
prerequisites: ["heap"]
relatedProblems: ["kth-largest-element-in-an-array", "k-closest-points-to-origin", "top-k-frequent-elements", "median-finder"]
status: "PUBLISHED"
orderIndex: 35
reasonForSelection: "Min-heap for streaming kth largest; dynamic data structure"
---

# Problem Statement

Design a class to find the `k`th largest element in a stream. Note that it is the `k`th largest element in the sorted order, not the `k`th distinct element.

Implement `KthLargest` class:

- `KthLargest(int k, int[] nums)` Initializes the object with the integer `k` and the stream of integers `nums`.
- `int add(int val)` Appends the integer `val` to the stream and returns the element representing the `k`th largest element in the stream.

## Constraints

- 1 ≤ k ≤ 10⁴
- 0 ≤ nums.length ≤ 10⁴
- -10⁴ ≤ nums[i] ≤ 10⁴
- -10⁴ ≤ val ≤ 10⁴
- At most 10⁴ calls will be made to `add`.
- It is guaranteed that there will be at least `k` elements in the stream when you search for the `k`th largest.

## Examples

**Example 1:**
```
Input:
["KthLargest","add","add","add","add","add"]
[[3,[4,5,8,2]],[3],[5],[10],[9],[4]]
Output:
[null,4,5,5,8,8]
Explanation:
KthLargest kthLargest = new KthLargest(3, [4,5,8,2]);
kthLargest.add(3);   // return 4
kthLargest.add(5);   // return 5
kthLargest.add(10);  // return 5
kthLargest.add(9);   // return 8
kthLargest.add(4);   // return 8
```

## Variations

### Variation 1: Kth Largest Element in an Array
**Name**: Kth Largest Element in an Array

**What Changed**: Static array, not stream.

**Original Approach Works**: Min-heap of size k, build from array.

**What Breaks**: Static vs dynamic.

**New Constraint**: Return kth largest from static array.

**Algorithm Changes**: Build min-heap of size k from nums.

**Complexity Changes**: Time O(n log k), Space O(k).

### Variation 2: Median Finder
**Name**: Median Finder

**What Changed**: Find median of stream.

**Original Approach Works**: Dual heaps (max-heap + min-heap).

**What Breaks**: Median vs kth largest.

**New Constraint**: Return median.

**Algorithm Changes**: Balance two heaps, median from tops.

**Complexity Changes**: Time O(log n) add, O(1) find, Space O(n).