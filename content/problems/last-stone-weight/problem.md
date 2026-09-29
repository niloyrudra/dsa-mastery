---
id: "last-stone-weight"
title: "Last Stone Weight"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/last-stone-weight/"
patterns: ["heap-fundamentals", "priority-queue-applications"]
algorithms: []
dataStructures: ["heap"]
templates: ["heap-template-ts"]
prerequisites: ["heap"]
relatedProblems: ["kth-largest-element-in-an-array", "kth-largest-element-in-a-stream", "minimum-cost-to-connect-all-points", "merge-k-sorted-lists"]
status: "PUBLISHED"
orderIndex: 36
reasonForSelection: "Max-heap; repeatedly extract two largest, push difference"
---

# Problem Statement

You are given an array of integers `stones` where `stones[i]` is the weight of the `i`th stone.

We are playing a game with the stones. On each turn, we choose the **heaviest two** stones and smash them together. Suppose the heaviest two stones have weights `x` and `y` with `x ≤ y`. The result of this smash is:

- If `x == y`, both stones are destroyed.
- If `x != y`, the stone of weight `x` is destroyed, and the stone of weight `y` has new weight `y - x`.

At the end of the game, there is at most one stone left.

Return the weight of the last remaining stone. If there are no stones left, return `0`.

## Constraints

- 1 ≤ stones.length ≤ 30
- 1 ≤ stones[i] ≤ 1000

## Examples

**Example 1:**
```
Input: stones = [2,7,4,1,8,1]
Output: 1
Explanation:
We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then,
we combine 4 and 2 to get 2 so the array converts to [2,1,1,1] then,
we combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of the last stone.
```

**Example 2:**
```
Input: stones = [1]
Output: 1
```

## Variations

### Variation 1: Kth Largest Element in an Array
**Name**: Kth Largest Element in an Array

**What Changed**: Find kth largest, not last stone.

**Original Approach Works**: Min-heap of size k.

**What Breaks**: Different objective.

**New Constraint**: Return kth largest.

**Algorithm Changes**: Min-heap of size k.

**Complexity Changes**: Time O(n log k), Space O(k).

### Variation 2: Minimum Cost to Connect All Points
**Name**: Minimum Cost to Connect All Points

**What Changed**: Connect all points with minimum cost (MST).

**Original Approach Works**: Prim's algorithm with min-heap.

**What Breaks**: Graph edges vs stones.

**New Constraint**: Return MST total cost.

**Algorithm Changes**: Prim's with min-heap of edges.

**Complexity Changes**: Time O(E log V), Space O(V).