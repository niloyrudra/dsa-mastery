---
id: "sliding-window-fixed"
name: "Fixed Sliding Window"
category: "ARRAYS_STRINGS"
patterns: []
algorithms: []
dataStructures: ["array"]
templates: ["sliding-window-template-ts"]
problems: ["sliding-window-maximum"]
prerequisites: ["array", "sliding-window"]
relatedPatterns: ["sliding-window-variable", "monotonic-stack"]
status: "PUBLISHED"
---
# Fixed Sliding Window Pattern

## Definition
Maintaining a window of fixed size k that slides through the array, computing results for each position.

## When to Use
- Finding maximum/minimum/average of all subarrays of size k
- Problems where window size is fixed and given
- Any problem requiring computation over all contiguous subarrays of fixed length

## Why It Works
By maintaining a running sum/count and only adding the new element and removing the oldest element, we achieve O(n) instead of O(n*k).

## Intuition
The window of size k moves one step at a time. Only the entering and leaving elements change; we update our aggregate accordingly.

## Invariant
Window always contains exactly k consecutive elements; window sum is maintained incrementally.

## Strengths
- O(n) time, O(1) space for sum-based problems
- Avoids recomputing from scratch for each window

## Weaknesses
- Only works when window size is fixed
- Not suitable for variable window problems

## Recognition Signals
- "subarray of size k"
- "maximum/minimum/average of each subarray of size k"
- "fixed window size"
- "sliding window of length k"

## Common Failure Modes
- Incorrect window initialization
- Off-by-one in loop bounds
- Not handling k > array length
- Forgetting to update aggregate when window slides

## Generalizations
Can be extended to 2D sliding windows for matrices; can maintain multiple aggregates simultaneously.

## Complexity
- Time: O(n)
- Space: O(1) or O(k) depending on what's maintained