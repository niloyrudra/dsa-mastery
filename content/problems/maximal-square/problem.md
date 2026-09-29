---
id: "maximal-square"
title: "Maximal Square"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/maximal-square/"
patterns: ["dp-2d-fundamentals", "geometry-fundamentals"]
algorithms: []
dataStructures: ["array"]
templates: []
prerequisites: ["array", "dynamic-programming"]
relatedProblems: ["minimum-path-sum", "maximal-rectangle", "largest-rectangle-in-histogram", "rotate-image"]
status: "PUBLISHED"
orderIndex: 46
reasonForSelection: "2D DP on matrix; dp[i][j] = side length of largest square ending at (i,j)"
---

# Problem Statement

Given an `m x n` binary matrix filled with `0`'s and `1`'s, find the largest square containing only `1`'s and return its area.

## Constraints

- 1 ≤ m, n ≤ 300
- `matrix[i][j]` is `'0'` or `'1'`.

## Examples

**Example 1:**
```
Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
Output: 4
```

**Example 2:**
```
Input: matrix = [["0","1"],["1","0"]]
Output: 1
```

**Example 3:**
```
Input: matrix = [["0"]]
Output: 0
```

## Variations

### Variation 1: Maximal Rectangle
**Name**: Maximal Rectangle

**What Changed**: Largest rectangle (not necessarily square) of 1's.

**Original Approach Works**: Histogram per row + largest rectangle in histogram.

**What Breaks**: Rectangle vs square.

**New Constraint**: Return area of largest rectangle.

**Algorithm Changes**: For each row, compute heights, then largest rectangle in histogram.

**Complexity Changes**: Time O(m*n), Space O(n).

### Variation 2: Largest Rectangle in Histogram
**Name**: Largest Rectangle in Histogram

**What Changed**: 1D histogram instead of 2D matrix.

**Original Approach Works**: Monotonic stack for max area.

**What Breaks**: 1D vs 2D.

**New Constraint**: Return max rectangle area in histogram.

**Algorithm Changes**: Monotonic increasing stack.

**Complexity Changes**: Time O(n), Space O(n).