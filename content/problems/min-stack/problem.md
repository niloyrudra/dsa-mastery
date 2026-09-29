---
id: "min-stack"
title: "Min Stack"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/min-stack/"
patterns: ["stack-fundamentals"]
algorithms: []
dataStructures: ["stack"]
templates: []
prerequisites: ["stack"]
relatedProblems: ["evaluate-reverse-polish-notation", "valid-parentheses", "daily-temperatures", "largest-rectangle-in-histogram"]
status: "PUBLISHED"
orderIndex: 12
reasonForSelection: "Auxiliary stack for minimums; O(1) getMin with O(1) push/pop"
---

# Problem Statement

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Implement the `MinStack` class:

- `MinStack()` initializes the stack object.
- `void push(int val)` pushes the element `val` onto the stack.
- `void pop()` removes the element on the top of the stack.
- `int top()` gets the top element of the stack.
- `int getMin()` retrieves the minimum element in the stack.

You must implement a solution with `O(1)` time complexity for each function.

## Constraints

- -2³¹ ≤ val ≤ 2³¹ - 1
- Methods `pop`, `top` and `getMin` operations will always be called on **non-empty** stacks.
- At most 3 × 10⁴ calls will be made to `push`, `pop`, `top`, and `getMin`.

## Examples

**Example 1:**
```
Input:
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]
Output:
[null,null,null,null,-3,null,0,-2]
Explanation:
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2
```

## Variations

### Variation 1: Max Stack
**Name**: Max Stack

**What Changed**: Track maximum instead of minimum.

**Original Approach Works**: Same auxiliary stack approach.

**What Breaks**: Max instead of min.

**New Constraint**: Return maximum.

**Algorithm Changes**: Auxiliary stack tracks max.

**Complexity Changes**: Time O(1), Space O(n).

### Variation 2: Evaluate Reverse Polish Notation
**Name**: Evaluate Reverse Polish Notation

**What Changed**: Stack for postfix evaluation.

**Original Approach Works**: Stack for operands.

**What Breaks**: Arithmetic operations.

**New Constraint**: Return evaluated result.

**Algorithm Changes**: Push operands, pop for operators.

**Complexity Changes**: Time O(n), Space O(n).