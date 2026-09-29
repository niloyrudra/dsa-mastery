---
id: "unique-paths"
title: "Unique Paths"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/unique-paths/"
patterns: ["dp-2d-fundamentals", "dp-1d-fundamentals", "math-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming", "math-fundamentals"]
relatedProblems: ["minimum-path-sum", "unique-paths-ii", "unique-paths-iii", "number-of-paths-with-max-score", "minimum-moves-to-reach-target-score"]
status: "PUBLISHED"
orderIndex: 45
reasonForSelection: "2D DP on grid; combinatorial math: C(m+n-2, m-1)"
---

# Problem Statement

There is a robot on an `m x n` grid. The robot is initially located at the **top-left corner** (i.e., `grid[0][0]`). The robot tries to move to the **bottom-right corner** (i.e., `grid[m - 1][n - 1]`). The robot can only move either down or right at any point in time.

Given the two integers `m` and `n`, return the number of possible unique paths that the robot can take to reach the bottom-right corner.

## Constraints

- 1 ≤ m, n ≤ 100

## Examples

**Example 1:**
```
Input: m = 3, n = 7
Output: 28
```

**Example 2:**
```
Input: m = 3, n = 2
Output: 3
Explanation:
From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Right -> Down
2. Right -> Down -> Right
3. Down -> Right -> Right
```

## Variations

### Variation 1: Unique Paths II
**Name**: Unique Paths II

**What Changed**: Grid with obstacles (0 = free, 1 = obstacle).

**Original Approach Works**: Same DP, skip obstacles.

**What Breaks**: Obstacles block paths.

**New Constraint**: Return paths avoiding obstacles.

**Algorithm Changes**: dp[i][j] = 0 if obstacle, else dp[i-1][j] + dp[i][j-1].

**Complexity Changes**: Time O(m*n), Space O(n).

### Variation 2: Minimum Path Sum
**Name**: Minimum Path Sum

**What Changed**: Minimize sum instead of count paths.

**Original Approach Works**: 2D DP with min instead of add.

**What Breaks**: Min vs count.

**New Constraint**: Return minimum sum.

**Algorithm Changes**: dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1]).

**Complexity Changes**: Time O(m*n), Space O(n).