---
id: "greedy-fundamentals"
name: "Greedy Fundamentals"
category: "GREEDY"
patterns: []
algorithms: ["kruskal", "prim"]
dataStructures: ["array", "heap", "queue"]
templates: ["heap-template-ts"]
problems: ["jump-game", "jump-game-ii", "gas-station", "task-scheduler", "non-overlapping-intervals", "merge-intervals", "insert-interval", "minimum-number-of-arrows-to-burst-balloons", "partition-labels", "hand-of-straights", "best-time-to-buy-and-sell-stock"]
prerequisites: ["array", "heap", "queue"]
relatedPatterns: ["dp-1d-fundamentals", "dp-2d-fundamentals", "binary-search-on-answer", "graph-union-find"]
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