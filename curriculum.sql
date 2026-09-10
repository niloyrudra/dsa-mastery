-- Curriculum Nodes
INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Introduction', 'Start your DSA journey', 'CATEGORY', 0, 0, NULL, NULL, NULL, NULL;

INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Two Pointers', 'Master the two pointers technique', 'PATTERN', 1, 1, c.id, NULL, pat.id, NULL
FROM curriculum_nodes c, patterns pat
WHERE c.name = 'Introduction' AND pat.slug = 'two-pointers';

INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Two Sum', 'Classic hash map problem', 'PROBLEM', 2, 2, c.id, p.id, NULL, NULL
FROM curriculum_nodes c, problems p
WHERE c.name = 'Two Pointers' AND p.slug = 'two-sum';

INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Valid Palindrome', 'Two pointers on strings', 'PROBLEM', 3, 2, c.id, p.id, NULL, NULL
FROM curriculum_nodes c, problems p
WHERE c.name = 'Two Pointers' AND p.slug = 'valid-palindrome';

INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Sliding Window', 'Variable and fixed size windows', 'PATTERN', 4, 1, c.id, NULL, pat.id, NULL
FROM curriculum_nodes c, patterns pat
WHERE c.name = 'Introduction' AND pat.slug = 'sliding-window';

INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Best Time to Buy and Sell Stock', 'Single pass minimum tracking', 'PROBLEM', 5, 2, c.id, p.id, NULL, NULL
FROM curriculum_nodes c, problems p
WHERE c.name = 'Sliding Window' AND p.slug = 'best-time-to-buy-sell-stock';

INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Binary Search', 'Divide and conquer search', 'PATTERN', 6, 1, c.id, NULL, pat.id, NULL
FROM curriculum_nodes c, patterns pat
WHERE c.name = 'Introduction' AND pat.slug = 'binary-search';

INSERT INTO curriculum_nodes (id, name, description, type, order_index, depth, parent_id, problem_id, pattern_id, algorithm_id)
SELECT gen_random_uuid(), 'Binary Search', 'Fundamental implementation', 'PROBLEM', 7, 2, c.id, p.id, NULL, a.id
FROM curriculum_nodes c, problems p, algorithms a
WHERE c.name = 'Binary Search' AND p.slug = 'binary-search' AND a.slug = 'binary-search';