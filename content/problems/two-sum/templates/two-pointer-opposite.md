---
id: "two-pointer-opposite"
name: "Two Pointer - Opposite Ends Template"
pattern: "two-pointer"
language: "TYPESCRIPT"
purpose: "Find pair in sorted array meeting condition"
whenToReachForIt: "Sorted array, pair target, opposite direction movement"
prerequisites: ["arrays", "basic-loops"]
mentalModel: "Two people at opposite ends of a line walking toward each other"
status: "PUBLISHED"
---

# Template: Two Pointer - Opposite Ends

## Code
```typescript
function twoPointerOpposite<T>(
    arr: T[],                     // [1] Input array (must be sorted)
    target: T,                    // [2] Target value to find
    compare: (sum: T, target: T) => number  // [3] Compare function
): [number, number] | null {
    let left = 0;                 // [4] Left pointer starts at beginning
    let right = arr.length - 1;   // [5] Right pointer starts at end
    
    while (left < right) {        // [6] Loop invariant: all pairs outside [left, right] checked
        const sum = arr[left] + arr[right];  // [7] Compute current pair sum
        const cmp = compare(sum, target);    // [8] Compare sum to target
        
        if (cmp === 0) {          // [9] Found exact match
            return [left, right];
        } else if (cmp < 0) {     // [10] Sum too small, need larger
            left++;               // [11] Move left pointer right
        } else {                  // [12] Sum too large, need smaller
            right--;              // [13] Move right pointer left
        }
    }
    
    return null;                  // [14] No valid pair found
}
```

## Explanation
[1] Input array (must be sorted for this pattern)
[2] Target value to find
[3] Comparison function: returns -1 if sum < target, 0 if equal, 1 if sum > target
[4] Left pointer starts at beginning
[5] Right pointer starts at end
[6] Loop invariant: all pairs outside [left, right] have been evaluated and rejected
[7] Compute current pair sum
[8] Compare sum to target
[9-13] Move pointers based on comparison - this is the core insight
[14] No valid pair found

## Invariant
At each iteration, all pairs (i, j) where i < left or j > right have been evaluated and rejected.

## Common Modifications
- Return values instead of indices
- Count pairs instead of finding one
- Handle duplicates (move pointers past duplicates)
- Three-pointer partition (Dutch National Flag)

## Common Mistakes
- Forgetting to sort array first
- Moving wrong pointer (left++ when sum > target)
- Not handling duplicate values correctly
- Off-by-one in loop condition (left <= right vs left < right)

## Complexity
- Time: O(n) - each pointer moves at most n times
- Space: O(1) - only two pointers

## Example Problems
- Two Sum (sorted variant)
- 3Sum
- Container With Most Water
- Trapping Rain Water