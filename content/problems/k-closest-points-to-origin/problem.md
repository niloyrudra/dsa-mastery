---
id: "k-closest-points-to-origin"
title: "K Closest Points to Origin"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/k-closest-points-to-origin/"
patterns: ["heap-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["heap", "array"]
templates: ["heap-template-ts"]
prerequisites: ["heap", "array"]
relatedProblems: ["kth-largest-element-in-an-array", "kth-largest-element-in-a-stream", "top-k-frequent-elements", "minimum-cost-to-connect-all-points"]
status: "PUBLISHED"
orderIndex: 34
reasonForSelection: "Max-heap of size k; Euclidean distance squared for comparison"
---

# Problem Statement

Given an array of points where `points[i] = [x, y]` represents a point on the **X-Y** plane and an integer `k`, return the `k` closest points to the origin `(0, 0)`.

The distance between two points on the X-Y plane is the Euclidean distance (i.e., `√(x₁ - x₂)² + (y₁ - y₂)²`).

You may return the answer in any order. The answer is **guaranteed** to be unique (except for the order that it is in).

## Constraints

- 1 ≤ k ≤ points.length ≤ 10⁴
- -10⁴ < x, y < 10⁴

## Examples

**Example 1:**
```
Input: points = [[1,3],[-2,2]], k = 1
Output: [[-2,2]]
Explanation: The distance between (-2, 2) and the origin is √8 = 2.828.
The distance between (1, 3) and the origin is √10 = 3.162.
```

**Example 2:**
```
Input: points = [[3,3],[5,-1],[-2,4]], k = 2
Output: [[3,3],[-2,4]]
```

## Variations

### Variation 1: Kth Largest Element in an Array
**Name**: Kth Largest Element in an Array

**What Changed**: Find single kth largest element.

**Original Approach Works**: Min-heap of size k.

**What Breaks**: Single element vs k elements.

**New Constraint**: Return kth largest.

**Algorithm Changes**: Keep min-heap of size k, return top.

**Complexity Changes**: Time O(n log k), Space O(k).

### Variation 2: Top K Frequent Elements
**Name**: Top K Frequent Elements

**What Changed**: Frequency-based top k.

**Original Approach Works**: Frequency map + min-heap.

**What Breaks**: Frequency vs distance.

**New Constraint**: Return k most frequent elements.

**Algorithm Changes**: Count frequencies, heap on frequency.

**Complexity Changes**: Time O(n log k), Space O(n).