/**
 * Approach: Brute Force (WORST)
 * Time: O(n²), Space: O(1)
 * 
 * Compare every pair of elements.
 * 
 * WHY IT WORKS: Checks all possible pairs exhaustively.
 * 
 * BOTTLENECK: Quadratic time is too slow for n=10⁵.
 * 
 * INSIGHT TO NEXT: Hash set reduces to O(n) with O(n) space.
 * Sorting reduces to O(n log n) with O(1) space.
 */
function containsDuplicateBrute(nums: number[]): boolean {
    for (let i = 0; i < nums.length; i++) {
        for (let j = i + 1; j < nums.length; j++) {
            if (nums[i] === nums[j]) return true;
        }
    }
    return false;
}