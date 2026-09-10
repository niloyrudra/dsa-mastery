/**
 * Approach: Two Pointers Greedy (BEST)
 * Time: O(n), Space: O(1)
 * 
 * Start with widest container, move pointer at smaller height.
 * 
 * WHY IT WORKS: Moving the larger height pointer can never increase area
 * because width decreases and height is bounded by smaller height.
 * Moving smaller height might find a taller line.
 * 
 * PROOF: If height[left] < height[right], any container with left and right'
 * (where right' < right) has width < current width, and height ≤ height[left].
 * So area < current area. Thus moving right is useless. Move left instead.
 * 
 * INVARIANT: At each step, the maximum area for current [left, right]
 * has been considered. All wider containers have been evaluated.
 */
function maxArea(height: number[]): number {
    let left = 0;
    let right = height.length - 1;
    let maxArea = 0;
    
    while (left < right) {
        const currentArea = Math.min(height[left], height[right]) * (right - left);
        maxArea = Math.max(maxArea, currentArea);
        
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return maxArea;
}