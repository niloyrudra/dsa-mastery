---
id: "climbing-stairs"
title: "Climbing Stairs"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/climbing-stairs/"
patterns: ["dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming", "recursion"]
relatedProblems: ["house-robber", "min-cost-climbing-stairs", "fibonacci-number", "coin-change"]
status: "PUBLISHED"
orderIndex: 40
reasonForSelection: "Simplest 1D DP; Fibonacci pattern; introduces DP state transition"
---

# Problem Statement

You are climbing a staircase. It takes `n` steps to reach the top.

Each time you can either climb `1` or `2` steps. In how many distinct ways can you climb to the top?

## Constraints

- 1 ≤ n ≤ 45

## Examples

**Example 1:**
```
Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
```

**Example 2:**
```
Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
```

## Variations

### Variation 1: Min Cost Climbing Stairs
**Name**: Min Cost Climbing Stairs

**What Changed**: Each step has a cost; minimize total cost to reach top.

**Original Approach Works**: Same DP, but dp[i] = cost[i] + min(dp[i-1], dp[i-2]).

**What Breaks**: Cost added, not just counting ways.

**New Constraint**: Can start at step 0 or 1; return min cost to reach top.

**Algorithm Changes**: dp[i] = cost[i] + min(dp[i-1], dp[i-2]), return min(dp[n-1], dp[n-2]).

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: House Robber
**Name**: House Robber

**What Changed**: Cannot rob adjacent houses; maximize sum.

**Original Approach Works**: Same DP recurrence, dp[i] = max(dp[i-1], dp[i-2] + nums[i]).

**What Breaks**: Maximization instead of counting.

**New Constraint**: Cannot select adjacent elements.

**Algorithm Changes**: dp[i] = max(dp[i-1], dp[i-2] + nums[i]).

**Complexity Changes**: Time O(n), Space O(1).