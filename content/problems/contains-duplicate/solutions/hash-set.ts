/**
 * Approach: Hash Set (BEST)
 * Time: O(n), Space: O(n)
 * 
 * Use a hash set to track seen elements.
 * For each number, check if it's already in set.
 * If yes, return true. Otherwise add to set.
 * 
 * WHY IT WORKS: Hash set provides O(1) average lookup/insert.
 * We can detect duplicates in single pass.
 * 
 * BOTTLENECK: Space complexity O(n) in worst case.
 * 
 * INSIGHT TO NEXT: For bounded range, can use bit vector.
 * For duplicate within k distance, use sliding window.
 */
function containsDuplicate(nums: number[]): boolean {
    const seen = new Set<number>();
    
    for (const num of nums) {
        if (seen.has(num)) return true;
        seen.add(num);
    }
    
    return false;
}