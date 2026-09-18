---
id: "dp-2d-fundamentals"
name: "2D Dynamic Programming"
category: "Dynamic Programming"
definition: "DP with two-dimensional state: dp[i][j] represents optimal solution for subproblems involving two sequences or 2D grid. Common patterns: LCS, edit distance, grid paths, interval DP."
when_to_use: "Two strings comparison (LCS, edit distance); grid path problems (unique paths, min path sum); matrix problems (maximal square); interval DP (burst balloons); game theory on intervals."
why_it_works: "Optimal substructure in 2D: solution for (i,j) depends on smaller subproblems (i-1,j), (i,j-1), (i-1,j-1). Overlapping subproblems in both dimensions."
intuition: "Fill DP table row by row or diagonally. Base cases on boundaries. Transition combines results from adjacent cells."
invariant: "After processing row i, all dp[i][*] are correct. Or: dp[i][j] depends only on already computed states."
strengths: "Solves complex string/grid problems in polynomial time; natural for pairwise sequence problems; table visualization helps debugging."
weaknesses: "O(n*m) space can be large; state transitions can be complex; hard to identify correct subproblems; space optimization trickier than 1D."
recognition_signals:
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
common_failure_modes:
  - "Wrong table dimensions"
  - "Incorrect base cases (first row/column)"
  - "Wrong transition dependencies"
  - "Off-by-one in string indices vs dp indices"
  - "Not handling empty string/grid"
  - "Space optimization breaking diagonal dependencies"
generalizations: "Can optimize space to O(min(n,m)) for LCS/edit distance; diagonal order for interval DP; 3D DP for three strings; DP on trees (tree DP)."
time_complexity: "O(n * m) typically"
space_complexity: "O(n * m) or O(min(n,m)) with optimization"
time_reasoning: "Each cell computed once; constant transitions per cell."
space_reasoning: "2D table of size n*m; can reduce to 1D for some patterns."
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