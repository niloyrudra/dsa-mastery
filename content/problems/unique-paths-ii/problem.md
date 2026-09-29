---
id: "unique-paths-ii"
title: "Unique Paths II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/unique-paths-ii/"
patterns: ["dp-2d-fundamentals", "dp-1d-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["unique-paths", "unique-paths-iii", "minimum-path-sum", "unique-binary-search-trees", "different-ways-to-add-parentheses"]
status: "PUBLISHED"
orderIndex: 46
reasonForSelection: "2D DP with obstacles; dp[i][j] = 0 if obstacle"
---

# Problem Statement

You are given an `m x n` integer array `grid` where `grid[i][j]` is `1` (obstacle) or `0` (empty space).

The robot is initially located at the **top-left corner** (i.e., `grid[0][0]`). The robot tries to move to the **bottom-right corner** (i.e., `grid[m - 1][n - 1]`). The robot can only move either down or right at any point in time.

An obstacle and space are marked as `1` or `0` respectively in `grid`. A path that the robot takes cannot include **any** square that is an obstacle.

Return the number of possible unique paths that the robot can take to reach the bottom-right corner.

## Constraints

- m == grid.length
- n == grid[i].length
- 1 ≤ m, n ≤ 100
- grid[i][j] is `0` or `1`.

## Examples

**Example 1:**
```
Input: grid = [[0,0,0],[0,1,0],[0,0,0]]
Output: 2
```

**Example 2:**
```
Input: grid = [[0,1],[0,0]]
Output: 1
```

## Variations

### Variation 1: Unique Paths
**Name**: Unique Paths

**What Changed**: No obstacles.

**Original Approach Works**: Same DP without obstacle check.

**What Breaks**: Simpler.

**New Constraint**: No obstacles.

**Algorithm Changes**: No obstacle check.

**Complexity Changes**: Time O(m*n), Space O(n).

### Variation 2: Unique Paths III
**Name**: Unique Paths III

**What Changed**: Must visit all non-obstacle squares exactly once.

**Original Approach Works**: Backtracking with visited tracking.

**What Breaks**: Must visit all empty cells.

**New Constraint**: Visit all empty cells exactly once.

**Algorithm Changes**: Backtracking with count of empty cells.

**Complexity Changes**: Time O(4^(m*n)), Space O(m*n).