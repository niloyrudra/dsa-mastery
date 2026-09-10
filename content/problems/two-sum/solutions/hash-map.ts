/**
 * Approach: Hash Map Lookup (BEST for unsorted array)
 * Time: O(n), Space: O(n)
 * 
 * Use a hash map to store value -> index mapping.
 * For each number, check if target - num exists in map.
 * 
 * WHY IT WORKS: Hash map provides O(1) average lookup.
 * We can find the complement in constant time.
 * 
 * BOTTLENECK: Space complexity is O(n) due to hash map storage.
 * 
 * INSIGHT TO NEXT: If array is sorted, we can use two-pointer
 * to achieve O(1) space.
 */
function twoSumHashMap(nums: number[], target: number): number[] {
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