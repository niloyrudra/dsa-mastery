-- Add slug columns to curriculum_nodes for frontend routing
-- V6__add_slug_columns_to_curriculum_nodes.sql

ALTER TABLE curriculum_nodes 
ADD COLUMN IF NOT EXISTS problem_slug VARCHAR(200),
ADD COLUMN IF NOT EXISTS pattern_slug VARCHAR(200),
ADD COLUMN IF NOT EXISTS algorithm_slug VARCHAR(200);

-- Add indexes for slug columns
CREATE INDEX IF NOT EXISTS idx_curriculum_problem_slug ON curriculum_nodes(problem_slug);
CREATE INDEX IF NOT EXISTS idx_curriculum_pattern_slug ON curriculum_nodes(pattern_slug);
CREATE INDEX IF NOT EXISTS idx_curriculum_algorithm_slug ON curriculum_nodes(algorithm_slug);