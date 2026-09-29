---
id: "minimum-path-sum"
title: "Minimum Path Sum"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/minimum-path-sum/"
patterns: ["dp-2d-fundamentals", "dp-1d-fundamentals", "greedy-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["unique-paths", "unique-paths-ii", "maximal-square", "climbing-stairs", "maximum-subarray", "minimum-cost-to-connect-all-points"]
status: "PUBLISHED"
orderIndex: 47
reasonForSelection: "2D DP on grid; right/down moves only"
---

# Problem Statement

Given a `m x n` grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

Note: You can only move either down or right at any point in time.

## Constraints

- m == grid.length
- n == grid[i].length
- 1 ≤ m, n ≤ 200
- 0 ≤ grid[i][j] ≤ 200

## Examples

**Example 1:**
```
Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: The path 1 → 3 → 1 → 1 → 1 minimizes the sum.
```

**Example 2:**
```
Input: grid = [[1,2,3],[4,5,6]]
Output: 12
```

## Variations

### Variation 1: Unique Paths
**Name**: Unique Paths

**What Changed**: Count paths instead of minimum sum.

**Original Approach Works**: Same 2D DP, but add instead of min.

**What Breaks**: Count vs min.

**New Constraint**: Return number of paths.

**Algorithm Changes**: dp[i][j] = dp[i-1][j] + dp[i][j-1].

**Complexity Changes**: Time O(m*n), Space O(n) or O(m*n).

### Variation 2: Maximum Subarray
**Name**: Maximum Subarray

**What Changed**: 1D array, maximum sum subarray.

**Original Approach Works**: Kadane's algorithm (1D DP).

**What Breaks**: 1D vs 2D grid.

**New Constraint**: Return maximum sum.

**Algorithm Changes**: Track max ending here, global max.

**Complexity Changes**: Time O(n), Space O(1).