---
id: "combination-sum"
title: "Combination Sum"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/combination-sum/"
patterns: ["backtracking-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "recursion"]
relatedProblems: ["subsets", "combination-sum-ii", "combination-sum-iii", "combination-sum-iv", "coin-change"]
status: "PUBLISHED"
orderIndex: 56
reasonForSelection: "Backtracking with unbounded candidates; classic combination generation"
---

# Problem Statement

Given an array of **distinct** integers `candidates` and a target integer `target`, return a list of all **unique combinations** of `candidates` where the chosen numbers sum to `target`. You may return the combinations in any order.

The **same** number may be chosen from `candidates` an **unlimited number of times**. Two combinations are unique if the frequency of at least one of the chosen numbers is different.

The test cases are generated such that the number of unique combinations that sum up to `target` is less than 150 combinations for the given input.

## Constraints

- 1 ≤ candidates.length ≤ 30
- 2 ≤ candidates[i] ≤ 40
- All elements of `candidates` are distinct.
- 1 ≤ target ≤ 40

## Examples

**Example 1:**
```
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.
```

**Example 2:**
```
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
```

**Example 3:**
```
Input: candidates = [2], target = 1
Output: []
```

## Variations

### Variation 1: Combination Sum II
**Name**: Combination Sum II

**What Changed**: Each candidate can be used at most once; candidates may have duplicates.

**Original Approach Works**: Backtracking with index to avoid reuse; sort and skip duplicates.

**What Breaks**: Cannot reuse same index; must handle duplicate candidates.

**New Constraint**: Each candidate used at most once.

**Algorithm Changes**: Start next recursion from i+1; skip duplicates: if i > start && candidates[i] == candidates[i-1] continue.

**Complexity Changes**: Time O(2ⁿ), Space O(target).

### Variation 2: Combination Sum III
**Name**: Combination Sum III

**What Changed**: Find combinations of k numbers from 1-9 that sum to n.

**Original Approach Works**: Backtracking with numbers 1-9, track count.

**What Breaks**: Fixed set of candidates (1-9), exactly k numbers.

**New Constraint**: Use numbers 1-9 exactly k times.

**Algorithm Changes**: candidates = [1..9]; stop when path.length == k.

**Complexity Changes**: Time O(C(9,k)), Space O(k).