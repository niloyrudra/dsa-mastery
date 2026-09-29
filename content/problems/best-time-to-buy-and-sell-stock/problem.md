---
id: "best-time-to-buy-and-sell-stock"
title: "Best Time to Buy and Sell Stock"
difficulty: "EASY"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/best-time-to-buy-and-sell-stock/"
patterns: ["greedy-fundamentals", "dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["best-time-to-buy-and-sell-stock-ii", "best-time-to-buy-and-sell-stock-iii", "maximum-subarray", "maximum-profit-in-job-scheduling"]
status: "PUBLISHED"
orderIndex: 15
reasonForSelection: "Sliding window minimum tracking; maximum difference with forward scan"
---

# Problem Statement

You are given an array `prices` where `prices[i]` is the price of a given stock on the `i`th day.

You want to maximize your profit by choosing a **single day** to buy one stock and choosing a **different day in the future** to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return `0`.

## Constraints

- 1 ≤ prices.length ≤ 10⁵
- 0 ≤ prices[i] ≤ 10⁴

## Examples

**Example 1:**
```
Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
```

**Example 2:**
```
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.
```

## Variations

### Variation 1: Best Time to Buy and Sell Stock II
**Name**: Best Time to Buy and Sell Stock II

**What Changed**: Can make multiple transactions (buy/sell multiple times).

**Original Approach Works**: Greedy - sum all positive differences.

**What Breaks**: Single transaction assumption no longer holds.

**New Constraint**: Can complete as many transactions as you like.

**Algorithm Changes**: Yes - sum all positive price[i+1] - price[i].

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Best Time to Buy and Sell Stock III
**Name**: Best Time to Buy and Sell Stock III

**What Changed**: At most two transactions allowed.

**Original Approach Works**: DP with states for 0, 1, 2 transactions.

**What Breaks**: Need to track two separate buy/sell operations.

**New Constraint**: At most 2 transactions.

**Algorithm Changes**: DP with buy1, sell1, buy2, sell2 states.

**Complexity Changes**: Time O(n), Space O(1).