/**
 * Approach: Two Pointers In-Place (BEST - O(1) space)
 * Time: O(n), Space: O(1)
 * 
 * Use two pointers on original string, skip non-alphanumeric on the fly.
 * 
 * WHY IT WORKS: Same two-pointer logic, but skip invalid chars during comparison.
 * No extra space needed.
 * 
 * INVARIANT: left only moves right, right only moves left.
 * All checked pairs are valid alphanumeric matches.
 */
function isPalindromeInPlace(s: string): boolean {
    let left = 0;
    let right = s.length - 1;
    
    while (left < right) {
        // Skip non-alphanumeric from left
        while (left < right && !isAlphanumeric(s[left])) {
            left++;
        }
        // Skip non-alphanumeric from right
        while (left < right && !isAlphanumeric(s[right])) {
            right--;
        }
        
        if (left < right) {
            if (s[left].toLowerCase() !== s[right].toLowerCase()) {
                return false;
            }
            left++;
            right--;
        }
    }
    
    return true;
}

function isAlphanumeric(char: string): boolean {
    return /^[a-z0-9]$/i.test(char);
}