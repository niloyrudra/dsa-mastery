---
id: "house-robber"
title: "House Robber"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/house-robber/"
patterns: ["dp-1d-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["climbing-stairs", "house-robber-ii", "house-robber-iii", "coin-change", "delete-and-earn"]
status: "PUBLISHED"
orderIndex: 41
reasonForSelection: "Classic 1D DP with adjacency constraint; space optimization to O(1)"
---

# Problem Statement

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and **it will automatically contact the police if two adjacent houses were broken into on the same night**.

Given an integer array `nums` representing the amount of money of each house, return the maximum amount of money you can rob tonight **without alerting the police**.

## Constraints

- 1 ≤ nums.length ≤ 100
- 0 ≤ nums[i] ≤ 400

## Examples

**Example 1:**
```
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.
```

**Example 2:**
```
Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
Total amount you can rob = 2 + 9 + 1 = 12.
```

## Variations

### Variation 1: House Robber II
**Name**: House Robber II

**What Changed**: Houses form a circle (first and last are adjacent).

**Original Approach Works**: Run DP twice - exclude first, exclude last.

**What Breaks**: Circular adjacency constraint.

**New Constraint**: Cannot rob both first and last house.

**Algorithm Changes**: max(rob(nums[0..n-2]), rob(nums[1..n-1])).

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: House Robber III
**Name**: House Robber III

**What Changed**: Houses form a binary tree.

**Original Approach Works**: Tree DP with post-order (rob/not rob).

**What Breaks**: Tree structure vs linear array.

**New Constraint**: Cannot rob parent and child.

**Algorithm Changes**: Return [rob, not_rob] for each node.

**Complexity Changes**: Time O(n), Space O(h).

### Variation 3: Delete and Earn
**Name**: Delete and Earn

**What Changed**: Delete nums[i] and earn nums[i] points, but delete all nums[i]-1 and nums[i]+1.

**Original Approach Works**: Reduce to House Robber on frequency array.

**What Breaks**: Values mapped to indices.

**New Constraint**: Earn points by taking values, neighbors deleted.

**Algorithm Changes**: Frequency array, then House Robber DP.

**Complexity Changes**: Time O(n + max), Space O(max).