---
id: "jump-game"
title: "Jump Game"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/jump-game/"
patterns: ["greedy-fundamentals", "dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["jump-game-ii", "gas-station", "best-time-to-buy-and-sell-stock", "minimum-path-sum"]
status: "PUBLISHED"
orderIndex: 51
reasonForSelection: "Greedy reachability; O(n) greedy vs O(n^2) DP"
---

# Problem Statement

You are given an integer array `nums`. You are initially positioned at the array's **first index**, and each element in the array represents your maximum jump length at that position.

Return `true` if you can reach the last index, or `false` otherwise.

## Constraints

- 1 ≤ nums.length ≤ 10⁴
- 0 ≤ nums[i] ≤ 10⁵

## Examples

**Example 1:**
```
Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
```

**Example 2:**
```
Input: nums = [3,2,1,0,4]
Output: false
Explanation: You will always arrive at index 3 no matter what. Its maximum jump length is 0, which makes it impossible to reach the last index.
```

## Variations

### Variation 1: Jump Game II
**Name**: Jump Game II

**What Changed**: Find minimum jumps to reach end.

**Original Approach Works**: Greedy with current/next reach tracking.

**What Breaks**: Need minimum jumps, not just reachability.

**New Constraint**: Return minimum number of jumps.

**Algorithm Changes**: Track current end and farthest, increment jumps when i == curEnd.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Gas Station
**Name**: Gas Station

**What Changed**: Circular route with gas/cost.

**Original Approach Works**: Greedy total surplus check.

**What Breaks**: Circular dependency.

**New Constraint**: Return starting station index.

**Algorithm Changes**: Track total surplus, reset start when tank < 0.

**Complexity Changes**: Time O(n), Space O(1).