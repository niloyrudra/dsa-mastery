---
id: "recursion"
name: "Recursion"
description: "Function that calls itself to solve smaller instances of the same problem. Requires base case and recursive case."
status: "PUBLISHED"
---
# Recursion

## Overview
Recursion solves problems by breaking them into smaller identical subproblems.

## Structure
1. **Base Case**: Simplest input, returns directly (stops recursion)
2. **Recursive Case**: Reduce problem size, call self, combine results

## Example: Factorial
```typescript
function factorial(n: number): number {
    if (n <= 1) return 1;        // Base case
    return n * factorial(n - 1); // Recursive case
}
```

## Call Stack
Each call creates stack frame with local variables. Stack depth = recursion depth.
- Space: O(depth)
- Stack overflow if too deep

## Tail Recursion
Last operation is recursive call. Can be optimized to iteration (TCO).
```typescript
function factorial(n: number, acc = 1): number {
    if (n <= 1) return acc;
    return factorial(n - 1, n * acc); // Tail call
}
```

## Common Patterns
- **Divide & Conquer**: Split, solve parts, combine (Merge Sort, Quick Sort)
- **Backtracking**: Try, recurse, undo (N-Queens, Subsets)
- **Tree/Graph Traversal**: Visit node, recurse on children
- **Memoization**: Cache results (DP top-down)

## When to Use
- Natural recursive structure (trees, graphs)
- Divide & conquer algorithms
- Backtracking/exhaustive search
- When iterative version is complex

## When to Avoid
- Simple iteration works (factorial, sum)
- Very deep recursion (stack overflow)
- Performance critical (overhead)