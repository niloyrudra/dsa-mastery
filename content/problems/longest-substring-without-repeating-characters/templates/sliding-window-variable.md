---
id: "sliding-window-variable"
name: "Sliding Window - Variable Size Template"
pattern: "sliding-window-variable"
language: "TYPESCRIPT"
purpose: "Find longest/shortest substring/subarray meeting condition"
whenToReachForIt: "Substring/subarray with constraints (unique chars, k distinct, etc.)"
prerequisites: ["arrays", "hash-map"]
mentalModel: "Expand right to grow, contract left to satisfy constraint"
status: "PUBLISHED"
---

# Template: Sliding Window - Variable Size

## Code
```typescript
function slidingWindowVariable<T>(
    arr: T[],                     // [1] Input array/string
    isValid: (window: Map<T, number>) => boolean,  // [2] Window validity check
    onExpand: (item: T, window: Map<T, number>) => void,  // [3] Add item to window
    onContract: (item: T, window: Map<T, number>) => void  // [4] Remove item from window
): number {
    const window = new Map<T, number>();  // [5] Frequency map for window
    let left = 0;
    let best = 0;
    
    for (let right = 0; right < arr.length; right++) {  // [6] Expand right
        onExpand(arr[right], window);      // [7]
        
        while (!isValid(window)) {         // [8] Contract until valid
            onContract(arr[left], window); // [9]
            left++;                        // [10]
        }
        
        best = Math.max(best, right - left + 1);  // [11] Update best
    }
    
    return best;  // [12]
}
```

## Explanation
[1] Input array or string to search
[2] Function checking if current window satisfies constraints
[3] Function to add new right element to window
[4] Function to remove left element from window
[5] Hash map tracking frequency of elements in window
[6] Expand window by moving right pointer
[7] Add new element to window state
[8] Contract window from left while invalid
[9] Remove left element from window state
[10] Move left pointer right
[11] Update best answer (length, sum, etc.)
[12] Return optimal result

## Invariant
Window [left, right] always satisfies the constraint (or is being contracted to satisfy it).

## Common Modifications
- Find minimum window instead of maximum (change best update)
- Track exact window indices for substring return
- Use array instead of hash map for fixed charset
- Handle multiple constraints (AND/OR logic in isValid)

## Common Mistakes
- Contracting when window is already valid
- Not updating best after contraction
- Off-by-one in window length calculation
- Forgetting to initialize frequency map properly

## Complexity
- Time: O(n) - each element enters and leaves window at most once
- Space: O(min(n, m)) - window size bounded by input and charset

## Example Problems
- Longest Substring Without Repeating Characters
- Minimum Window Substring
- Longest Substring with At Most K Distinct
- Fruit Into Baskets