/**
 * Approach: Sort and Compare (BETTER for space)
 * Time: O(n log n), Space: O(1) or O(n) depending on sort
 * 
 * Sort the array, then check adjacent elements for equality.
 * 
 * WHY IT WORKS: Sorting groups equal elements together.
 * Adjacent comparison finds duplicates.
 * 
 * BOTTLENECK: Time complexity O(n log n) due to sort.
 * 
 * INSIGHT TO NEXT: Hash set achieves O(n) time with O(n) space tradeoff.
 */
function containsDuplicateSort(nums: number[]): boolean {
    nums.sort((a, b) => a - b);
    
    for (let i = 1; i < nums.length; i++) {
        if (nums[i] === nums[i - 1]) return true;
    }
    
    return false;
}