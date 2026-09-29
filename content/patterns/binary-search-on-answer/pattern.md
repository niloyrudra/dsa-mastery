---
id: "binary-search-on-answer"
name: "Binary Search on Answer"
category: "SEARCH"
patterns: []
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
problems: ["koko-eating-bananas", "capacity-to-ship-packages", "split-array-largest-sum", "minimum-number-of-arrows-to-burst-balloons", "cheapest-flights-within-k-stops"]
prerequisites: ["array", "binary-search"]
relatedPatterns: ["binary-search-boundary", "greedy-fundamentals"]
status: "PUBLISHED"
---
# Binary Search on Answer Pattern

## Definition
Using binary search on the answer space (range of possible values) rather than on array indices, when the answer has a monotonic property.

## When to Use
- Finding minimum/maximum value satisfying a condition
- Capacity allocation (Koko Eating Bananas)
- Splitting arrays
- Optimization problems with monotonic feasibility

## Why It Works
If a value x works, then all values >= x (or <= x) also work. This monotonicity allows binary search on the answer range.

## Intuition
Instead of searching for a target in an array, we search for the optimal answer value. The 'check' function determines if a candidate answer is feasible.

## Invariant
Search space [left, right] always contains the optimal answer; mid is feasible or not, narrowing the range.

## Strengths
- O(log(max-min) * check) time
- Transforms optimization into decision problem
- Wide applicability

## Weaknesses
- Requires monotonic feasibility
- Check function can be complex
- Off-by-one errors in boundaries
- Determining search bounds can be tricky

## Recognition Signals
- "minimum/maximum possible value"
- "minimize maximum"
- "maximize minimum"
- "capacity allocation"
- "split array"
- "koko eating bananas"
- "split array largest sum"
- "allocate books"

## Common Failure Modes
- Wrong search bounds (left/right)
- Incorrect mid calculation (overflow, off-by-one)
- Feasibility function not monotonic
- Infinite loop with while(left < right) vs while(left <= right)
- Not handling edge cases in check function

## Generalizations
Can be applied to double/float answers with epsilon; can search on 2D answer space; combines with greedy/dp for check function.

## Complexity
- Time: O(log(range) * check)
- Space: O(1) or O(check)