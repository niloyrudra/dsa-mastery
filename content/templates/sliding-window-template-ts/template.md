---
id: "sliding-window-template-ts"
name: "Sliding Window Template"
language: "TYPESCRIPT"
purpose: "Templates for fixed and variable sliding window problems"
when_to_reach_for_it: "Subarray/substring problems with specific properties (sum, count, distinct chars)"
prerequisites: "Understanding of window boundaries, hash maps for counting"
mental_model: "Window expands right, contracts left when condition violated. Aggregate maintained incrementally."
explanation: "Fixed window: maintain sum of k elements, update by adding new, removing old. Variable window: expand right, contract left when invalid, track valid windows."
invariant: "Window [left, right] always satisfies problem constraint (or we're shrinking to make it valid)."
common_modifications: "Can track count, sum, distinct count, frequency map. Fixed vs variable window based on problem."
common_mistakes: "Not updating left correctly when contracting; forgetting to update aggregate when removing; infinite loop in while."
time_complexity: "O(n)"
space_complexity: "O(k) or O(n) for hash map"
time_reasoning: "Each element enters and leaves window at most once."
space_reasoning: "Hash map stores at most window size or alphabet size."
pattern: "sliding-window-variable"
status: "PUBLISHED"
---
# Sliding Window Templates (TypeScript)

## Fixed Window - Max Sum Subarray of Size K
```typescript
function maxSubarraySum(arr: number[], k: number): number {
    let windowSum = 0, maxSum = -Infinity;
    for (let i = 0; i < arr.length; i++) {
        windowSum += arr[i];
        if (i >= k) windowSum -= arr[i - k];
        if (i >= k - 1) maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

## Variable Window - Longest Substring Without Repeating
```typescript
function lengthOfLongestSubstring(s: string): number {
    const map = new Map<string, number>();
    let left = 0, maxLen = 0;
    for (let right = 0; right < s.length; right++) {
        const c = s[right];
        if (map.has(c) && map.get(c)! >= left) left = map.get(c)! + 1;
        map.set(c, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```

## Variable Window - Minimum Window Substring
```typescript
function minWindow(s: string, t: string): string {
    const need = new Map<string, number>();
    for (const c of t) need.set(c, (need.get(c) || 0) + 1);

    let left = 0, have = 0, minLen = Infinity, start = 0;
    const window = new Map<string, number>();

    for (let right = 0; right < s.length; right++) {
        const c = s[right];
        window.set(c, (window.get(c) || 0) + 1);
        if (need.has(c) && window.get(c)! === need.get(c)!) have++;

        while (have === need.size) {
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }
            const lc = s[left];
            window.set(lc, window.get(lc)! - 1);
            if (need.has(lc) && window.get(lc)! < need.get(lc)!) have--;
            left++;
        }
    }
    return minLen === Infinity ? "" : s.slice(start, start + minLen);
}
```