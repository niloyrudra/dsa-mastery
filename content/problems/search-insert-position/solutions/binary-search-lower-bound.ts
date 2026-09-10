/**
 * Approach: Binary Search - Lower Bound (BEST)
 * Time: O(log n), Space: O(1)
 * 
 * Find leftmost position where target could be inserted.
 * This is equivalent to lower_bound in C++.
 * 
 * WHY IT WORKS: Binary search maintains invariant that
 * answer is in [left, right). When target not found, left
 * converges to insertion point.
 * 
 * INVARIANT: At each step, the insertion point is in [left, right).
 * 
 * KEY INSIGHT: Using left < right (not left <= right) ensures
 * left is the insertion point when loop exits.
 */
function searchInsert(nums: number[], target: number): number {
    let left = 0;
    let right = nums.length;
    
    while (left < right) {
        const mid = left + Math.floor((right - left) / 2);
        
        if (nums[mid] < target) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }
    
    return left;
}