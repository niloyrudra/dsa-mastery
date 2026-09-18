---
id: "two-pointers"
name: "Two Pointers"
description: "Technique using two indices moving through array from different directions or speeds. Common on sorted arrays."
status: "PUBLISHED"
---
# Two Pointers

## Overview
Two indices traverse array simultaneously. Reduces O(n²) to O(n) for many pair problems.

## Patterns
### Opposite Ends (Sorted Arrays)
```typescript
let left = 0, right = n - 1;
while (left < right) {
    const sum = arr[left] + arr[right];
    if (sum === target) return [left, right];
    if (sum < target) left++;
    else right--;
}
```

### Same Direction (Fast/Slow)
```typescript
let slow = 0, fast = 0;
while (fast < n) {
    if (condition) slow++;
    fast++;
}
```

### Same Direction (Read/Write)
```typescript
let write = 0;
for (read = 0; read < n; read++) {
    if (shouldKeep(arr[read])) {
        arr[write++] = arr[read];
    }
}
```

## When to Use
- Sorted array + pair/triplet sum
- Palindrome checking
- Container with most water
- Remove duplicates in-place
- Merge two sorted arrays
- Cycle detection (Floyd's)

## Key Insight
Monotonic movement: pointers only move in one direction, never backtrack. Total steps = O(n).

## Common Problems
- Two Sum (sorted)
- 3Sum / 4Sum
- Container With Most Water
- Trapping Rain Water
- Valid Palindrome
- Move Zeroes
- Remove Duplicates