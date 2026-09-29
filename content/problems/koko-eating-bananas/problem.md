---
id: "koko-eating-bananas"
title: "Koko Eating Bananas"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/koko-eating-bananas/"
patterns: ["binary-search-on-answer", "binary-search-boundary"]
algorithms: ["binary-search"]
dataStructures: ["array"]
templates: ["binary-search-template-ts"]
prerequisites: ["array", "binary-search"]
relatedProblems: ["find-minimum-in-rotated-sorted-array", "capacity-to-ship-packages", "split-array-largest-sum", "minimum-number-of-arrows-to-burst-balloons"]
status: "PUBLISHED"
orderIndex: 25
reasonForSelection: "Binary search on eating speed; feasibility check function"
---

# Problem Statement

Koko loves to eat bananas. There are `n` piles of bananas, the `i`th pile has `piles[i]` bananas. The guards have gone and will come back in `h` hours.

Koko can decide her bananas-per-hour eating speed of `k`. Each hour, she chooses some pile of bananas and eats `k` bananas from that pile. If the pile has less than `k` bananas, she eats all of them instead and will not eat any more bananas during this hour.

Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.

Return the minimum integer `k` such that she can eat all the bananas within `h` hours.

## Constraints

- 1 ≤ piles.length ≤ 10⁴
- 1 ≤ piles[i] ≤ 10⁹
- piles.length ≤ h ≤ 10⁹

## Examples

**Example 1:**
```
Input: piles = [3,6,7,11], h = 8
Output: 4
Explanation: With k = 4, Koko can finish in 8 hours.
```

**Example 2:**
```
Input: piles = [30,11,23,4,20], h = 5
Output: 30
```

**Example 3:**
```
Input: piles = [30,11,23,4,20], h = 6
Output: 23
```

## Variations

### Variation 1: Capacity To Ship Packages Within D Days
**Name**: Capacity To Ship Packages Within D Days

**What Changed**: Ship packages instead of eating bananas.

**Original Approach Works**: Same binary search on answer, feasibility check.

**What Breaks**: Different domain, same algorithm.

**New Constraint**: Return minimum ship capacity.

**Algorithm Changes**: Feasibility = can ship all packages in D days with capacity k.

**Complexity Changes**: Time O(n log sum), Space O(1).

### Variation 2: Split Array Largest Sum
**Name**: Split Array Largest Sum

**What Changed**: Split array into m subarrays, minimize largest sum.

**Original Approach Works**: Binary search on answer (max subarray sum).

**What Breaks**: Subarray split vs hour-based.

**New Constraint**: Return minimized largest sum.

**Algorithm Changes**: Feasibility = can split into m subarrays with max sum k.

**Complexity Changes**: Time O(n log sum), Space O(1).