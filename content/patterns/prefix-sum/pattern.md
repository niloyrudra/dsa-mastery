---
id: "prefix-sum"
name: "Prefix Sum"
category: "Array"
definition: "Precomputing cumulative sums to answer range sum queries in O(1) time after O(n) preprocessing."
when_to_use: "Multiple range sum queries on static arrays; subarray sum problems; finding subarrays with specific sum properties."
why_it_works: "Range sum from index i to j equals prefix[j+1] - prefix[i]. This avoids recomputing sums for overlapping ranges."
intuition: "Build an array where each element at index i stores the sum of all elements from index 0 to i-1. Then any subarray sum is just the difference of two prefix values."
invariant: "prefix[i] = sum of elements from index 0 to i-1; prefix[0] = 0."
strengths: "O(1) range queries after O(n) preprocessing; simple to implement; works well with hash maps for subarray sum equals k problems."
weaknesses: "Does not handle array updates efficiently; requires O(n) extra space; not suitable for dynamic arrays with frequent modifications."
recognition_signals:
  - "range sum query"
  - "subarray sum equals k"
  - "maximum subarray sum"
  - "number of subarrays with sum"
  - "multiple sum queries on static array"
common_failure_modes:
  - "Off-by-one errors in prefix array indices"
  - "Not handling empty subarrays"
  - "Confusing prefix[i] with sum up to index i (it's up to i-1)"
  - "Forgetting prefix[0] = 0 initialization"
generalizations: "Can be extended to 2D prefix sums for matrix range queries; can be used with hash maps for counting subarrays with specific sums."
time_complexity: "O(n) preprocessing, O(1) per query"
space_complexity: "O(n) for prefix array"
time_reasoning: "Single pass to build prefix array; each query is constant time subtraction."
space_reasoning: "Stores n+1 prefix sums."
status: "PUBLISHED"
---
# Prefix Sum Pattern

## Definition
Precomputing cumulative sums to answer range sum queries in O(1) time after O(n) preprocessing.

## When to Use
- Multiple range sum queries on static arrays
- Subarray sum problems (e.g., subarray sum equals k)
- Finding subarrays with specific sum properties
- Problems requiring frequent sum calculations over subarrays

## Why It Works
Range sum from index i to j equals `prefix[j+1] - prefix[i]`. This avoids recomputing sums for overlapping ranges.

## Intuition
Build an array where each element at index i stores the sum of all elements from index 0 to i-1. Then any subarray sum is just the difference of two prefix values.

## Invariant
`prefix[i] = sum of elements from index 0 to i-1; prefix[0] = 0.`

## Strengths
- O(1) range queries after O(n) preprocessing
- Simple to implement
- Works well with hash maps for subarray sum equals k problems

## Weaknesses
- Does not handle array updates efficiently
- Requires O(n) extra space
- Not suitable for dynamic arrays with frequent modifications

## Recognition Signals
- "range sum query"
- "subarray sum equals k"
- "maximum subarray sum"
- "number of subarrays with sum"
- "multiple sum queries on static array"

## Common Failure Modes
- Off-by-one errors in prefix array indices
- Not handling empty subarrays
- Confusing prefix[i] with sum up to index i (it's up to i-1)
- Forgetting prefix[0] = 0 initialization

## Generalizations
Can be extended to 2D prefix sums for matrix range queries; can be used with hash maps for counting subarrays with specific sums.

## Complexity
- Time: O(n) preprocessing, O(1) per query
- Space: O(n) for prefix array