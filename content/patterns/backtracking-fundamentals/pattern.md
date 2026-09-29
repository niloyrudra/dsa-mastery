---
id: "backtracking-fundamentals"
name: "Backtracking Fundamentals"
category: "BACKTRACKING"
patterns: []
algorithms: []
dataStructures: ["array"]
templates: []
problems: ["subsets", "permutations", "permutations-ii", "combination-sum", "combination-sum-ii", "combination-sum-iv", "generate-parentheses", "n-queens", "palindrome-partitioning", "word-search", "word-search-ii"]
prerequisites: ["array", "recursion"]
relatedPatterns: ["dp-1d-fundamentals", "dp-2d-fundamentals"]
status: "PUBLISHED"
---
# Backtracking Fundamentals Pattern

## Definition
Systematic exploration of all possible configurations via DFS with pruning; build solution incrementally, backtrack when constraints violated.

## When to Use
- Generating all subsets/permutations/combinations
- Solving constraint satisfaction (N-Queens, Sudoku)
- Word search
- Partitioning problems

## Why It Works
Explores state space tree recursively. Pruning eliminates invalid branches early. Each recursive call makes a choice, then un-makes it (backtracks).

## Intuition
At each step, try all valid options, recurse, then undo the choice. Track current state (path). Base case: complete solution or dead end.

## Invariant
Current path represents a partial solution; all constraints satisfied for current path; unused elements available for next choices.

## Strengths
- Finds all solutions
- Natural for combinatorial problems
- Pruning dramatically reduces search space
- Simple recursive structure

## Weaknesses
- Exponential time in worst case
- Stack overflow for deep recursion
- Duplicate solutions without deduplication
- Hard to optimize

## Recognition Signals
- "all subsets"
- "all permutations"
- "all combinations"
- "n-queens"
- "word search"
- "palindrome partitioning"
- "generate parentheses"
- "combination sum"
- "letter combinations of phone number"

## Common Failure Modes
- Not backtracking (forgetting to undo choice)
- Missing base case
- Duplicate solutions (not sorting + skipping duplicates)
- Inefficient constraint checking
- Stack overflow for large input
- Not pruning early enough

## Generalizations
Can use iterative with explicit stack; bitmask for subsets; memoization for overlapping subproblems; branch and bound for optimization.

## Complexity
- Time: Exponential O(k^n) typically, where k is choices per step, n is depth
- Space: O(n) recursion stack + O(k) for current path