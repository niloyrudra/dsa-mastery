---
id: "top-k-frequent-elements"
title: "Top K Frequent Elements"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/top-k-frequent-elements/"
patterns: ["hash-map-lookup", "heap-fundamentals"]
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table", "heap"]
templates: ["hash-map-template-ts", "heap-template-ts"]
prerequisites: ["array", "hash-table", "heap"]
relatedProblems: ["kth-largest-element-in-an-array", "kth-largest-element-in-a-stream", "top-k-frequent-words"]
status: "PUBLISHED"
orderIndex: 5
reasonForSelection: "Combines frequency counting with heap for top-k; bucket sort alternative"
---

# Problem Statement

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements. You may return the answer in any order.

## Constraints

- 1 ≤ nums.length ≤ 10⁵
- -10⁴ ≤ nums[i] ≤ 10⁴
- k is in the range [1, number of unique elements in the array].
- It is **guaranteed** that the answer is unique.

## Examples

**Example 1:**
```
Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
```

**Example 2:**
```
Input: nums = [1], k = 1
Output: [1]
```

## Variations

### Variation 1: Top K Frequent Words
**Name**: Top K Frequent Words

**What Changed**: Input is array of strings; return top k frequent words sorted by frequency then lexicographically.

**Original Approach Works**: Same frequency counting + heap approach.

**What Breaks**: Need custom comparator for tie-breaking (lexicographic order for same frequency).

**New Constraint**: Return words sorted by frequency (desc) then lexicographically (asc).

**Algorithm Changes**: Yes - custom comparator in heap.

**Complexity Changes**: Time O(n log k), Space O(n).

### Variation 2: Kth Largest Element in an Array
**Name**: Kth Largest Element in an Array

**What Changed**: Find single kth largest element, not top k frequent.

**Original Approach Works**: Min-heap of size k or QuickSelect.

**What Breaks**: Different problem - finding kth largest value, not k most frequent elements.

**New Constraint**: Return single element.

**Algorithm Changes**: Yes - QuickSelect for O(n) average or min-heap.

**Complexity Changes**: Time O(n) average (QuickSelect) or O(n log k) (heap), Space O(1) or O(k).