---
id: "sliding-window-variable"
name: "Sliding Window - Variable Size"
category: "ARRAYS_STRINGS"
patterns: []
algorithms: ["sliding-window-variable"]
dataStructures: ["array", "hash-map", "string"]
templates: ["sliding-window-variable", "sliding-window-char-set"]
problems: ["longest-substring-without-repeating", "minimum-window-substring", "longest-substring-k-distinct", "fruit-into-baskets"]
prerequisites: ["arrays", "hash-map"]
relatedPatterns: ["two-pointer", "sliding-window-fixed"]
status: "PUBLISHED"
---

# Sliding Window - Variable Size Pattern

## Definition

Maintaining a window (contiguous subarray/substring) defined by left and right pointers that expands and contracts based on whether the window satisfies a constraint. The window size is not fixed - it grows when valid and shrinks when invalid, dynamically adjusting to find optimal subarrays.

## When to Use

- Find longest/shortest subarray/substring satisfying a condition
- Condition is monotonic: adding elements makes it "more valid" or "less valid"
- Substring with unique characters, at most K distinct, exact character counts
- Problems where you can determine validity by looking at window contents
- When O(n) is needed but naive is O(n²) or worse

## Why It Works

The window maintains an invariant: it always satisfies the constraint (or is being contracted to satisfy it). When expanding right makes it invalid, contracting left restores validity. Each element enters and leaves the window at most once, giving O(n) total operations.

## Recognition Signals

- **Structural**: "Longest substring with..." → variable window, expand right, contract left
- **Structural**: "Shortest substring containing..." → variable window, expand to cover, contract to minimize
- **Structural**: "At most K distinct / exactly K" → frequency map in window, track distinct count
- **Structural**: "Subarray sum equals K" → prefix sum + hash map (different pattern) or sliding window if all positive
- **Keywords**: "substring", "subarray", "window", "longest", "shortest", "at most", "at least"

## Invariant

Window [left, right] always satisfies the constraint (or is empty during contraction). All subarrays ending at `right` and starting before `left` have been considered and rejected.

## Strengths

- O(n) time for many problems that seem to require O(n²)
- O(1) or O(k) space where k is constraint bound (distinct chars, etc.)
- Natural for substring/subarray problems with constraints
- Flexible: works with counts, sets, sums, products

## Weaknesses

- Only works when constraint is monotonic with respect to window expansion
- Doesn't work with negative numbers for sum problems (adding can decrease sum)
- Requires efficient add/remove operations for window state (hash map, counts)
- Can be tricky to determine correct contraction condition

## Common Failure Modes

1. **Contracting when already valid**: Should only contract when invalid (for longest) or when trying to minimize (for shortest)
2. **Not updating answer after contraction**: For "shortest" problems, answer must be checked after each contraction step
3. **Wrong validity check**: Condition must be checkable in O(1) from window state
4. **Off-by-one in window length**: `right - left + 1` vs `right - left`
5. **Forgetting to clean up window state on contraction**: Counts must be decremented correctly

## Generalizations

- **Fixed Window**: Window size is constant (use when problem specifies length K)
- **Two Pointers**: Sliding window with specific movement rules (opposite ends)
- **Binary Search on Answer**: When window constraint is monotonic in window size
- **Prefix Sum + Hash Map**: For subarray sum with negative numbers

## Complexity

- Time: O(n) - each element enters and leaves window at most once
- Space: O(min(n, k)) - window state (frequency map, set, etc.)

## Example Problems

- **Longest Substring Without Repeating Characters**: Expand right, contract left on duplicate
- **Minimum Window Substring**: Expand to cover all required, contract to minimize
- **Longest Substring with At Most K Distinct**: Frequency map, contract when distinct > K
- **Fruit Into Baskets**: At most 2 types, same as K distinct with K=2
- **Permutation in String**: Fixed window size = len(s1), check frequency match