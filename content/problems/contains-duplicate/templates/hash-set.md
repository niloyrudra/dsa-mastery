---
id: "contains-duplicate-hash-set"
name: "Contains Duplicate - Hash Set Template"
language: "TYPESCRIPT"
purpose: "Template for duplicate detection using hash set"
when_to_reach_for_it: "Any problem requiring duplicate detection or frequency counting"
prerequisites: "Understanding of hash set O(1) operations"
mental_model: "Track seen elements. If element already seen, it's a duplicate."
code: |
// Contains Duplicate - Hash Set
function containsDuplicate(nums: number[]): boolean {
    const seen = new Set<number>();
    
    for (const num of nums) {
        if (seen.has(num)) return true;
        seen.add(num);
    }
    
    return false;
}

// Contains Duplicate II - Sliding Window Hash Map
function containsNearbyDuplicate(nums: number[], k: number): boolean {
    const map = new Map<number, number>();
    
    for (let i = 0; i < nums.length; i++) {
        if (map.has(nums[i]) && i - map.get(nums[i])! <= k) {
            return true;
        }
        map.set(nums[i], i);
    }
    
    return false;
}

// Find All Duplicates
function findDuplicates(nums: number[]): number[] {
    const seen = new Set<number>();
    const duplicates: number[] = [];
    
    for (const num of nums) {
        if (seen.has(num)) {
            duplicates.push(num);
        } else {
            seen.add(num);
        }
    }
    
    return duplicates;
}

explanation: "Hash set provides O(1) lookup to track seen elements. For index-distance constraints, use hash map value->index."
invariant: "Set contains all unique elements processed so far."
common_modifications: "Can use Map for index tracking (Contains Duplicate II). Can return all duplicates instead of boolean."
common_mistakes: "Not checking before adding; using wrong data structure for index queries; forgetting to handle empty array."
time_complexity: "O(n)"
space_complexity: "O(n)"
pattern: "hash-map-lookup"
status: "PUBLISHED"
---
# Contains Duplicate Template (TypeScript)

## Basic Duplicate Detection
```typescript
function containsDuplicate(nums: number[]): boolean {
    const seen = new Set<number>();
    for (const num of nums) {
        if (seen.has(num)) return true;
        seen.add(num);
    }
    return false;
}
```

## Contains Duplicate II (Within K Distance)
```typescript
function containsNearbyDuplicate(nums: number[], k: number): boolean {
    const map = new Map<number, number>();
    for (let i = 0; i < nums.length; i++) {
        if (map.has(nums[i]) && i - map.get(nums[i])! <= k) return true;
        map.set(nums[i], i);
    }
    return false;
}
```

## Find All Duplicates
```typescript
function findDuplicates(nums: number[]): number[] {
    const seen = new Set<number>();
    const duplicates: number[] = [];
    for (const num of nums) {
        if (seen.has(num)) duplicates.push(num);
        else seen.add(num);
    }
    return duplicates;
}
```