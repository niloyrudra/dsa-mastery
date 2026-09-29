---
id: "math-fundamentals"
name: "Math Fundamentals"
category: "MATH"
patterns: []
algorithms: []
dataStructures: ["array"]
templates: []
problems: ["power-of-two", "happy-number", "factorial-trailing-zeroes", "divide-two-integers", "fraction-to-recurring-decimal", "count-primes", "reverse-integer", "add-binary", "single-number", "single-number-ii", "total-hamming-distance"]
prerequisites: ["array"]
relatedPatterns: ["bit-manipulation-fundamentals", "dp-1d-fundamentals"]
status: "PUBLISHED"
---
# Math Fundamentals Pattern

## Definition
Mathematical techniques: modular arithmetic, GCD/LCM, prime factorization, fast exponentiation, number theory properties, combinatorics.

## When to Use
- Power of two
- Happy number
- Factorial trailing zeroes
- Divide two integers
- Fraction to recurring decimal
- Counting primes
- Modular exponentiation
- GCD/LCM problems

## Why It Works
Mathematical properties provide O(1) or O(log n) solutions. Modular arithmetic: (a*b) % m = ((a%m) * (b%m)) % m. Fast power: binary exponentiation reduces O(n) to O(log n).

## Intuition
Look for mathematical patterns: powers of 2 have single bit set; trailing zeroes come from factors of 5; recurring decimals from division cycles; GCD by Euclidean algorithm.

## Invariant
Mathematical invariants: modulo operations preserve equivalence; Euclidean algorithm maintains GCD; binary exponentiation maintains result * base^exp = target.

## Strengths
- O(1) or O(log n) time
- Elegant solutions
- No complex data structures
- Constant space usually

## Weaknesses
- Requires mathematical insight
- Easy to miss edge cases
- Overflow in languages without big integers
- Not all problems have mathematical shortcuts

## Recognition Signals
- "power of two"
- "happy number"
- "factorial trailing zeroes"
- "divide two integers"
- "fraction to recurring decimal"
- "counting primes"
- "modular exponentiation"
- "gcd lcm"
- "prime factorization"
- "combinatorics"

## Common Failure Modes
- Integer overflow
- Off-by-one in binary exponentiation
- Not handling negative numbers in modulo
- Infinite loop in happy number detection
- Precision issues with floating point
- Not handling large factorials

## Generalizations
Matrix exponentiation for linear recurrences; Chinese Remainder Theorem; Miller-Rabin primality test; Pollard's rho factorization; FFT for polynomial multiplication.

## Complexity
- Time: O(log n) for fast power/GCD; O(sqrt(n)) for prime factorization; O(1) for bit tricks
- Space: O(1) typically