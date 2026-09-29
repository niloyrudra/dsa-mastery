---
id: "coin-change"
title: "Coin Change"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/coin-change/"
patterns: ["dp-1d-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["climbing-stairs", "coin-change-ii", "combination-sum-iv", "perfect-squares"]
status: "PUBLISHED"
orderIndex: 42
reasonForSelection: "Unbounded knapsack pattern; minimum coins for amount"
---

# Problem Statement

You are given an integer array `coins` representing coins of different denominations and an integer `amount` representing a total amount of money.

Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return `-1`.

You may assume that you have an infinite number of each kind of coin.

## Constraints

- 1 ≤ coins.length ≤ 12
- 1 ≤ coins[i] ≤ 2³¹ - 1
- 0 ≤ amount ≤ 10⁴

## Examples

**Example 1:**
```
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1
```

**Example 2:**
```
Input: coins = [2], amount = 3
Output: -1
```

**Example 3:**
```
Input: coins = [1], amount = 0
Output: 0
```

## Variations

### Variation 1: Coin Change II
**Name**: Coin Change II

**What Changed**: Count number of combinations to make amount (order doesn't matter).

**Original Approach Works**: DP with outer loop on coins, inner on amount.

**What Breaks**: Count combinations not minimum coins.

**New Constraint**: Return number of combinations.

**Algorithm Changes**: dp[0] = 1; for coin in coins: for i = coin to amount: dp[i] += dp[i-coin].

**Complexity Changes**: Time O(n*amount), Space O(amount).

### Variation 2: Combination Sum IV
**Name**: Combination Sum IV

**What Changed**: Order matters (permutations); can use elements multiple times.

**Original Approach Works**: DP with outer loop on amount, inner on nums.

**What Breaks**: Count permutations not combinations.

**New Constraint**: Different sequences counted separately.

**Algorithm Changes**: dp[0] = 1; for i = 1 to target: for num in nums: if i >= num: dp[i] += dp[i-num].

**Complexity Changes**: Time O(n*target), Space O(target).