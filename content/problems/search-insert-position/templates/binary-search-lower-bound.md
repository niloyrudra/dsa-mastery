---
id: "binary-search-lower-bound"
name: "Binary Search - Lower Bound Template"
pattern: "binary-search-boundary"
language: "TYPESCRIPT"
purpose: "Find first position where element >= target (insertion point)"
whenToReachForIt: "Sorted array, need insertion point or first >= target"
prerequisites: ["arrays", "binary-search"]
mentalModel: "Narrow search space to find boundary where condition changes"
status: "PUBLISHED"
---

# Template: Binary Search - Lower Bound

## Code
```typescript
function lowerBound(
    arr: number[],                // [1] Sorted array
    target: number                // [2] Target value
): number {
    let left = 0;                 // [3] Search space start
    let right = arr.length;       // [4] Search space end (exclusive)
    
    while (left < right) {        // [5] Invariant: answer in [left, right)
        const mid = left + Math.floor((right - left) / 2);  // [6]
        
        if (arr[mid] < target) {  // [7] Target is to the right
            left = mid + 1;       // [8] Exclude mid and left side
        } else {                  // [9] arr[mid] >= target
            right = mid;          // [10] mid could be answer, keep it
        }
    }
    
    return left;                  // [11] left is insertion point
}
```

## Explanation
[1] Sorted array to search
[2] Target value to find insertion point for
[3] Left boundary of search space (inclusive)
[4] Right boundary of search space (exclusive) - note: length, not length-1
[5] Loop continues while search space has more than 1 element
[6] Midpoint calculation avoids overflow
[7] If mid element < target, target must be to the right
[8] Exclude mid and everything left of it
[9] If mid element >= target, mid could be the answer
[10] Keep mid in search space by setting right = mid
[11] When left == right, that's the insertion point

## Invariant
At each iteration, the insertion point (first index where arr[i] >= target) is in [left, right).

## Common Modifications
- Upper bound: first index where arr[i] > target (change < to <=)
- Find exact match: check arr[left] === target after loop
- Search in descending array: reverse comparison

## Common Mistakes
- Using left <= right (causes infinite loop or off-by-one)
- Setting right = mid - 1 (excludes potential answer)
- Not handling empty array (works correctly with length)
- Using (left + right) / 2 (overflow risk in other languages)

## Complexity
- Time: O(log n) - halves search space each iteration
- Space: O(1) - constant extra space

## Example Problems
- Search Insert Position
- First Bad Version
- Find First and Last Position
- Koko Eating Bananas (binary search on answer)