-- DSA Mastery Platform - Seed Data Migration
-- V2__seed_data.sql

-- Insert Patterns
INSERT INTO patterns (slug, name, category, definition, when_to_use, why_it_works, intuition, invariant, strengths, weaknesses, recognition_signals, common_failure_modes, generalizations, time_complexity, space_complexity, time_reasoning, space_reasoning, status) VALUES
('two-pointers', 'Two Pointers', 'Array', 'Using two pointers to traverse an array from different directions or at different speeds.', 'When dealing with sorted arrays or finding pairs with specific properties.', 'By moving pointers based on comparison, we can eliminate impossible candidates efficiently.', 'Left pointer moves right, right pointer moves left.', 'Array must be sorted for the standard approach.', 'O(n) time, O(1) space for most problems.', 'Requires sorted input; not suitable for unsorted arrays without sorting first.', '["sorted array", "pair sum", "subarray", "palindrome"]', 'Forgetting to sort the array first; incorrect pointer movement logic.', 'Can be extended to three pointers for 3-sum problems.', 'O(n)', 'O(1)', 'Single pass with two pointers.', 'Only uses two pointer variables.', 'PUBLISHED'),
('sliding-window', 'Sliding Window', 'Array', 'Maintaining a window of elements that slides through the array.', 'Finding subarrays/substrings with specific properties (sum, count, etc.).', 'By expanding and contracting the window, we avoid recomputing from scratch.', 'Window boundaries only move forward.', 'Elements must be positive for standard sum problems.', 'O(n) time, O(1) space.', 'Does not work with negative numbers for sum problems without modification.', '["subarray", "substring", "maximum", "minimum", "k elements"]', 'Incorrect window boundary updates; not handling edge cases.', 'Can use variable or fixed window size.', 'O(n)', 'O(1)', 'Each element visited at most twice.', 'Only stores window boundaries and current aggregate.', 'PUBLISHED'),
('binary-search', 'Binary Search', 'Search', 'Divide and conquer search on sorted data by repeatedly halving the search space.', 'Finding an element in sorted array or finding boundary conditions.', 'Each comparison eliminates half of remaining candidates.', 'Search space is halved each iteration.', 'Requires sorted data.', 'O(log n) time, O(1) space.', 'Only works on sorted data; off-by-one errors common.', '["sorted", "find", "boundary", "first occurrence", "last occurrence"]', 'Infinite loops with while(left <= right); incorrect mid calculation.', 'Can find first/last occurrence, lower/upper bounds.', 'O(log n)', 'O(1)', 'Halving search space each step.', 'Constant space for indices.', 'PUBLISHED'),
('prefix-sum', 'Prefix Sum', 'Array', 'Precomputing cumulative sums to answer range sum queries in O(1).', 'Multiple range sum queries on static array.', 'Range sum = prefix[r] - prefix[l-1].', 'Prefix array is monotonically increasing for positive numbers.', 'O(n) preprocessing, O(1) per query.', 'Does not handle updates efficiently.', '["range sum", "subarray sum", "multiple queries"]', 'Off-by-one errors in prefix indices; not handling l=0.', 'Can be extended to 2D prefix sums.', 'O(n) preprocessing', 'O(n) for prefix array', 'Single pass to build prefix array.', 'Stores n+1 prefix sums.', 'PUBLISHED'),
('hash-map', 'Hash Map / Frequency Count', 'Hashing', 'Using hash map to store frequencies or indices for O(1) lookups.', 'Counting frequencies, finding duplicates, two-sum variants.', 'Hash map provides average O(1) insert/lookup.', 'Key-value pairs stored with hash function.', 'O(n) time, O(n) space.', 'Extra space overhead; hash collisions possible.', '["frequency", "count", "two-sum", "anagram", "duplicate"]', 'Using wrong key type; not handling missing keys.', 'Can use defaultdict or getOrDefault for cleaner code.', 'O(n)', 'O(n)', 'Single pass with hash map operations.', 'Stores up to n entries.', 'PUBLISHED');

-- Insert Algorithms
INSERT INTO algorithms (slug, name, description, step_by_step_reasoning, complexity_analysis, time_complexity, space_complexity, time_reasoning, space_reasoning, status) VALUES
('merge-sort', 'Merge Sort', 'Divide and conquer sorting algorithm that divides array into halves, sorts them, and merges.', '1. Divide array into two halves. 2. Recursively sort each half. 3. Merge sorted halves.', 'Divides into log n levels, each level does O(n) work merging.', 'O(n log n)', 'O(n)', 'log n levels × O(n) merge per level.', 'Auxiliary array of size n for merging.', 'PUBLISHED'),
('quick-sort', 'Quick Sort', 'Divide and conquer sorting using pivot partitioning.', '1. Choose pivot. 2. Partition around pivot. 3. Recursively sort partitions.', 'Average case: balanced partitions give log n depth. Worst case: O(n²) when pivot is always min/max.', 'O(n log n) avg, O(n²) worst', 'O(log n) avg', 'Expected balanced partitions.', 'Recursion stack depth.', 'PUBLISHED'),
('dfs', 'Depth First Search', 'Graph traversal exploring as far as possible along each branch before backtracking.', '1. Visit node. 2. Recursively visit unvisited neighbors. 3. Backtrack when no unvisited neighbors.', 'Visits each vertex and edge once.', 'O(V + E)', 'O(V)', 'Each vertex and edge processed once.', 'Recursion stack or explicit stack.', 'PUBLISHED'),
('bfs', 'Breadth First Search', 'Graph traversal exploring all neighbors at current depth before moving deeper.', '1. Enqueue start node. 2. Dequeue and visit. 3. Enqueue unvisited neighbors. 4. Repeat.', 'Visits each vertex and edge once.', 'O(V + E)', 'O(V)', 'Each vertex and edge processed once.', 'Queue stores at most one level of vertices.', 'PUBLISHED'),
('dijkstra', 'Dijkstra''s Algorithm', 'Shortest path from single source in weighted graph with non-negative edges.', '1. Initialize distances. 2. Extract min distance vertex. 3. Relax edges. 4. Repeat.', 'Using priority queue: O((V + E) log V).', 'O((V + E) log V)', 'O(V)', 'Each vertex extracted once, each edge relaxed once.', 'Distance array and priority queue.', 'PUBLISHED');

-- Insert Data Structures
INSERT INTO data_structures (slug, name, description, operations, why_useful_here, tradeoffs, alternatives, status) VALUES
('array', 'Array', 'Contiguous memory storage with O(1) index access.', '{"access": "O(1)", "search": "O(n)", "insert": "O(n)", "delete": "O(n)"}', 'Best for random access and cache-friendly iteration.', 'Insertion/deletion at beginning/middle is O(n).', 'Linked List, Dynamic Array', 'PUBLISHED'),
('linked-list', 'Linked List', 'Nodes connected by pointers with O(1) insertion/deletion at known position.', '{"access": "O(n)", "search": "O(n)", "insert": "O(1)", "delete": "O(1)"}', 'Best for frequent insertions/deletions.', 'No random access; poor cache locality.', 'Array, Dynamic Array', 'PUBLISHED'),
('hash-table', 'Hash Table', 'Key-value storage with average O(1) operations.', '{"access": "O(1)", "search": "O(1)", "insert": "O(1)", "delete": "O(1)"}', 'Best for fast lookups and frequency counting.', 'No ordering; worst case O(n) with collisions.', 'Tree Map, Trie', 'PUBLISHED'),
('stack', 'Stack', 'LIFO data structure with push/pop operations.', '{"push": "O(1)", "pop": "O(1)", "peek": "O(1)"}', 'Best for backtracking, expression evaluation.', 'No random access.', 'Array, Linked List', 'PUBLISHED'),
('queue', 'Queue', 'FIFO data structure with enqueue/dequeue operations.', '{"enqueue": "O(1)", "dequeue": "O(1)", "peek": "O(1)"}', 'Best for BFS, level-order traversal.', 'No random access.', 'Array, Linked List', 'PUBLISHED'),
('heap', 'Heap (Priority Queue)', 'Complete binary tree maintaining heap property.', '{"insert": "O(log n)", "extract": "O(log n)", "peek": "O(1)"}', 'Best for priority-based processing.', 'No efficient search.', 'Balanced BST', 'PUBLISHED'),
('binary-tree', 'Binary Tree', 'Hierarchical structure with at most two children per node.', '{"access": "O(n)", "search": "O(n)", "insert": "O(n)", "delete": "O(n)"}', 'Foundation for BST, heaps, tries.', 'Unbalanced trees degrade to O(n).', 'AVL Tree, Red-Black Tree', 'PUBLISHED'),
('graph', 'Graph', 'Vertices connected by edges.', '{"add_vertex": "O(1)", "add_edge": "O(1)", "traverse": "O(V+E)"}', 'Models relationships and networks.', 'Complex algorithms needed.', 'Adjacency Matrix, Edge List', 'PUBLISHED');

-- Insert some Problems
INSERT INTO problems (slug, title, description, external_url, difficulty, status, order_index, reason_for_selection) VALUES
('two-sum', 'Two Sum', 'Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.', 'https://leetcode.com/problems/two-sum/', 'EASY', 'PUBLISHED', 1, 'Classic hash map problem introducing the two-sum pattern.'),
('valid-palindrome', 'Valid Palindrome', 'A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and removing all non-alphanumeric characters, it reads the same forward and backward.', 'https://leetcode.com/problems/valid-palindrome/', 'EASY', 'PUBLISHED', 2, 'Classic two-pointer string problem.'),
('best-time-to-buy-sell-stock', 'Best Time to Buy and Sell Stock', 'You are given an array prices where prices[i] is the price of a given stock on the ith day. Maximize profit by choosing a single day to buy and a different day to sell.', 'https://leetcode.com/problems/best-time-to-buy-and-sell-stock/', 'EASY', 'PUBLISHED', 3, 'Classic sliding window / single pass minimum tracking.'),
('binary-search', 'Binary Search', 'Given a sorted array of integers nums and an integer target, return the index if target is found, otherwise return -1.', 'https://leetcode.com/problems/binary-search/', 'EASY', 'PUBLISHED', 4, 'Fundamental binary search implementation.'),
('merge-two-sorted-lists', 'Merge Two Sorted Lists', 'Merge two sorted linked lists and return it as a sorted list.', 'https://leetcode.com/problems/merge-two-sorted-lists/', 'EASY', 'PUBLISHED', 5, 'Classic linked list merge operation.');

-- Link Problems to Patterns
INSERT INTO problem_patterns (problem_id, pattern_id) 
SELECT p.id, pat.id FROM problems p, patterns pat
WHERE (p.slug = 'two-sum' AND pat.slug = 'hash-map')
   OR (p.slug = 'valid-palindrome' AND pat.slug = 'two-pointers')
   OR (p.slug = 'best-time-to-buy-sell-stock' AND pat.slug = 'sliding-window')
   OR (p.slug = 'binary-search' AND pat.slug = 'binary-search')
   OR (p.slug = 'merge-two-sorted-lists' AND pat.slug = 'two-pointers');

-- Link Problems to Algorithms
INSERT INTO problem_algorithms (problem_id, algorithm_id)
SELECT p.id, a.id FROM problems p, algorithms a
WHERE (p.slug = 'merge-two-sorted-lists' AND a.slug = 'merge-sort')
   OR (p.slug = 'binary-search' AND a.slug = 'binary-search');

-- Link Problems to Data Structures
INSERT INTO problem_data_structures (problem_id, data_structure_id)
SELECT p.id, ds.id FROM problems p, data_structures ds
WHERE (p.slug = 'two-sum' AND ds.slug = 'hash-table')
   OR (p.slug = 'valid-palindrome' AND ds.slug = 'array')
   OR (p.slug = 'best-time-to-buy-sell-stock' AND ds.slug = 'array')
   OR (p.slug = 'binary-search' AND ds.slug = 'array')
   OR (p.slug = 'merge-two-sorted-lists' AND ds.slug = 'linked-list');

-- Insert Solutions
INSERT INTO solutions (problem_id, name, approach, explanation, why_it_works, why_not_ideal, bottleneck_remaining, insight_to_next, time_complexity, space_complexity, time_reasoning, space_reasoning, best_case_time, worst_case_time, average_case_time, language, code) 
SELECT p.id, 'Hash Map Solution', 'BEST', 
'Use a hash map to store value -> index mapping. For each number, check if target - num exists in map.',
'Hash map provides O(1) lookup, so we find the complement in constant time.',
'Requires O(n) extra space.',
'Space complexity is the bottleneck.',
'Can use two-pointer if array is sorted.',
'O(n)', 'O(n)', 'Single pass with O(1) hash map operations.', 'Hash map stores up to n entries.', 'O(n)', 'O(n)', 'O(n)', 'TYPESCRIPT',
'function twoSum(nums: number[], target: number): number[] {
    const map = new Map<number, number>();
    for (let i = 0; i < nums.length; i++) {
        const complement = target - nums[i];
        if (map.has(complement)) {
            return [map.get(complement)!, i];
        }
        map.set(nums[i], i);
    }
    return [];
}')
FROM problems p WHERE p.slug = 'two-sum';

INSERT INTO solutions (problem_id, name, approach, explanation, why_it_works, why_not_ideal, bottleneck_remaining, insight_to_next, time_complexity, space_complexity, time_reasoning, space_reasoning, best_case_time, worst_case_time, average_case_time, language, code) 
SELECT p.id, 'Two Pointers', 'BEST', 
'Use left and right pointers. Move based on character comparison after filtering.',
'Two pointers converge from both ends, checking palindrome property.',
'Requires O(n) space for filtered string or O(1) with in-place.',
'Space for filtered string.',
'Can do in-place with two pointers on original string.',
'O(n)', 'O(n)', 'Single pass from both ends.', 'Filtered string storage.', 'O(n)', 'O(n)', 'O(n)', 'TYPESCRIPT',
'function isPalindrome(s: string): boolean {
    const filtered = s.toLowerCase().replace(/[^a-z0-9]/g, "");
    let left = 0, right = filtered.length - 1;
    while (left < right) {
        if (filtered[left] !== filtered[right]) return false;
        left++; right--;
    }
    return true;
}')
FROM problems p WHERE p.slug = 'valid-palindrome';

-- Insert Curriculum Nodes
INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Introduction', 'Start your DSA journey', 'CATEGORY', 0, 0, NULL, NULL, NULL, NULL;

INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Two Pointers', 'Master the two pointers technique', 'PATTERN', 1, 1, c.id, NULL, pat.id, NULL
FROM curriculum_nodes c, patterns pat
WHERE c.name = 'Introduction' AND pat.slug = 'two-pointers';

INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Two Sum', 'Classic hash map problem', 'PROBLEM', 2, 2, c.id, p.id, NULL, NULL
FROM curriculum_nodes c, problems p
WHERE c.name = 'Two Pointers' AND p.slug = 'two-sum';

INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Valid Palindrome', 'Two pointers on strings', 'PROBLEM', 3, 2, c.id, p.id, NULL, NULL
FROM curriculum_nodes c, problems p
WHERE c.name = 'Two Pointers' AND p.slug = 'valid-palindrome';

INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Sliding Window', 'Variable and fixed size windows', 'PATTERN', 4, 1, c.id, NULL, pat.id, NULL
FROM curriculum_nodes c, patterns pat
WHERE c.name = 'Introduction' AND pat.slug = 'sliding-window';

INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Best Time to Buy and Sell Stock', 'Single pass minimum tracking', 'PROBLEM', 5, 2, c.id, p.id, NULL, NULL
FROM curriculum_nodes c, problems p
WHERE c.name = 'Sliding Window' AND p.slug = 'best-time-to-buy-sell-stock';

INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Binary Search', 'Divide and conquer search', 'PATTERN', 6, 1, c.id, NULL, pat.id, NULL
FROM curriculum_nodes c, patterns pat
WHERE c.name = 'Introduction' AND pat.slug = 'binary-search';

INSERT INTO curriculum_nodes (name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT 'Binary Search', 'Fundamental implementation', 'PROBLEM', 7, 2, c.id, p.id, NULL, a.id
FROM curriculum_nodes c, problems p, algorithms a
WHERE c.name = 'Binary Search' AND p.slug = 'binary-search' AND a.slug = 'binary-search';

-- Insert Templates
INSERT INTO templates (slug, name, language, purpose, when_to_reach_for_it, prerequisites, mental_model, code, explanation, invariant, common_modifications, common_mistakes, time_complexity, space_complexity, time_reasoning, space_reasoning, pattern_id, status)
SELECT 'two-pointers-template-ts', 'Two Pointers Template', 'TYPESCRIPT', 'Template for two pointer problems', 'When you have a sorted array or need to find pairs', 'Understanding of array indexing', 'Two pointers moving towards each other or in same direction', 
$$function twoPointers(arr, condition) {
    let left = 0;
    let right = arr.length - 1;
    while (left < right) {
        if (condition(arr[left], arr[right])) {
            return [arr[left], arr[right]];
        }
        if (arr[left] + arr[right] < target) left++;
        else right--;
    }
    return null;
}$$,
'Initialize left at start, right at end. Move based on comparison.',
'Left <= Right',
'Can move both pointers, or just one.',
'Forgetting to check bounds; infinite loop.',
'O(n)', 'O(1)', 'Each element visited at most once.', 'Only two indices.', pat.id, 'PUBLISHED'
FROM patterns pat WHERE pat.slug = 'two-pointers';

-- Insert Mastery Records (default for all entities)
INSERT INTO mastery_records (entity_type, entity_id, status, mastery_score)
SELECT 'PATTERN', id, 'NOT_STARTED', 0 FROM patterns;

INSERT INTO mastery_records (entity_type, entity_id, status, mastery_score)
SELECT 'ALGORITHM', id, 'NOT_STARTED', 0 FROM algorithms;

INSERT INTO mastery_records (entity_type, entity_id, status, mastery_score)
SELECT 'DATA_STRUCTURE', id, 'NOT_STARTED', 0 FROM data_structures;

INSERT INTO mastery_records (entity_type, entity_id, status, mastery_score)
SELECT 'PROBLEM', id, 'NOT_STARTED', 0 FROM problems;