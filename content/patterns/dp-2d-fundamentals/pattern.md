---
id: "dp-2d-fundamentals"
name: "2D Dynamic Programming"
category: "DYNAMIC_PROGRAMMING"
patterns: []
algorithms: []
dataStructures: ["array"]
templates: []
problems: ["unique-paths", "unique-paths-ii", "minimum-path-sum", "maximal-square", "edit-distance", "longest-common-subsequence", "distinct-subsequences", "interleaving-string", "regular-expression-matching", "burst-balloons", "target-sum"]
prerequisites: ["array", "dynamic-programming"]
relatedPatterns: ["dp-1d-fundamentals", "greedy-fundamentals"]
status: "PUBLISHED"
---
# 2D Dynamic Programming Pattern

## Definition
DP with two-dimensional state: dp[i][j] represents optimal solution for subproblems involving two sequences or 2D grid. Common patterns: LCS, edit distance, grid paths, interval DP.

## When to Use
- Two strings comparison (LCS, edit distance)
- Grid path problems (unique paths, min path sum)
- Matrix problems (maximal square)
- Interval DP (burst balloons)
- Game theory on intervals

## Why It Works
Optimal substructure in 2D: solution for (i,j) depends on smaller subproblems (i-1,j), (i,j-1), (i-1,j-1). Overlapping subproblems in both dimensions.

## Intuition
Fill DP table row by row or diagonally. Base cases on boundaries. Transition combines results from adjacent cells.

## Invariant
After processing row i, all dp[i][*] are correct. Or: dp[i][j] depends only on already computed states.

## Strengths
- Solves complex string/grid problems in polynomial time
- Natural for pairwise sequence problems
- Table visualization helps debugging

## Weaknesses
- O(n*m) space can be large
- State transitions can be complex
- Hard to identify correct subproblems
- Space optimization trickier than 1D

## Recognition Signals
- "longest common subsequence"
- "edit distance"
- "unique paths"
- "minimum path sum"
- "maximal square"
- "burst balloons"
- "regular expression matching"
- "interleaving string"
- "distinct subsequences"
- "target sum"

## Common Failure Modes
- Wrong table dimensions
- Incorrect base cases (first row/column)
- Wrong transition dependencies
- Off-by-one in string indices vs dp indices
- Not handling empty string/grid
- Space optimization breaking diagonal dependencies

## Generalizations
Can optimize space to O(min(n,m)) for LCS/edit distance; diagonal order for interval DP; 3D DP for three strings; DP on trees (tree DP).

## Complexity
- Time: O(n * m) typically
- Space: O(n * m) or O(min(n,m)) with optimization