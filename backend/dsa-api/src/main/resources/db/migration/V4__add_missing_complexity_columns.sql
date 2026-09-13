-- V4: Add missing columns to algorithms table for Complexity embeddable
-- The Complexity embeddable requires best_case_time, worst_case_time, average_case_time

ALTER TABLE algorithms ADD COLUMN IF NOT EXISTS best_case_time VARCHAR(100);
ALTER TABLE algorithms ADD COLUMN IF NOT EXISTS worst_case_time VARCHAR(100);
ALTER TABLE algorithms ADD COLUMN IF NOT EXISTS average_case_time VARCHAR(100);

-- Pattern entity also has Complexity embeddable
ALTER TABLE patterns ADD COLUMN IF NOT EXISTS best_case_time VARCHAR(100);
ALTER TABLE patterns ADD COLUMN IF NOT EXISTS worst_case_time VARCHAR(100);
ALTER TABLE patterns ADD COLUMN IF NOT EXISTS average_case_time VARCHAR(100);

-- Template entity also has Complexity embeddable
ALTER TABLE templates ADD COLUMN IF NOT EXISTS best_case_time VARCHAR(100);
ALTER TABLE templates ADD COLUMN IF NOT EXISTS worst_case_time VARCHAR(100);
ALTER TABLE templates ADD COLUMN IF NOT EXISTS average_case_time VARCHAR(100);