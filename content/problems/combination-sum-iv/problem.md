---
id: "combination-sum-iv"
title: "Combination Sum IV"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/combination-sum-iv/"
patterns: ["dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["combination-sum", "coin-change", "climbing-stairs", "coin-change-ii"]
status: "PUBLISHED"
orderIndex: 44
reasonForSelection: "1D DP with unbounded items; order matters (permutation)"
---

# Problem Statement

Given an array of **distinct** integers `nums` and a target integer `target`, return the number of possible combinations that add up to `target`.

The test cases are generated so that the answer can fit in a 32-bit integer.

## Constraints

- 1 ≤ nums.length ≤ 200
- 1 ≤ nums[i] ≤ 1000
- All the elements of `nums` are **unique**.
- 1 ≤ target ≤ 1000

## Examples

**Example 1:**
```
Input: nums = [1,2,3], target = 4
Output: 7
Explanation:
The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)
Note that different sequences are counted as different combinations.
```

**Example 2:**
```
Input: nums = [9], target = 3
Output: 0
```

## Variations

### Variation 1: Combination Sum
**Name**: Combination Sum

**What Changed**: Return actual combinations (not count); order doesn't matter.

**Original Approach Works**: Backtracking with pruning.

**What Breaks**: Must generate actual combinations, not just count.

**New Constraint**: Return list of combinations, no duplicates.

**Algorithm Changes**: Backtracking with path tracking.

**Complexity Changes**: Time O(N^(target/min)), Space O(target).

### Variation 2: Coin Change II
**Name**: Coin Change II

**What Changed**: Count combinations (order doesn't matter); coins can repeat.

**Original Approach Works**: DP with outer loop on coins.

**What Breaks**: Different DP loop order for combinations vs permutations.

**New Constraint**: Return number of combinations.

**Algorithm Changes**: Outer loop on coins, inner on amount.

**Complexity Changes**: Time O(n*amount), Space O(amount).