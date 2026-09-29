---
id: "sliding-window-maximum"
title: "Sliding Window Maximum"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/sliding-window-maximum/"
patterns: ["sliding-window-fixed", "monotonic-stack"]
algorithms: []
dataStructures: ["array"]
templates: ["sliding-window-template-ts"]
prerequisites: ["array", "sliding-window"]
relatedProblems: ["largest-rectangle-in-histogram", "daily-temperatures", "max-sliding-window-sum", "trapping-rain-water"]
status: "PUBLISHED"
orderIndex: 15
reasonForSelection: "Monotonic decreasing deque; O(n) with amortized O(1) operations"
---

# Problem Statement

You are given an array of integers `nums`, there is a sliding window of size `k` which is moving from the very left of the array to the very right. You can only see the `k` numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window.

## Constraints

- 1 ≤ nums.length ≤ 10⁵
- -10⁴ ≤ nums[i] ≤ 10⁴
- 1 ≤ k ≤ nums.length

## Examples

**Example 1:**
```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation:
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]       7
```

**Example 2:**
```
Input: nums = [1], k = 1
Output: [1]
```

## Variations

### Variation 1: Largest Rectangle in Histogram
**Name**: Largest Rectangle in Histogram

**What Changed**: Max rectangle area in histogram.

**Original Approach Works**: Monotonic stack for next/previous smaller.

**What Breaks**: Area vs sliding window max.

**New Constraint**: Return max area.

**Algorithm Changes**: Stack of indices, calculate on pop.

**Complexity Changes**: Time O(n), Space O(n).

### Variation 2: Daily Temperatures
**Name**: Daily Temperatures

**What Changed**: Days until warmer temperature.

**Original Approach Works**: Monotonic stack.

**What Breaks**: Days vs values.

**New Constraint**: Return days array.

**Algorithm Changes**: Stack of indices, calculate i - stack.top on pop.

**Complexity Changes**: Time O(n), Space O(n).