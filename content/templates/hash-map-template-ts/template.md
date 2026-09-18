---
id: "hash-map-template-ts"
name: "Hash Map Template"
language: "TYPESCRIPT"
purpose: "Templates for hash map / frequency counting problems"
when_to_reach_for_it: "Two sum, frequency counting, anagrams, duplicates, subarray sum equals k"
prerequisites: "Understanding of hash maps, O(1) average operations"
mental_model: "Map key to value/index/count. Single pass with lookups."
code: |
// Two Sum - Hash Map
function twoSum(nums: number[], target: number): number[] {
    const map = new Map<number, number>();

    for (let i = 0; i < nums.length; i++) {
        const complement = target - nums[i];
        if (map.has(complement)) {
            return [map.get(complement)!, i];
        }
        map.set(nums[i], i);
    }

    return [];
}

// Frequency Counter - Group Anagrams
function groupAnagrams(strs: string[]): string[][] {
    const map = new Map<string, string[]>();

    for (const str of strs) {
        const key = str.split('').sort().join('');
        if (!map.has(key)) map.set(key, []);
        map.get(key)!.push(str);
    }

    return Array.from(map.values());
}

// Subarray Sum Equals K - Prefix Sum + Hash Map
function subarraySum(nums: number[], k: number): number {
    const map = new Map<number, number>();
    map.set(0, 1); // prefix sum 0 occurs once
    let count = 0, sum = 0;

    for (const num of nums) {
        sum += num;
        if (map.has(sum - k)) {
            count += map.get(sum - k)!;
        }
        map.set(sum, (map.get(sum) || 0) + 1);
    }

    return count;
}

// Top K Frequent Elements
function topKFrequent(nums: number[], k: number): number[] {
    const freq = new Map<number, number>();
    for (const num of nums) freq.set(num, (freq.get(num) || 0) + 1);

    const buckets: number[][] = Array(nums.length + 1).fill(null).map(() => []);
    for (const [num, f] of freq) buckets[f].push(num);

    const result: number[] = [];
    for (let i = buckets.length - 1; i >= 0 && result.length < k; i--) {
        result.push(...buckets[i]);
    }
    return result;
}

explanation: "Hash map provides O(1) average lookup/insert. Use for complement finding, frequency counting, prefix sum tracking."
invariant: "Map contains processed elements with their indices/counts/frequencies."
common_modifications: "Can use Map, Object, or custom hash. Can store indices, counts, or lists. Bucket sort for top-k."
common_mistakes: "Not checking map before inserting (overwrites); using wrong key type; forgetting to initialize map with base case (prefix sum 0)."
time_complexity: "O(n)"
space_complexity: "O(n)"
time_reasoning: "Single pass with O(1) hash map operations."
space_reasoning: "Hash map stores up to n entries."
pattern: "hash-map-lookup"
status: "PUBLISHED"
---
# Hash Map Templates (TypeScript)

## Two Sum
```typescript
function twoSum(nums: number[], target: number): number[] {
    const map = new Map<number, number>();
    for (let i = 0; i < nums.length; i++) {
        const complement = target - nums[i];
        if (map.has(complement)) return [map.get(complement)!, i];
        map.set(nums[i], i);
    }
    return [];
}
```

## Group Anagrams
```typescript
function groupAnagrams(strs: string[]): string[][] {
    const map = new Map<string, string[]>();
    for (const str of strs) {
        const key = str.split('').sort().join('');
        if (!map.has(key)) map.set(key, []);
        map.get(key)!.push(str);
    }
    return Array.from(map.values());
}
```

## Subarray Sum Equals K
```typescript
function subarraySum(nums: number[], k: number): number {
    const map = new Map<number, number>();
    map.set(0, 1);
    let sum = 0, count = 0;
    for (const num of nums) {
        sum += num;
        if (map.has(sum - k)) count += map.get(sum - k)!;
        map.set(sum, (map.get(sum) || 0) + 1);
    }
    return count;
}
```

## Top K Frequent Elements
```typescript
function topKFrequent(nums: number[], k: number): number[] {
    const freq = new Map<number, number>();
    for (const num of nums) freq.set(num, (freq.get(num) || 0) + 1);

    const buckets: number[][] = Array(nums.length + 1).fill(null).map(() => []);
    for (const [num, f] of freq) buckets[f].push(num);

    const result: number[] = [];
    for (let i = buckets.length - 1; i >= 0 && result.length < k; i--) {
        result.push(...buckets[i]);
    }
    return result;
}
```