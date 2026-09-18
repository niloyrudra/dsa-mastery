---
id: "sliding-window"
name: "Sliding Window"
description: "Technique for subarray/substring problems. Maintain window boundaries that slide through array, updating aggregate incrementally."
status: "PUBLISHED"
---
# Sliding Window

## Overview
Efficient technique for problems on contiguous subarrays/substrings. Avoids recomputing from scratch.

## Types
### Fixed Window
Window size k is constant.
```typescript
for (right = 0; right < n; right++) {
    add(arr[right]);
    if (right >= k) remove(arr[right-k]);
    if (right >= k-1) updateResult();
}
```

### Variable Window
Window expands/contracts based on condition.
```typescript
while (right < n) {
    add(arr[right]);
    while (invalid) remove(arr[left++]);
    if (valid) updateResult();
    right++;
}
```

## Key Insight
Each element enters and leaves window at most once → O(n) total.

## When to Use
- Subarray/substring with specific property
- "Maximum/Minimum subarray of size k"
- "Longest substring with..."
- "Number of subarrays with..."
- Requires O(n) or O(n log n)

## Template Variables
- `left`, `right`: Window boundaries
- `windowSum/windowCount`: Current aggregate
- `result`: Best answer found

## Common Patterns
- **Sum-based**: Add/remove values
- **Count-based**: Hash map of frequencies
- **Distinct count**: Track unique elements
- **Two pointers variant**: Left/right move independently