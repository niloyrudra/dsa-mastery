---
id: "combination-sum-ii"
title: "Combination Sum II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/combination-sum-ii/"
patterns: ["backtracking-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion"]
relatedProblems: ["combination-sum", "combination-sum-iii", "subsets-ii"]
status: "PUBLISHED"
orderIndex: 58
reasonForSelection: "Backtracking with duplicate skipping; each candidate used once"
---

# Problem Statement

Given a collection of candidate numbers (`candidates`) and a target number (`target`), find all unique combinations in `candidates` where the candidate numbers sum to `target`.

Each number in `candidates` may only be used **once** in the combination.

Note: The solution set must not contain duplicate combinations.

## Constraints

- 1 ≤ candidates.length ≤ 100
- 1 ≤ candidates[i] ≤ 50
- 1 ≤ target ≤ 30

## Examples

**Example 1:**
```
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: [[1,1,6],[1,2,5],[1,7],[2,6]]
```

**Example 2:**
```
Input: candidates = [2,5,2,1,2], target = 5
Output: [[1,2,2],[5]]
```

## Variations

### Variation 1: Combination Sum
**Name**: Combination Sum

**What Changed**: Candidates are distinct; each can be used unlimited times.

**Original Approach Works**: Backtracking without duplicate skipping; start from same index.

**What Breaks**: Can reuse same element multiple times.

**New Constraint**: Unlimited use of each distinct candidate.

**Algorithm Changes**: Start next recursion from same index i; no duplicate skipping needed.

**Complexity Changes**: Time O(N^(target/min)), Space O(target).

### Variation 2: Subsets II
**Name**: Subsets II

**What Changed**: Generate all subsets (not target sum); handle duplicates.

**Original Approach Works**: Backtracking with duplicate skipping.

**What Breaks**: No target sum constraint; collect all subsets.

**New Constraint**: Return all unique subsets.

**Algorithm Changes**: Collect path at each step; same duplicate skip logic.

**Complexity Changes**: Time O(2ⁿ), Space O(n).