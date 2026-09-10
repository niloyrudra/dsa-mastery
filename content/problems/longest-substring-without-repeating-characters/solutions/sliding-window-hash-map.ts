/**
 * Approach: Sliding Window with Hash Map (BEST)
 * Time: O(n), Space: O(min(n, m)) where m is charset size
 * 
 * Expand right, contract left when duplicate found.
 * Use hash map to track last seen index of each character.
 * 
 * WHY IT WORKS: When duplicate found at right, left jumps to
 * max(left, lastSeen[char] + 1) - skipping all invalid substrings.
 * 
 * INVARIANT: Window [left, right] always contains unique characters.
 * 
 * INSIGHT TO NEXT: Can use array instead of hash map for fixed charset (ASCII).
 */
function lengthOfLongestSubstring(s: string): number {
    const lastSeen = new Map<string, number>();
    let left = 0;
    let maxLen = 0;
    
    for (let right = 0; right < s.length; right++) {
        const char = s[right];
        
        if (lastSeen.has(char)) {
            left = Math.max(left, lastSeen.get(char)! + 1);
        }
        
        lastSeen.set(char, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    
    return maxLen;
}