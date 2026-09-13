-- V5: Add missing BaseEntity columns (updated_at) to tables that extend BaseEntity
-- entity_references is missing updated_at column

ALTER TABLE entity_references ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- Also check other tables that extend BaseEntity
-- problems, patterns, algorithms, data_structures, templates, concepts
-- all extend BaseEntity and should have created_at and updated_at
-- Let's verify and add missing columns

-- patterns table
ALTER TABLE patterns ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- data_structures table  
ALTER TABLE data_structures ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- templates table
ALTER TABLE templates ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- concepts table
ALTER TABLE concepts ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- curriculum_nodes table
ALTER TABLE curriculum_nodes ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- template_mappings table
ALTER TABLE template_mappings ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- solutions table (missing variation_id column)
ALTER TABLE solutions ADD COLUMN IF NOT EXISTS variation_id UUID REFERENCES problem_variations(id) ON DELETE SET NULL;

-- problem_variations table
ALTER TABLE problem_variations ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- test_cases table
ALTER TABLE test_cases ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- learning_objectives table
ALTER TABLE learning_objectives ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- ai_conversations table
ALTER TABLE ai_conversations ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- ai_messages table
ALTER TABLE ai_messages ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();

-- execution_attempts table (has attempted_at and completed_at but missing created_at and updated_at from BaseEntity, and test_results)
ALTER TABLE execution_attempts ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT NOW();
ALTER TABLE execution_attempts ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW();
ALTER TABLE execution_attempts ADD COLUMN IF NOT EXISTS test_results JSONB;