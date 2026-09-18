---
id: "dp-1d-fundamentals"
name: "1D Dynamic Programming"
category: "Dynamic Programming"
definition: "DP with one-dimensional state: dp[i] represents optimal solution for prefix/subproblem ending at i. Transitions depend on previous states."
when_to_use: "Optimization on sequences; counting ways; decision problems; longest increasing subsequence; house robber; coin change; word break; decode ways; jump game."
why_it_works: "Optimal substructure: optimal solution to problem contains optimal solutions to subproblems. Overlapping subproblems: same subproblems solved repeatedly."
intuition: "Build solution bottom-up or top-down with memoization. State represents 'best we can do up to position i'. Transition: try all valid previous states."
invariant: "dp[i] correctly represents optimal value for subproblem up to i after processing i."
strengths: "Polynomial time for many exponential problems; bottom-up avoids recursion overhead; space optimization often possible (rolling array)."
weaknesses: "State definition can be tricky; O(n) or O(n^2) space; not all problems have optimal substructure; identifying transitions is the hard part."
recognition_signals:
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
common_failure_modes:
  - "Wrong state definition"
  - "Incorrect base cases"
  - "Missing transitions"
  - "Off-by-one in indices"
  - "Not handling negative infinity for maximization"
  - "Space optimization breaking dependencies"
generalizations: "Can optimize space to O(1) for some (rolling array); 2D DP for sequence pairs; digit DP for number properties; DP with bitmask for subsets."
time_complexity: "O(n * k) where k is number of transitions per state"
space_complexity: "O(n) or O(1) with rolling array"
time_reasoning: "Each state computed once; each transition takes O(1) or O(k)."
space_reasoning: "DP array of size n; can reduce to constant if only depends on few previous states."
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