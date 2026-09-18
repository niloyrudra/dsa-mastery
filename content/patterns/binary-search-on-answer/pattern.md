---
id: "binary-search-on-answer"
name: "Binary Search on Answer"
category: "Search"
definition: "Using binary search on the answer space (range of possible values) rather than on array indices, when the answer has a monotonic property."
when_to_use: "Finding minimum/maximum value satisfying a condition; capacity allocation (Koko Eating Bananas); splitting arrays; optimization problems with monotonic feasibility."
why_it_works: "If a value x works, then all values >= x (or <= x) also work. This monotonicity allows binary search on the answer range."
intuition: "Instead of searching for a target in an array, we search for the optimal answer value. The 'check' function determines if a candidate answer is feasible."
invariant: "Search space [left, right] always contains the optimal answer; mid is feasible or not, narrowing the range."
strengths: "O(log(max-min) * check) time; transforms optimization into decision problem; wide applicability."
weaknesses: "Requires monotonic feasibility; check function can be complex; off-by-one errors in boundaries; determining search bounds can be tricky."
recognition_signals:
  - "minimum/maximum possible value"
  - "minimize maximum"
  - "maximize minimum"
  - "capacity allocation"
  - "split array"
  - "koko eating bananas"
  - "split array largest sum"
  - "allocate books"
common_failure_modes:
  - "Wrong search bounds (left/right)"
  - "Incorrect mid calculation (overflow, off-by-one)"
  - "Feasibility function not monotonic"
  - "Infinite loop with while(left < right) vs while(left <= right)"
  - "Not handling edge cases in check function"
generalizations: "Can be applied to double/float answers with epsilon; can search on 2D answer space; combines with greedy/dp for check function."
time_complexity: "O(log(range) * check)"
space_complexity: "O(1) or O(check)"
time_reasoning: "Binary search on answer range; each step calls check function."
space_reasoning: "Constant extra space besides check function."
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