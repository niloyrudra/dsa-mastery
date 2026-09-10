/**
 * Approach: Two Pointers with Filtering (BEST)
 * Time: O(n), Space: O(n) for filtered string or O(1) with in-place
 * 
 * Filter string to alphanumeric lowercase, then use two pointers.
 * 
 * WHY IT WORKS: Two pointers converge from both ends, checking palindrome property.
 * 
 * BOTTLENECK: Space for filtered string (O(n)).
 * 
 * INSIGHT TO NEXT: Can do in-place with two pointers on original string,
 * skipping non-alphanumeric characters on the fly.
 */
function isPalindromeFiltered(s: string): boolean {
    const filtered = s.toLowerCase().replace(/[^a-z0-9]/g, "");
    let left = 0;
    let right = filtered.length - 1;
    
    while (left < right) {
        if (filtered[left] !== filtered[right]) return false;
        left++;
        right--;
    }
    return true;
}