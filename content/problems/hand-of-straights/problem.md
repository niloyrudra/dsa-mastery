---
id: "hand-of-straights"
title: "Hand of Straights"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/hand-of-straights/"
patterns: ["greedy-fundamentals", "heap-fundamentals", "hash-map-lookup"]
algorithms: []
dataStructures: ["array", "heap", "hash-table"]
templates: ["heap-template-ts", "hash-map-template-ts"]
prerequisites: ["array", "heap", "hash-table"]
relatedProblems: ["task-scheduler", "divide-array-in-sets-of-k-consecutive-numbers", "split-array-into-consecutive-subsequences"]
status: "PUBLISHED"
orderIndex: 51
reasonForSelection: "Greedy with min-heap or sorted map; group consecutive cards"
---

# Problem Statement

Alice has some number of cards and she wants to rearrange the cards into groups so that each group is of size `groupSize`, and consists of `groupSize` consecutive cards.

Given an integer array `hand` where `hand[i]` is the value written on the `i`th card and an integer `groupSize`, return `true` if she can rearrange the cards, or `false` otherwise.

## Constraints

- 1 ≤ hand.length ≤ 10⁵
- 0 ≤ hand[i] ≤ 10⁹
- 1 ≤ groupSize ≤ hand.length

## Examples

**Example 1:**
```
Input: hand = [1,2,3,6,2,3,4,7,8], groupSize = 3
Output: true
Explanation: Alice's hand can be rearranged as [1,2,3],[2,3,4],[6,7,8].
```

**Example 2:**
```
Input: hand = [1,2,3,4,5], groupSize = 4
Output: false
Explanation: Alice's hand cannot be rearranged into groups of 4.
```

## Variations

### Variation 1: Divide Array in Sets of K Consecutive Numbers
**Name**: Divide Array in Sets of K Consecutive Numbers

**What Changed**: Same problem, different name.

**Original Approach Works**: Same greedy with min-heap/sorted map.

**What Breaks**: N/A.

**New Constraint**: N/A.

**Algorithm Changes**: N/A.

**Complexity Changes**: N/A.

### Variation 2: Split Array into Consecutive Subsequences
**Name**: Split Array into Consecutive Subsequences

**What Changed**: Groups can be of varying lengths (≥ 3).

**Original Approach Works**: Greedy with hash map tracking available extensions.

**What Breaks**: Variable group sizes.

**New Constraint**: Each subsequence length ≥ 3.

**Algorithm Changes**: Hash map for end-value counts, extend if possible.

**Complexity Changes**: Time O(n log n), Space O(n).