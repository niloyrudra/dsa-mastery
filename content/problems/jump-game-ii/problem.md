---
id: "jump-game-ii"
title: "Jump Game II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/jump-game-ii/"
patterns: ["greedy-fundamentals", "dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array"]
relatedProblems: ["jump-game", "gas-station", "best-time-to-buy-and-sell-stock", "minimum-path-sum"]
status: "PUBLISHED"
orderIndex: 52
reasonForSelection: "Greedy farthest reach; O(n) vs O(n^2) DP"
---

# Problem Statement

You are given a **0-indexed** array of integers `nums` of length `n`. You are initially positioned at `nums[0]`.

Each element `nums[i]` represents the maximum length of a forward jump from index `i`. In other words, if you are at `nums[i]`, you can jump to any `nums[i + j]` where:

- `0 ≤ j ≤ nums[i]`
- `i + j < n`

Return the minimum number of jumps to reach `nums[n - 1]`. The test cases are generated such that you can reach `nums[n - 1]`.

## Constraints

- 1 ≤ nums.length ≤ 10⁴
- 0 ≤ nums[i] ≤ 1000
- It's guaranteed that you can reach `nums[n - 1]`.

## Examples

**Example 1:**
```
Input: nums = [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2.
Jump 1 step from index 0 to 1, then 3 steps to the last index.
```

**Example 2:**
```
Input: nums = [2,3,0,1,4]
Output: 2
```

## Variations

### Variation 1: Jump Game
**Name**: Jump Game

**What Changed**: Return boolean if reachable, not minimum jumps.

**Original Approach Works**: Greedy reachability check.

**What Breaks**: Boolean vs minimum count.

**New Constraint**: Return true/false.

**Algorithm Changes**: Track maxReach, return maxReach >= n-1.

**Complexity Changes**: Time O(n), Space O(1).

### Variation 2: Minimum Jumps to Reach End (DP)
**Name**: Minimum Jumps to Reach End (DP version)

**What Changed**: DP approach O(n²).

**Original Approach Works**: dp[i] = min jumps to reach i.

**What Breaks**: O(n²) vs O(n) greedy.

**New Constraint**: Use DP for learning.

**Algorithm Changes**: dp[i] = min(dp[j] + 1) for all j < i where j + nums[j] >= i.

**Complexity Changes**: Time O(n²), Space O(n).