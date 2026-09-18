---
id: "dynamic-programming"
name: "Dynamic Programming"
description: "Optimization technique for problems with optimal substructure and overlapping subproblems. Bottom-up or top-down with memoization."
status: "PUBLISHED"
---
# Dynamic Programming

## Overview
DP solves problems by breaking into overlapping subproblems, solving each once, storing results.

## Requirements
1. **Optimal Substructure**: Optimal solution contains optimal solutions to subproblems
2. **Overlapping Subproblems**: Same subproblems solved repeatedly

## Approaches
### Top-Down (Memoization)
```typescript
function dp(n) {
    if (memo[n] !== undefined) return memo[n];
    if (baseCase) return baseValue;
    memo[n] = combine(dp(n-1), dp(n-2));
    return memo[n];
}
```

### Bottom-Up (Tabulation)
```typescript
function dp(n) {
    dp[0] = base0;
    dp[1] = base1;
    for (i = 2; i <= n; i++) {
        dp[i] = combine(dp[i-1], dp[i-2]);
    }
    return dp[n];
}
```

## Space Optimization
If dp[i] only depends on few previous:
```typescript
// O(1) space
let prev2 = base0, prev1 = base1;
for (i = 2; i <= n; i++) {
    curr = combine(prev1, prev2);
    prev2 = prev1;
    prev1 = curr;
}
```

## Common Patterns
- **1D DP**: Fibonacci, House Robber, Coin Change, LIS
- **2D DP**: LCS, Edit Distance, Grid Paths, Knapsack
- **Interval DP**: Burst Balloons, Matrix Chain Multiplication
- **Tree DP**: Diameter, Max Path Sum, House Robber III
- **Bitmask DP**: TSP, Assignment, Subset DP
- **Digit DP**: Count numbers with property

## When to Use
- Optimization problems (min/max/count)
- Counting ways
- Decision problems (can we reach?)
- String/sequence problems

## Recognize DP
- "Maximum/Minimum..."
- "Number of ways..."
- "Can we...?"
- "Longest/Shortest..."
- Overlapping subproblems evident