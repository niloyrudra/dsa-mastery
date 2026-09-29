---
id: "gas-station"
title: "Gas Station"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/gas-station/"
patterns: ["greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["maximum-subarray", "best-time-to-buy-and-sell-stock", "jump-game", "can-complete-circuit"]
status: "PUBLISHED"
orderIndex: 50
reasonForSelection: "Greedy total surplus check; O(n) single pass"
---

# Problem Statement

There are `n` gas stations along a circular route, where the amount of gas at the `i`th station is `gas[i]`.

You have a car with an unlimited gas tank and it costs `cost[i]` of gas to travel from the `i`th station to its next `(i + 1)`th station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays `gas` and `cost`, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return `-1`.

If there exists a solution, it is **guaranteed** to be **unique**.

## Constraints

- n == gas.length == cost.length
- 1 ≤ n ≤ 10⁵
- 0 ≤ gas[i], cost[i] ≤ 10⁴

## Examples

**Example 1:**
```
Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The tank is 5 - 5 + 4 = 4 (back to start)
```

**Example 2:**
```
Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation: You can't travel around the circuit.
```

## Variations

### Variation 1: Can Complete Circuit
**Name**: Can Complete Circuit (same problem, different phrasing)

**What Changed**: Same problem.

**Original Approach Works**: Same greedy algorithm.

**What Breaks**: N/A.

**New Constraint**: N/A.

**Algorithm Changes**: N/A.

**Complexity Changes**: N/A.

### Variation 2: Best Time to Buy and Sell Stock
**Name**: Best Time to Buy and Sell Stock

**What Changed**: Max profit from single buy/sell.

**Original Approach Works**: Track min price and max difference.

**What Breaks**: Different greedy criteria.

**New Constraint**: Return max profit.

**Algorithm Changes**: Track min price, update max profit.

**Complexity Changes**: Time O(n), Space O(1).