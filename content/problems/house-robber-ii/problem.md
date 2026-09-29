---
id: "house-robber-ii"
title: "House Robber II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/house-robber-ii/"
patterns: ["dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["house-robber", "house-robber-iii", "delete-and-earn", "coin-change"]
status: "PUBLISHED"
orderIndex: 42
reasonForSelection: "Circular array; max of two subproblems (exclude first or last)"
---

# Problem Statement

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are **arranged in a circle**. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have a security system connected, and **it will automatically contact the police if two adjacent houses were broken into on the same night**.

Given an integer array `nums` representing the amount of money of each house, return the maximum amount of money you can rob tonight **without alerting the police**.

## Constraints

- 1 ≤ nums.length ≤ 100
- 0 ≤ nums[i] ≤ 1000

## Examples

**Example 1:**
```
Input: nums = [2,3,2]
Output: 3
Explanation: You cannot rob house 1 (money = 2) and house 3 (money = 2) because they are adjacent.
```

**Example 2:**
```
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.
```

## Variations

### Variation 1: House Robber
**Name**: House Robber

**What Changed**: Linear array (not circular).

**Original Approach Works**: Same DP, but no circular constraint.

**What Breaks**: Simpler - no need to run twice.

**New Constraint**: Linear array.

**Algorithm Changes**: Single DP pass.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: House Robber III
**Name**: House Robber III

**What Changed**: Houses form a binary tree.

**Original Approach Works**: Tree DP with post-order.

**What Breaks**: Tree structure vs array.

**New Constraint**: Cannot rob parent and child.

**Algorithm Changes**: Return [rob, not_rob] for each node.

**Complexity Changes**: Time O(n), Space O(h).