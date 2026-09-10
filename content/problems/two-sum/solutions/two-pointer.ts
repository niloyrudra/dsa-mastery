/**
 * Approach: Two Pointers (BEST for sorted array)
 * Time: O(n), Space: O(1)
 * 
 * Use left and right pointers at opposite ends.
 * Move pointers based on sum comparison with target.
 * 
 * WHY IT WORKS: Since array is sorted, moving left++ increases sum,
 * moving right-- decreases sum. We can eliminate impossible pairs.
 * 
 * INVARIANT: At each step, all pairs outside [left, right] 
 * have been checked and rejected.
 * 
 * NOTE: This approach requires the input array to be sorted.
 * If not sorted, must sort first (O(n log n) time, loses original indices).
 */
function twoSumTwoPointer(nums: number[], target: number): number[] {
    // Create array of (value, originalIndex) pairs
    const indexedNums = nums.map((val, idx) => ({ val, idx }));
    indexedNums.sort((a, b) => a.val - b.val);
    
    let left = 0;
    let right = indexedNums.length - 1;
    
    while (left < right) {
        const sum = indexedNums[left].val + indexedNums[right].val;
        
        if (sum === target) {
            return [indexedNums[left].idx, indexedNums[right].idx];
        } else if (sum < target) {
            left++;
        } else {
            right--;
        }
    }
    
    return [];
}