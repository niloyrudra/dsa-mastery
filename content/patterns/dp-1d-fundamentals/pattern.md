---
id: "dp-1d-fundamentals"
name: "1D Dynamic Programming"
category: "DYNAMIC_PROGRAMMING"
patterns: []
algorithms: []
dataStructures: ["array"]
templates: []
problems: ["climbing-stairs", "house-robber", "house-robber-ii", "coin-change", "word-break", "decode-ways", "jump-game", "jump-game-ii", "longest-increasing-subsequence", "maximum-subarray", "maximum-product-subarray"]
prerequisites: ["array", "dynamic-programming", "recursion"]
relatedPatterns: ["dp-2d-fundamentals", "greedy-fundamentals"]
status: "PUBLISHED"
---
# 1D Dynamic Programming Pattern

## Definition
DP with one-dimensional state: dp[i] represents optimal solution for prefix/subproblem ending at i. Transitions depend on previous states.

## When to Use
- Optimization on sequences
- Counting ways
- Decision problems
- Longest increasing subsequence
- House robber
- Coin change
- Word break
- Decode ways
- Jump game
- Climbing stairs

## Why It Works
Optimal substructure: optimal solution to problem contains optimal solutions to subproblems. Overlapping subproblems: same subproblems solved repeatedly.

## Intuition
Build solution bottom-up or top-down with memoization. State represents 'best we can do up to position i'. Transition: try all valid previous states.

## Invariant
dp[i] correctly represents optimal value for subproblem up to i after processing i.

## Strengths
- Polynomial time for many exponential problems
- Bottom-up avoids recursion overhead
- Space optimization often possible (rolling array)

## Weaknesses
- State definition can be tricky
- O(n) or O(n^2) space
- Not all problems have optimal substructure
- Identifying transitions is the hard part

## Recognition Signals
- "maximum/minimum"
- "number of ways"
- "can we reach"
- "longest increasing subsequence"
- "house robber"
- "coin change"
- "word break"
- "decode ways"
- "jump game"
- "climbing stairs"

## Common Failure Modes
- Wrong state definition
- Incorrect base cases
- Missing transitions
- Off-by-one in indices
- Not handling negative infinity for maximization
- Space optimization breaking dependencies

## Generalizations
Can optimize space to O(1) for some (rolling array); 2D DP for sequence pairs; digit DP for number properties; DP with bitmask for subsets.

## Complexity
- Time: O(n * k) where k is number of transitions per state
- Space: O(n) or O(1) with rolling array