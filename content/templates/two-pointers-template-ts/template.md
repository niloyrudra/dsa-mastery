---
id: "two-pointers-template-ts"
name: "Two Pointers Template"
language: "TYPESCRIPT"
purpose: "Template for two pointer problems on sorted arrays"
when_to_reach_for_it: "When you have a sorted array and need to find pairs/triplets with specific sum, or palindrome problems"
prerequisites: "Understanding of array indexing, sorted array properties"
mental_model: "Two pointers moving towards each other or in same direction based on comparison"
explanation: "Initialize left at start, right at end. Move based on comparison with target. For 3Sum, fix one element and use two pointers on remaining."
invariant: "Left <= Right. Sum of elements at left and right compared to target."
common_modifications: "Can move both pointers, or just one. Can use for 3Sum, 4Sum by fixing elements. Can count pairs instead of returning indices."
common_mistakes: "Forgetting to check bounds; infinite loop when left >= right; not handling duplicates in 3Sum."
time_complexity: "O(n)"
space_complexity: "O(1)"
time_reasoning: "Each element visited at most once by either pointer."
space_reasoning: "Only two indices stored."
pattern: "two-pointer"
status: "PUBLISHED"
---
# Two Pointers Template (TypeScript)

## Basic Two Sum (Sorted Array)
```typescript
function twoSumSorted(arr: number[], target: number): number[] {
    let left = 0, right = arr.length - 1;
    while (left < right) {
        const sum = arr[left] + arr[right];
        if (sum === target) return [left, right];
        if (sum < target) left++;
        else right--;
    }
    return [];
}
```

## 3Sum
```typescript
function threeSum(nums: number[]): number[][] {
    nums.sort((a, b) => a - b);
    const result: number[][] = [];

    for (let i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] === nums[i - 1]) continue;
        let left = i + 1, right = nums.length - 1;
        while (left < right) {
            const sum = nums[i] + nums[left] + nums[right];
            if (sum === 0) {
                result.push([nums[i], nums[left], nums[right]]);
                while (left < right && nums[left] === nums[left + 1]) left++;
                while (left < right && nums[right] === nums[right - 1]) right--;
                left++; right--;
            } else if (sum < 0) left++;
            else right--;
        }
    }
    return result;
}
```

## Palindrome Check
```typescript
function isPalindrome(s: string): boolean {
    const filtered = s.toLowerCase().replace(/[^a-z0-9]/g, "");
    let left = 0, right = filtered.length - 1;
    while (left < right) {
        if (filtered[left] !== filtered[right]) return false;
        left++; right--;
    }
    return true;
}
```