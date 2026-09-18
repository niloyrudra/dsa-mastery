---
id: "big-o-notation"
name: "Big O Notation"
description: "Mathematical notation describing limiting behavior of algorithm complexity. Upper bound on growth rate."
status: "PUBLISHED"
---
# Big O Notation

## Overview
Big O describes the worst-case time or space complexity of an algorithm as input size grows.

## Common Complexities (Fastest to Slowest)
| Notation | Name | Example |
|----------|------|---------|
| O(1) | Constant | Array access, hash lookup |
| O(log n) | Logarithmic | Binary search |
| O(n) | Linear | Array traversal |
| O(n log n) | Linearithmic | Merge sort, heap operations |
| O(n²) | Quadratic | Nested loops |
| O(2^n) | Exponential | Subsets, recursive fibonacci |
| O(n!) | Factorial | Permutations, TSP brute force |

## Rules
- **Drop constants**: O(2n) → O(n)
- **Drop lower terms**: O(n² + n) → O(n²)
- **Multiplicative**: O(n) * O(log n) = O(n log n)
- **Additive**: O(n) + O(log n) = O(n)

## Space Complexity
Same notation for memory usage. Includes:
- Input space
- Auxiliary space (extra)
- Call stack (recursion)

## When Analyzing
- **Best case**: Ω (omega) - lower bound
- **Average case**: Θ (theta) - tight bound
- **Worst case**: O (big O) - upper bound (most common)

## Interview Tip
Always state both time AND space complexity. Explain why.