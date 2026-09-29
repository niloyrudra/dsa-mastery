---
id: "monotonic-stack"
name: "Monotonic Stack"
category: "STACK"
patterns: []
algorithms: []
dataStructures: ["stack", "array"]
templates: []
problems: ["daily-temperatures", "largest-rectangle-in-histogram", "trapping-rain-water", "next-greater-element"]
prerequisites: ["stack", "array"]
relatedPatterns: ["sliding-window-fixed", "sliding-window-variable"]
status: "PUBLISHED"
---
# Monotonic Stack Pattern

## Definition
A stack that maintains elements in monotonic order (increasing or decreasing) to efficiently find next greater/smaller elements.

## When to Use
- Finding next greater element, next smaller element, previous greater/smaller
- Largest rectangle in histogram
- Trapping rain water
- Daily temperatures
- Number of visible people

## Why It Works
By maintaining monotonic order, we can determine the next greater/smaller element for each position in O(n) total time - each element is pushed and popped at most once.

## Intuition
When a new element arrives, it resolves the 'waiting' elements that are smaller/larger than it. Elements in the stack are waiting for their next greater/smaller element.

## Invariant
Stack elements are in strictly increasing (or decreasing) order from bottom to top.

## Strengths
- O(n) time for next greater/smaller problems
- Single pass
- Elegant solution for histogram problems

## Weaknesses
- Only works for next/previous greater/smaller type problems
- Requires understanding of monotonic property
- Can be tricky to get direction right

## Recognition Signals
- "next greater element"
- "next smaller element"
- "previous greater element"
- "previous smaller element"
- "largest rectangle in histogram"
- "trapping rain water"
- "daily temperatures"
- "number of visible people"

## Common Failure Modes
- Wrong monotonic direction (increasing vs decreasing)
- Not handling equal elements correctly
- Forgetting to process remaining elements in stack at the end
- Incorrect index calculations for width in histogram

## Generalizations
Can be used with pairs (value, index); can maintain multiple monotonic stacks; extends to 2D for matrix histogram problems.

## Complexity
- Time: O(n)
- Space: O(n)