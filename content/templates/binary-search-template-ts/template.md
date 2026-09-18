---
id: "binary-search-template-ts"
name: "Binary Search Template"
language: "TYPESCRIPT"
purpose: "Templates for binary search on sorted arrays and binary search on answer"
when_to_reach_for_it: "Search in sorted array, find boundaries, optimization problems with monotonic property"
prerequisites: "Understanding of sorted array properties, loop invariants"
mental_model: "Maintain search space [left, right]. Halve it each iteration based on mid comparison."
code: |
// Basic Binary Search
function binarySearch(arr: number[], target: number): number {
    let left = 0, right = arr.length - 1;

    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);

        if (arr[mid] === target) return mid;
        if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }

    return -1;
}

// Lower Bound - First element >= target
function lowerBound(arr: number[], target: number): number {
    let left = 0, right = arr.length;

    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        if (arr[mid] < target) left = mid + 1;
        else right = mid;
    }

    return left;
}

// Upper Bound - First element > target
function upperBound(arr: number[], target: number): number {
    let left = 0, right = arr.length;

    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        if (arr[mid] <= target) left = mid + 1;
        else right = mid;
    }

    return left;
}

// Binary Search on Answer - Koko Eating Bananas
function minEatingSpeed(piles: number[], h: number): number {
    let left = 1, right = Math.max(...piles);

    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        const hours = piles.reduce((sum, p) => sum + Math.ceil(p / mid), 0);

        if (hours <= h) right = mid;
        else left = mid + 1;
    }

    return left;
}

// First and Last Position
function searchRange(nums: number[], target: number): number[] {
    const left = lowerBound(nums, target);
    if (left === nums.length || nums[left] !== target) return [-1, -1];
    const right = upperBound(nums, target) - 1;
    return [left, right];
}

explanation: "Maintain [left, right] or [left, right) search space. mid = left + (right-left)/2 avoids overflow. Adjust boundaries based on comparison."
invariant: "Target, if present, is always in [left, right] (or [left, right))."
common_modifications: "Lower/upper bound for duplicates. Binary search on answer space (min/max). Search in rotated array (determine sorted half)."
common_mistakes: "Infinite loop with while(left <= right) vs while(left < right); off-by-one in mid calculation; not handling empty array; overflow in mid = (left+right)/2."
time_complexity: "O(log n)"
space_complexity: "O(1)"
time_reasoning: "Search space halved each iteration. log₂(n) iterations."
space_reasoning: "Only left, right, mid variables."
pattern: "binary-search-boundary"
status: "PUBLISHED"
---
# Binary Search Templates (TypeScript)

## Basic Binary Search
```typescript
function binarySearch(arr: number[], target: number): number {
    let left = 0, right = arr.length - 1;
    while (left <= right) {
        const mid = left + Math.floor((right - left) / 2);
        if (arr[mid] === target) return mid;
        if (arr[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```

## Lower Bound (First >= target)
```typescript
function lowerBound(arr: number[], target: number): number {
    let left = 0, right = arr.length;
    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        if (arr[mid] < target) left = mid + 1;
        else right = mid;
    }
    return left;
}
```

## Upper Bound (First > target)
```typescript
function upperBound(arr: number[], target: number): number {
    let left = 0, right = arr.length;
    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        if (arr[mid] <= target) left = mid + 1;
        else right = mid;
    }
    return left;
}
```

## Binary Search on Answer - Koko Eating Bananas
```typescript
function minEatingSpeed(piles: number[], h: number): number {
    let left = 1, right = Math.max(...piles);
    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        const hours = piles.reduce((sum, p) => sum + Math.ceil(p / mid), 0);
        if (hours <= h) right = mid;
        else left = mid + 1;
    }
    return left;
}
```

## First & Last Position
```typescript
function searchRange(nums: number[], target: number): number[] {
    const left = lowerBound(nums, target);
    if (left === nums.length || nums[left] !== target) return [-1, -1];
    return [left, upperBound(nums, target) - 1];
}
```