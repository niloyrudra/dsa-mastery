---
id: "bit-manipulation-fundamentals"
name: "Bit Manipulation Fundamentals"
category: "Bit Manipulation"
definition: "Bitwise operations for efficient computation: XOR properties, bit masking, bit counting, power of two checks, single number detection, Hamming distance."
when_to_use: "Single number; reverse integer; add binary; hamming distance; total hamming distance; reverse bits; missing number; sum of two integers; maximum xor; subset generation with bitmask."
why_it_works: "XOR properties: a ^ a = 0, a ^ 0 = a, a ^ b ^ a = b. Bit masking isolates specific bits. Brian Kernighan's algorithm: n & (n-1) clears lowest set bit. Two's complement for negative numbers."
intuition: "XOR cancels pairs. Bit shifts multiply/divide by 2. Masking extracts bits. Count bits by clearing lowest set bit repeatedly. Bitmask represents subsets (0/1 for each element)."
invariant: "XOR: cumulative XOR of pairs cancels out. Bit count: n & (n-1) reduces set bit count by 1. Bitmask: each bit represents inclusion/exclusion."
strengths: "O(1) or O(log n) time; constant space; extremely fast; elegant solutions for specific problems; hardware-accelerated."
weaknesses: "Limited to integer/bit problems; can be cryptic; easy to make mistakes with operator precedence; not portable across bit widths; signed vs unsigned issues."
recognition_signals:
  - "single number"
  - "hamming distance"
  - "reverse bits"
  - "power of two"
  - "missing number"
  - "sum of two integers"
  - "maximum xor"
  - "subset generation"
  - "bitwise and/or/xor"
  - "counting bits"
common_failure_modes:
  - "Signed vs unsigned shift (>>> vs >> in Java)"
  - "Operator precedence (bitwise vs arithmetic)"
  - "Infinite loop in bit counting (for negative numbers)"
  - "Overflow in reverse integer"
  - "Not handling 32-bit vs 64-bit"
  - "Off-by-one in bit positions"
generalizations: "Bit DP for subset problems; Trie for maximum XOR; Bloom filters; bitwise convolution; SIMD operations; quantum computing analogs."
time_complexity: "O(1) for single operations; O(log n) for bit counting; O(n) for array bit problems"
space_complexity: "O(1)"
time_reasoning: "Bitwise operations are constant time; bit counting loops over set bits."
space_reasoning: "Only uses integer variables."
status: "PUBLISHED"
---
# Bit Manipulation Fundamentals Pattern

## Definition
Bitwise operations for efficient computation: XOR properties, bit masking, bit counting, power of two checks, single number detection, Hamming distance.

## When to Use
- Single number
- Reverse integer
- Add binary
- Hamming distance
- Total Hamming distance
- Reverse bits
- Missing number
- Sum of two integers
- Maximum XOR
- Subset generation with bitmask

## Why It Works
XOR properties: a ^ a = 0, a ^ 0 = a, a ^ b ^ a = b. Bit masking isolates specific bits. Brian Kernighan's algorithm: n & (n-1) clears lowest set bit. Two's complement for negative numbers.

## Intuition
XOR cancels pairs. Bit shifts multiply/divide by 2. Masking extracts bits. Count bits by clearing lowest set bit repeatedly. Bitmask represents subsets (0/1 for each element).

## Invariant
XOR: cumulative XOR of pairs cancels out. Bit count: n & (n-1) reduces set bit count by 1. Bitmask: each bit represents inclusion/exclusion.

## Strengths
- O(1) or O(log n) time
- Constant space
- Extremely fast
- Elegant solutions for specific problems
- Hardware-accelerated

## Weaknesses
- Limited to integer/bit problems
- Can be cryptic
- Easy to make mistakes with operator precedence
- Not portable across bit widths
- Signed vs unsigned issues

## Recognition Signals
- "single number"
- "hamming distance"
- "reverse bits"
- "power of two"
- "missing number"
- "sum of two integers"
- "maximum xor"
- "subset generation"
- "bitwise and/or/xor"
- "counting bits"

## Common Failure Modes
- Signed vs unsigned shift (>>> vs >> in Java)
- Operator precedence (bitwise vs arithmetic)
- Infinite loop in bit counting (for negative numbers)
- Overflow in reverse integer
- Not handling 32-bit vs 64-bit
- Off-by-one in bit positions

## Generalizations
Bit DP for subset problems; Trie for maximum XOR; Bloom filters; bitwise convolution; SIMD operations; quantum computing analogs.

## Complexity
- Time: O(1) for single operations; O(log n) for bit counting; O(n) for array bit problems
- Space: O(1)