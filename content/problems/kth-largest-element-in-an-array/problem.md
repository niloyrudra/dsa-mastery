---
id: "kth-largest-element-in-an-array"
title: "Kth Largest Element in an Array"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/kth-largest-element-in-an-array/"
patterns: ["heap-fundamentals", "binary-search-on-answer"]
algorithms: []
dataStructures: ["heap", "array"]
templates: ["heap-template-ts", "binary-search-template-ts"]
prerequisites: ["heap", "array"]
relatedProblems: ["kth-largest-element-in-a-stream", "k-closest-points-to-origin", "top-k-frequent-elements", "kth-smallest-element-in-a-bst"]
status: "PUBLISHED"
orderIndex: 35
reasonForSelection: "Min-heap of size k or QuickSelect; partial sorting"
---

# Problem Statement

Given an integer array `nums` and an integer `k`, return the `k`th largest element in the array.

Note that it is the `k`th largest element in the sorted order, not the `k`th distinct element.

## Constraints

- 1 ≤ k ≤ nums.length ≤ 10⁵
- -10⁴ ≤ nums[i] ≤ 10⁴

## Examples

**Example 1:**
```
Input: nums = [3,2,1,5,6,4], k = 2
Output: 5
```

**Example 2:**
```
Input: nums = [3,2,3,1,2,4,5,5,6], k = 4
Output: 4
```

## Variations

### Variation 1: Kth Largest Element in a Stream
**Name**: Kth Largest Element in a Stream

**What Changed**: Dynamic stream instead of static array.

**Original Approach Works**: Min-heap of size k.

**What Breaks**: Dynamic additions.

**New Constraint**: Support add() operations.

**Algorithm Changes**: Maintain min-heap, add val, pop if size > k.

**Complexity Changes**: Time O(log k) per add, Space O(k).

### Variation 2: Kth Smallest Element in a BST
**Name**: Kth Smallest Element in a BST

**What Changed**: BST instead of array; inorder traversal.

**Original Approach Works**: Inorder traversal gives sorted order.

**What Breaks**: Tree traversal vs array heap.

**New Constraint**: Return kth smallest in BST.

**Algorithm Changes**: Inorder traversal, stop at kth.

**Complexity Changes**: Time O(k) average, Space O(h).