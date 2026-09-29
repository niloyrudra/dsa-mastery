---
id: "stack-fundamentals"
name: "Stack Fundamentals"
category: "STACK"
patterns: []
algorithms: []
dataStructures: ["stack"]
templates: []
problems: ["valid-parentheses", "generate-parentheses", "evaluate-reverse-polish-notation", "min-stack", "daily-temperatures", "largest-rectangle-in-histogram", "trapping-rain-water", "asteroid-collision"]
prerequisites: ["stack"]
relatedPatterns: ["monotonic-stack", "tree-dfs"]
status: "PUBLISHED"
---
# Stack Fundamentals Pattern

## Definition
Using stack (LIFO) for problems involving matching, nesting, or reversing order - parentheses matching, expression evaluation, backtracking simulation.

## When to Use
- Valid parentheses
- Expression evaluation (RPN)
- Nested structures
- Undo operations
- DFS simulation
- Matching pairs

## Why It Works
Stack naturally models LIFO behavior - last opened must be first closed. Perfect for matching nested structures.

## Intuition
Push opening symbols, pop and match with closing symbols. Stack state represents current nesting level.

## Invariant
Stack contains unmatched opening symbols in order of appearance.

## Strengths
- O(n) time, O(n) space
- Natural fit for nesting/matching
- Simple and intuitive

## Weaknesses
- Only works for LIFO matching
- Not suitable for queue-like (FIFO) matching
- Space can be O(n) in worst case

## Recognition Signals
- "valid parentheses"
- "matching brackets"
- "reverse polish notation"
- "evaluate expression"
- "nested structure"
- "generate parentheses"
- "min stack"

## Common Failure Modes
- Not checking if stack is empty before popping
- Wrong matching logic for different bracket types
- Forgetting to check if stack is empty at end
- Incorrect handling of operator precedence in RPN

## Generalizations
Can be extended to Min/Max stack with auxiliary stacks; can simulate recursion; useful for tree traversal without recursion.

## Complexity
- Time: O(n)
- Space: O(n)