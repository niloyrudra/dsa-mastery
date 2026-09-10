---
id: "hash-map-lookup"
name: "Hash Map Lookup Template"
pattern: "hash-map-lookup"
language: "TYPESCRIPT"
purpose: "Find complement in O(1) using hash map"
whenToReachForIt: "Unsorted array, need to find pair with target sum, single pass preferred"
prerequisites: ["arrays", "hash-map"]
mentalModel: "Store seen values in a lookup table; for each new value, check if its complement exists"
status: "PUBLISHED"
---

# Template: Hash Map Lookup

## Code
```typescript
function hashMapLookup<T, U>(
    arr: T[],                    // [1] Input array
    target: U,                   // [2] Target value to find
    getKey: (item: T) => U,      // [3] Extract key from item
    getComplement: (item: T, target: U) => U  // [4] Compute complement
): [number, number] | null {
    const map = new Map<U, number>();  // [5] Hash map for O(1) lookup
    
    for (let i = 0; i < arr.length; i++) {  // [6] Single pass
        const complement = getComplement(arr[i], target);  // [7]
        
        if (map.has(complement)) {          // [8] Check if complement seen
            return [map.get(complement)!, i]; // [9] Found pair
        }
        
        map.set(getKey(arr[i]), i);         // [10] Store current for future
    }
    
    return null;  // [11] No pair found
}
```

## Explanation
[1] Input array to search through
[2] Target value we want to achieve (sum, difference, etc.)
[3] Function to extract the lookup key from each element
[4] Function to compute what value we need to find
[5] Hash map stores key -> index for O(1) lookup
[6] Single pass through array - O(n) time
[7] Compute the complement we need to find
[8] Hash map lookup is O(1) average case
[9] Return indices of the pair
[10] Store current element for future complement checks
[11] No valid pair exists

## Invariant
At iteration i, the map contains all elements from indices [0, i-1] with their indices.

## Common Modifications
- Return values instead of indices
- Count all pairs instead of finding one
- Handle duplicate values correctly
- Find triplets (3Sum) by nesting this pattern

## Common Mistakes
- Checking map before inserting current element (causes self-pair)
- Not handling duplicate values in 3Sum variants
- Using wrong key extraction for complex objects

## Complexity
- Time: O(n) - single pass with O(1) hash map operations
- Space: O(n) - hash map stores up to n entries

## Example Problems
- Two Sum
- Contains Duplicate
- Valid Anagram
- Group Anagrams
- 3Sum (with sorting + this pattern)