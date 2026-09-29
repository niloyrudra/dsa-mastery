---
id: "median-of-two-sorted-arrays"
title: "Median of Two Sorted Arrays"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/median-of-two-sorted-arrays/"
patterns: ["binary-search-on-answer", "binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["find-first-and-last-position-of-element-in-sorted-array", "find-median-from-data-stream", "kth-smallest-element-in-a-bst", "search-in-rotated-sorted-array"]
status: "PUBLISHED"
orderIndex: 23
reasonForSelection: "Binary search on partition; O(log(min(m,n))) time"
---

# Problem Statement

Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return **the median** of the two sorted arrays.

The overall run time complexity should be `O(log (m+n))`.

## Constraints

- 0 ≤ nums1.length, nums2.length ≤ 1000
- 1 ≤ m + n ≤ 2000
- -10⁶ ≤ nums1[i], nums2[i] ≤ 10⁶

## Examples

**Example 1:**
```
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.
```

**Example 2:**
```
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
```

## Variations

### Variation 1: Find Median from Data Stream
**Name**: Find Median from Data Stream

**What Changed**: Dynamic stream instead of two static arrays.

**Original Approach Works**: Dual heaps for dynamic median.

**What Breaks**: Static arrays vs dynamic stream.

**New Constraint**: Support addNum and findMedian.

**Algorithm Changes**: Dual heaps (max-heap + min-heap).

**Complexity Changes**: Time O(log n) add, O(1) find, Space O(n).

### Variation 2: Kth Smallest Element in a BST
**Name**: Kth Smallest Element in a BST

**What Changed**: BST instead of sorted arrays.

**Original Approach Works**: Inorder traversal gives sorted order.

**What Breaks**: Tree traversal vs binary search on arrays.

**New Constraint**: Return kth smallest in BST.

**Algorithm Changes**: Inorder traversal with counter.

**Complexity Changes**: Time O(k), Space O(h).