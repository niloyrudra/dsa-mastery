---
id: "sliding-window-fixed"
name: "Fixed Sliding Window"
category: "Array"
definition: "Maintaining a window of fixed size k that slides through the array, computing results for each position."
when_to_use: "Finding maximum/minimum/average of all subarrays of size k; problems where window size is fixed and given."
why_it_works: "By maintaining a running sum/count and only adding the new element and removing the oldest element, we achieve O(n) instead of O(n*k)."
intuition: "The window of size k moves one step at a time. Only the entering and leaving elements change; we update our aggregate accordingly."
invariant: "Window always contains exactly k consecutive elements; window sum is maintained incrementally."
strengths: "O(n) time, O(1) space for sum-based problems; avoids recomputing from scratch for each window."
weaknesses: "Only works when window size is fixed; not suitable for variable window problems."
recognition_signals:
  - "subarray of size k"
  - "maximum/minimum/average of each subarray of size k"
  - "fixed window size"
  - "sliding window of length k"
common_failure_modes:
  - "Incorrect window initialization"
  - "Off-by-one in loop bounds"
  - "Not handling k > array length"
  - "Forgetting to update aggregate when window slides"
generalizations: "Can be extended to 2D sliding windows for matrices; can maintain multiple aggregates simultaneously."
time_complexity: "O(n)"
space_complexity: "O(1) or O(k) depending on what's maintained"
time_reasoning: "Each element enters and leaves the window exactly once."
space_reasoning: "Only stores current window aggregate and optionally the window elements."
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