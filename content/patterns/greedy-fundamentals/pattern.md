---
id: "greedy-fundamentals"
name: "Greedy Fundamentals"
category: "Greedy"
definition: "Making locally optimal choice at each step with the hope of finding global optimum. Works when problem has greedy choice property and optimal substructure."
when_to_use: "Interval scheduling; maximizing/minimizing with constraints; gas station; jump game; task scheduling; Huffman coding; minimum spanning tree (Kruskal/Prim); assigning cookies; boat rescue."
why_it_works: "Greedy choice property: locally optimal choice leads to globally optimal solution. Optimal substructure: optimal solution contains optimal solutions to subproblems. Exchange argument proves correctness."
intuition: "Sort by some criterion (end time, ratio, value). Make choice that seems best now. Prove no future choice can improve it."
invariant: "After each greedy choice, remaining problem is same type with smaller input; partial solution can be extended to optimal."
strengths: "O(n log n) typically (due to sorting); simple and intuitive; no DP table needed; often optimal for specific problem classes."
weaknesses: "Not always optimal (requires proof); counterexamples easy to find; hard to verify correctness; only works for specific problem structures."
recognition_signals:
  - "maximum/minimum number of"
  - "interval scheduling"
  - "greedy"
  - "jump game"
  - "gas station"
  - "partition labels"
  - "task scheduler"
  - "assign cookies"
  - "boat rescue"
  - "non-overlapping intervals"
common_failure_modes:
  - "Assuming greedy works without proof"
  - "Wrong sorting criterion"
  - "Not handling edge cases"
  - "Greedy choice not globally optimal"
  - "Confusing with DP (when greedy fails)"
generalizations: "Matroid theory characterizes greedy-solvable problems; can combine with binary search (greedy check); fractional vs 0/1 knapsack distinction."
time_complexity: "O(n log n) typically (sorting), sometimes O(n)"
space_complexity: "O(1) or O(n) for sorting"
time_reasoning: "Sorting dominates; single pass after sorting."
space_reasoning: "Sorting space; constant extra for greedy pass."
status: "PUBLISHED"
---
# Greedy Fundamentals Pattern

## Definition
Making locally optimal choice at each step with the hope of finding global optimum. Works when problem has greedy choice property and optimal substructure.

## When to Use
- Interval scheduling
- Maximizing/minimizing with constraints
- Gas station
- Jump game
- Task scheduling
- Huffman coding
- Minimum spanning tree (Kruskal/Prim)
- Assigning cookies
- Boat rescue

## Why It Works
Greedy choice property: locally optimal choice leads to globally optimal solution. Optimal substructure: optimal solution contains optimal solutions to subproblems. Exchange argument proves correctness.

## Intuition
Sort by some criterion (end time, ratio, value). Make choice that seems best now. Prove no future choice can improve it.

## Invariant
After each greedy choice, remaining problem is same type with smaller input; partial solution can be extended to optimal.

## Strengths
- O(n log n) typically (due to sorting)
- Simple and intuitive
- No DP table needed
- Often optimal for specific problem classes

## Weaknesses
- Not always optimal (requires proof)
- Counterexamples easy to find
- Hard to verify correctness
- Only works for specific problem structures

## Recognition Signals
- "maximum/minimum number of"
- "interval scheduling"
- "greedy"
- "jump game"
- "gas station"
- "partition labels"
- "task scheduler"
- "assign cookies"
- "boat rescue"
- "non-overlapping intervals"

## Common Failure Modes
- Assuming greedy works without proof
- Wrong sorting criterion
- Not handling edge cases
- Greedy choice not globally optimal
- Confusing with DP (when greedy fails)

## Generalizations
Matroid theory characterizes greedy-solvable problems; can combine with binary search (greedy check); fractional vs 0/1 knapsack distinction.

## Complexity
- Time: O(n log n) typically (sorting), sometimes O(n)
- Space: O(1) or O(n) for sorting