---
id: "evaluate-reverse-polish-notation"
title: "Evaluate Reverse Polish Notation"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/evaluate-reverse-polish-notation/"
patterns: ["stack-fundamentals"]
algorithms: []
dataStructures: ["stack"]
templates: []
prerequisites: ["stack"]
relatedProblems: ["valid-parentheses", "generate-parentheses", "min-stack", "basic-calculator"]
status: "PUBLISHED"
orderIndex: 12
reasonForSelection: "Stack for postfix evaluation; operand stack with operators"
---

# Problem Statement

Evaluate the value of an arithmetic expression in **Reverse Polish Notation**.

Valid operators are `+`, `-`, `*`, and `/`. Each operand may be an integer or another expression.

**Note** that division between two integers should truncate toward zero.

It is guaranteed that the given RPN expression is always valid. That means the expression would always evaluate to a result, and there will not be any division by zero operation.

## Constraints

- 1 ≤ tokens.length ≤ 10⁴
- `tokens[i]` is either an operator: `"+", "-", "*", "/"` or an integer in the range `[-200, 200]`.

## Examples

**Example 1:**
```
Input: tokens = ["2","1","+","3","*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
```

**Example 2:**
```
Input: tokens = ["4","13","5","/","+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6
```

**Example 3:**
```
Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
Output: 22
Explanation:
((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= 22
```

## Variations

### Variation 1: Basic Calculator
**Name**: Basic Calculator

**What Changed**: Infix expression with parentheses.

**Original Approach Works**: Stack for operators and operands.

**What Breaks**: Operator precedence and parentheses handling.

**New Constraint**: Handle +, -, *, /, (, ).

**Algorithm Changes**: Two stacks (operands, operators) or shunting-yard.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Valid Parentheses
**Name**: Valid Parentheses

**What Changed**: Check valid parentheses instead of evaluation.

**Original Approach Works**: Stack for matching brackets.

**What Breaks**: No arithmetic, just matching.

**New Constraint**: Return true if valid.

**Algorithm Changes**: Push opening, pop and match closing.

**Complexity Changes**: Time O(n), Space O(n).