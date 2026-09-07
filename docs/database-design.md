# DSA Mastery Platform - Database Design

## Database: PostgreSQL 16

### Connection Configuration
```yaml
# docker-compose.yml
postgres:
  image: postgres:16-alpine
  environment:
    POSTGRES_DB: dsa_mastery
    POSTGRES_USER: dsa_user
    POSTGRES_PASSWORD: ${DB_PASSWORD}
  volumes:
    - postgres_data:/var/lib/postgresql/data
    - ./init.sql:/docker-entrypoint-initdb.d/init.sql
  ports:
    - "5432:5432"
  healthcheck:
    test: ["CMD-SHELL", "pg_isready -U dsa_user -d dsa_mastery"]
    interval: 10s
    timeout: 5s
    retries: 5
```

### Extensions Required
```sql
-- init.sql
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pg_trgm";      -- trigram search
CREATE EXTENSION IF NOT EXISTS "btree_gin";    -- GIN index on arrays
CREATE EXTENSION IF NOT EXISTS "citext";       -- case-insensitive text
```

### Core Tables

#### problems
```sql
CREATE TABLE problems (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug CITEXT NOT NULL UNIQUE,
    title VARCHAR(500) NOT NULL,
    description TEXT NOT NULL,
    external_url VARCHAR(1000),
    difficulty VARCHAR(10) NOT NULL CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD')),
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT' CHECK (status IN ('DRAFT', 'RESEARCHED', 'GENERATED', 'VALIDATED', 'REVIEWED', 'PUBLISHED')),
    order_index INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_problems_slug ON problems(slug);
CREATE INDEX idx_problems_status ON problems(status);
CREATE INDEX idx_problems_difficulty ON problems(difficulty);
CREATE INDEX idx_problems_order ON problems(order_index);
```

#### patterns
```sql
CREATE TABLE patterns (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug CITEXT NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    definition TEXT NOT NULL,
    when_to_use TEXT NOT NULL,
    why_it_works TEXT NOT NULL,
    intuition TEXT,
    invariant TEXT,
    strengths TEXT,
    weaknesses TEXT,
    recognition_signals JSONB NOT NULL DEFAULT '[]',
    common_failure_modes TEXT,
    generalizations TEXT,
    time_complexity VARCHAR(100),
    space_complexity VARCHAR(100),
    time_reasoning TEXT,
    space_reasoning TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_patterns_slug ON patterns(slug);
CREATE INDEX idx_patterns_signals ON patterns USING GIN (recognition_signals);
```

#### algorithms
```sql
CREATE TABLE algorithms (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug CITEXT NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    step_by_step_reasoning TEXT NOT NULL,
    complexity_analysis TEXT NOT NULL,
    time_complexity VARCHAR(100),
    space_complexity VARCHAR(100),
    time_reasoning TEXT,
    space_reasoning TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_algorithms_slug ON algorithms(slug);
```

#### data_structures
```sql
CREATE TABLE data_structures (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug CITEXT NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    operations JSONB NOT NULL DEFAULT '{}',     -- {"lookup": "O(1)", "insert": "O(1)"}
    why_useful_here TEXT,
    tradeoffs TEXT,
    alternatives TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_data_structures_slug ON data_structures(slug);
```

#### templates
```sql
CREATE TABLE templates (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug CITEXT NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    language VARCHAR(20) NOT NULL CHECK (language IN ('TYPESCRIPT', 'JAVASCRIPT', 'PYTHON')),
    purpose TEXT NOT NULL,
    when_to_reach_for_it TEXT NOT NULL,
    prerequisites TEXT,
    mental_model TEXT NOT NULL,
    code TEXT NOT NULL,                          -- annotated with [1], [2], etc.
    explanation TEXT NOT NULL,                   -- maps markers to logic
    invariant TEXT NOT NULL,
    common_modifications TEXT,
    common_mistakes TEXT,
    time_complexity VARCHAR(100),
    space_complexity VARCHAR(100),
    time_reasoning TEXT,
    space_reasoning TEXT,
    pattern_id UUID REFERENCES patterns(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_templates_slug ON templates(slug);
CREATE INDEX idx_templates_pattern ON templates(pattern_id);
```

#### solutions
```sql
CREATE TABLE solutions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    name VARCHAR(200) NOT NULL,
    approach VARCHAR(10) NOT NULL CHECK (approach IN ('WORST', 'BETTER', 'BEST')),
    explanation TEXT NOT NULL,
    why_it_works TEXT NOT NULL,
    why_not_ideal TEXT,
    bottleneck_remaining TEXT,
    insight_to_next TEXT,
    time_complexity VARCHAR(100) NOT NULL,
    space_complexity VARCHAR(100) NOT NULL,
    time_reasoning TEXT NOT NULL,
    space_reasoning TEXT NOT NULL,
    language VARCHAR(20) NOT NULL CHECK (language IN ('TYPESCRIPT', 'JAVASCRIPT', 'PYTHON')),
    code TEXT NOT NULL,
    template_id UUID REFERENCES templates(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_solutions_problem ON solutions(problem_id);
CREATE INDEX idx_solutions_approach ON solutions(approach);
```

#### problem_variations
```sql
CREATE TABLE problem_variations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    base_problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    what_changed TEXT NOT NULL,
    original_approach_works BOOLEAN NOT NULL DEFAULT FALSE,
    what_breaks TEXT,
    new_constraint_matters TEXT,
    data_structure_changes BOOLEAN NOT NULL DEFAULT FALSE,
    algorithm_changes BOOLEAN NOT NULL DEFAULT FALSE,
    complexity_changes TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_variations_base_problem ON problem_variations(base_problem_id);
```

#### test_cases
```sql
CREATE TABLE test_cases (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    problem_id UUID REFERENCES problems(id) ON DELETE CASCADE,
    variation_id UUID REFERENCES problem_variations(id) ON DELETE CASCADE,
    name VARCHAR(200) NOT NULL,
    input JSONB NOT NULL,
    expected_output JSONB NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL CHECK (type IN ('BASIC', 'EDGE_CASE', 'PERFORMANCE', 'CUSTOM')),
    is_hidden BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    
    CONSTRAINT chk_test_case_parent CHECK (
        (problem_id IS NOT NULL AND variation_id IS NULL) OR
        (problem_id IS NULL AND variation_id IS NOT NULL)
    )
);

CREATE INDEX idx_test_cases_problem ON test_cases(problem_id);
CREATE INDEX idx_test_cases_variation ON test_cases(variation_id);
```

#### concepts
```sql
CREATE TABLE concepts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug CITEXT NOT NULL UNIQUE,
    name VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    explanation TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_concepts_slug ON concepts(slug);
```

#### references
```sql
CREATE TABLE references (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(500) NOT NULL,
    url VARCHAR(1000) NOT NULL,
    type VARCHAR(30) NOT NULL CHECK (type IN ('OFFICIAL_DOCS', 'PRIMARY_SOURCE', 'UNIVERSITY', 'BOOK', 'TECH_RESOURCE', 'PROBLEM_SOURCE')),
    relevant_concept VARCHAR(200),
    verifies TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
```

#### learning_objectives
```sql
CREATE TABLE learning_objectives (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    description TEXT NOT NULL,
    bloom_level VARCHAR(20) NOT NULL CHECK (bloom_level IN ('REMEMBER', 'UNDERSTAND', 'APPLY', 'ANALYZE', 'EVALUATE', 'CREATE')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_learning_objectives_problem ON learning_objectives(problem_id);
```

---

### Junction Tables (Many-to-Many)

```sql
-- Problem ↔ Pattern
CREATE TABLE problem_patterns (
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    PRIMARY KEY (problem_id, pattern_id)
);

-- Problem ↔ Algorithm
CREATE TABLE problem_algorithms (
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    algorithm_id UUID NOT NULL REFERENCES algorithms(id) ON DELETE CASCADE,
    PRIMARY KEY (problem_id, algorithm_id)
);

-- Problem ↔ DataStructure
CREATE TABLE problem_data_structures (
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    data_structure_id UUID NOT NULL REFERENCES data_structures(id) ON DELETE CASCADE,
    PRIMARY KEY (problem_id, data_structure_id)
);

-- Problem ↔ Template
CREATE TABLE problem_templates (
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    template_id UUID NOT NULL REFERENCES templates(id) ON DELETE CASCADE,
    PRIMARY KEY (problem_id, template_id)
);

-- Problem ↔ Problem (related)
CREATE TABLE problem_related (
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    related_problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    relationship_type VARCHAR(30) NOT NULL DEFAULT 'RELATED_TO',
    PRIMARY KEY (problem_id, related_problem_id)
);

-- Problem ↔ Concept (prerequisites)
CREATE TABLE problem_prerequisites (
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    concept_id UUID NOT NULL REFERENCES concepts(id) ON DELETE CASCADE,
    PRIMARY KEY (problem_id, concept_id)
);

-- Pattern ↔ Template
CREATE TABLE pattern_templates (
    pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    template_id UUID NOT NULL REFERENCES templates(id) ON DELETE CASCADE,
    PRIMARY KEY (pattern_id, template_id)
);

-- Pattern ↔ Algorithm
CREATE TABLE pattern_algorithms (
    pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    algorithm_id UUID NOT NULL REFERENCES algorithms(id) ON DELETE CASCADE,
    PRIMARY KEY (pattern_id, algorithm_id)
);

-- Pattern ↔ DataStructure
CREATE TABLE pattern_data_structures (
    pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    data_structure_id UUID NOT NULL REFERENCES data_structures(id) ON DELETE CASCADE,
    PRIMARY KEY (pattern_id, data_structure_id)
);

-- Pattern ↔ Pattern (sub/related)
CREATE TABLE pattern_relationships (
    pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    related_pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    type VARCHAR(20) NOT NULL CHECK (type IN ('SUB_PATTERN', 'RELATED')),
    PRIMARY KEY (pattern_id, related_pattern_id)
);

-- Algorithm ↔ DataStructure
CREATE TABLE algorithm_data_structures (
    algorithm_id UUID NOT NULL REFERENCES algorithms(id) ON DELETE CASCADE,
    data_structure_id UUID NOT NULL REFERENCES data_structures(id) ON DELETE CASCADE,
    PRIMARY KEY (algorithm_id, data_structure_id)
);

-- Algorithm ↔ Template
CREATE TABLE algorithm_templates (
    algorithm_id UUID NOT NULL REFERENCES algorithms(id) ON DELETE CASCADE,
    template_id UUID NOT NULL REFERENCES templates(id) ON DELETE CASCADE,
    PRIMARY KEY (algorithm_id, template_id)
);

-- Concept ↔ Concept (prerequisites)
CREATE TABLE concept_prerequisites (
    concept_id UUID NOT NULL REFERENCES concepts(id) ON DELETE CASCADE,
    prerequisite_id UUID NOT NULL REFERENCES concepts(id) ON DELETE CASCADE,
    PRIMARY KEY (concept_id, prerequisite_id)
);

-- Concept ↔ Pattern
CREATE TABLE concept_patterns (
    concept_id UUID NOT NULL REFERENCES concepts(id) ON DELETE CASCADE,
    pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    PRIMARY KEY (concept_id, pattern_id)
);

-- Problem ↔ Reference
CREATE TABLE problem_references (
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    reference_id UUID NOT NULL REFERENCES references(id) ON DELETE CASCADE,
    PRIMARY KEY (problem_id, reference_id)
);

-- Pattern ↔ Reference
CREATE TABLE pattern_references (
    pattern_id UUID NOT NULL REFERENCES patterns(id) ON DELETE CASCADE,
    reference_id UUID NOT NULL REFERENCES references(id) ON DELETE CASCADE,
    PRIMARY KEY (pattern_id, reference_id)
);

-- Algorithm ↔ Reference
CREATE TABLE algorithm_references (
    algorithm_id UUID NOT NULL REFERENCES algorithms(id) ON DELETE CASCADE,
    reference_id UUID NOT NULL REFERENCES references(id) ON DELETE CASCADE,
    PRIMARY KEY (algorithm_id, reference_id)
);
```

---

### Progress Tracking Tables

#### mastery_records
```sql
CREATE TABLE mastery_records (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    entity_type VARCHAR(30) NOT NULL CHECK (entity_type IN ('PROBLEM', 'PATTERN', 'ALGORITHM', 'CONCEPT')),
    entity_id UUID NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'NOT_STARTED' CHECK (status IN ('NOT_STARTED', 'LEARNING', 'PRACTICING', 'SOLVED', 'MASTERED', 'NEEDS_REVIEW')),
    mastery_score INTEGER NOT NULL DEFAULT 0 CHECK (mastery_score BETWEEN 0 AND 100),
    
    -- Problem-specific
    viewed BOOLEAN NOT NULL DEFAULT FALSE,
    explanation_viewed BOOLEAN NOT NULL DEFAULT FALSE,
    template_viewed BOOLEAN NOT NULL DEFAULT FALSE,
    attempts INTEGER NOT NULL DEFAULT 0,
    passed_tests BOOLEAN NOT NULL DEFAULT FALSE,
    solution_revealed BOOLEAN NOT NULL DEFAULT FALSE,
    optimal_understood BOOLEAN NOT NULL DEFAULT FALSE,
    variations_attempted BOOLEAN NOT NULL DEFAULT FALSE,
    last_practiced TIMESTAMPTZ,
    mastered_at TIMESTAMPTZ,
    
    -- Pattern/Concept-specific
    problems_solved INTEGER NOT NULL DEFAULT 0,
    problems_attempted INTEGER NOT NULL DEFAULT 0,
    concept_proficiency DOUBLE PRECISION NOT NULL DEFAULT 0.0 CHECK (concept_proficiency BETWEEN 0.0 AND 1.0),
    
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    
    UNIQUE (entity_type, entity_id)
);

CREATE INDEX idx_mastery_entity ON mastery_records(entity_type, entity_id);
CREATE INDEX idx_mastery_status ON mastery_records(status);
```

#### execution_attempts
```sql
CREATE TABLE execution_attempts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    problem_id UUID NOT NULL REFERENCES problems(id) ON DELETE CASCADE,
    user_id UUID NOT NULL DEFAULT '00000000-0000-0000-0000-000000000001',  -- single user for now
    language VARCHAR(20) NOT NULL CHECK (language IN ('TYPESCRIPT', 'JAVASCRIPT', 'PYTHON')),
    code TEXT NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('COMPILING', 'RUNNING', 'PASSED', 'FAILED', 'ERROR', 'TIMEOUT')),
    passed_tests INTEGER NOT NULL DEFAULT 0,
    total_tests INTEGER NOT NULL DEFAULT 0,
    execution_time_ms BIGINT,
    memory_bytes BIGINT,
    output TEXT,
    error TEXT,
    attempted_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_execution_problem ON execution_attempts(problem_id);
CREATE INDEX idx_execution_user ON execution_attempts(user_id);
CREATE INDEX idx_execution_status ON execution_attempts(status);
CREATE INDEX idx_execution_attempted ON execution_attempts(attempted_at DESC);
```

#### ai_conversations
```sql
CREATE TABLE ai_conversations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    problem_id UUID REFERENCES problems(id) ON DELETE SET NULL,
    model_used VARCHAR(100) NOT NULL,
    model_role VARCHAR(20) NOT NULL CHECK (model_role IN ('FAST', 'CODING', 'REASONING')),
    started_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    last_message_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ai_conv_problem ON ai_conversations(problem_id);
```

#### ai_messages
```sql
CREATE TABLE ai_messages (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    conversation_id UUID NOT NULL REFERENCES ai_conversations(id) ON DELETE CASCADE,
    role VARCHAR(20) NOT NULL CHECK (role IN ('USER', 'ASSISTANT', 'SYSTEM')),
    content TEXT NOT NULL,
    type VARCHAR(30) NOT NULL CHECK (type IN ('QUESTION', 'HINT', 'EXPLANATION', 'CODE_REVIEW', 'COMPLEXITY_ANALYSIS', 'VARIATION')),
    sequence INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_ai_msg_conversation ON ai_messages(conversation_id, sequence);
```

---

### Curriculum Graph Tables

#### curriculum_nodes
```sql
CREATE TABLE curriculum_nodes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(200) NOT NULL,
    description TEXT,
    type VARCHAR(20) NOT NULL CHECK (type IN ('CATEGORY', 'PATTERN', 'ALGORITHM', 'PROBLEM')),
    order_index INTEGER NOT NULL DEFAULT 0,
    depth INTEGER NOT NULL DEFAULT 0,
    parent_id UUID REFERENCES curriculum_nodes(id) ON DELETE CASCADE,
    problem_id UUID REFERENCES problems(id) ON DELETE SET NULL,
    pattern_id UUID REFERENCES patterns(id) ON DELETE SET NULL,
    algorithm_id UUID REFERENCES algorithms(id) ON DELETE SET NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_curriculum_parent ON curriculum_nodes(parent_id);
CREATE INDEX idx_curriculum_type ON curriculum_nodes(type);
CREATE INDEX idx_curriculum_problem ON curriculum_nodes(problem_id);
CREATE INDEX idx_curriculum_pattern ON curriculum_nodes(pattern_id);
```

#### relationships (generic polymorphic)
```sql
CREATE TABLE relationships (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    source_type VARCHAR(30) NOT NULL,
    source_id UUID NOT NULL,
    target_type VARCHAR(30) NOT NULL,
    target_id UUID NOT NULL,
    type VARCHAR(30) NOT NULL CHECK (type IN ('USES', 'REQUIRES', 'VARIATION_OF', 'RELATED_TO', 'PREREQUISITE_OF', 'IMPLEMENTS')),
    description TEXT,
    strength INTEGER NOT NULL DEFAULT 5 CHECK (strength BETWEEN 1 AND 10),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    
    UNIQUE (source_type, source_id, target_type, target_id, type)
);

CREATE INDEX idx_rel_source ON relationships(source_type, source_id);
CREATE INDEX idx_rel_target ON relationships(target_type, target_id);
CREATE INDEX idx_rel_type ON relationships(type);
```

---

### Template Mappings

#### template_mappings
```sql
CREATE TABLE template_mappings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    template_id UUID NOT NULL REFERENCES templates(id) ON DELETE CASCADE,
    solution_id UUID NOT NULL REFERENCES solutions(id) ON DELETE CASCADE,
    template_marker INTEGER NOT NULL,           -- e.g., 1, 2, 3 from [1], [2]
    solution_code_snippet TEXT NOT NULL,
    explanation TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    
    UNIQUE (template_id, solution_id, template_marker)
);

CREATE INDEX idx_tmpl_map_solution ON template_mappings(solution_id);
```

---

### Materialized Views (for Graph Queries)

```sql
-- Problem-to-Problem via shared patterns
CREATE MATERIALIZED VIEW problem_similarity AS
SELECT 
    p1.id AS problem_id_1,
    p2.id AS problem_id_2,
    COUNT(DISTINCT pp.pattern_id) AS shared_patterns,
    ARRAY_AGG(DISTINCT pat.slug) AS pattern_slugs
FROM problems p1
JOIN problem_patterns pp1 ON p1.id = pp1.problem_id
JOIN problem_patterns pp2 ON pp1.pattern_id = pp2.pattern_id
JOIN problems p2 ON pp2.problem_id = p2.id
JOIN patterns pat ON pat.id = pp1.pattern_id
WHERE p1.id < p2.id
GROUP BY p1.id, p2.id;

CREATE UNIQUE INDEX idx_prob_sim ON problem_similarity(problem_id_1, problem_id_2);

-- Refresh: REFRESH MATERIALIZED VIEW CONCURRENTLY problem_similarity;
```

---

### Migration Strategy

- Use Flyway for schema migrations
- Location: `src/main/resources/db/migration/`
- Naming: `V{version}__{description}.sql`
- Baseline: `V1__initial_schema.sql` (this document)
- Run on application startup

---

### Backup/Export

```bash
# Full backup
docker exec postgres pg_dump -U dsa_user dsa_mastery > backup_$(date +%Y%m%d).sql

# Data-only for content migration
docker exec postgres pg_dump -U dsa_user --data-only --table=problems --table=patterns ... dsa_mastery > content_export.sql
```

---

### Performance Considerations

1. **Indexes**: All foreign keys indexed, slug columns unique indexed, JSONB GIN indexes for search
2. **Materialized Views**: Pre-computed problem similarity for graph
3. **Partitioning**: Not needed for single-user scale (< 10K rows per table)
4. **Connection Pool**: HikariCP (Spring Boot default), 10 connections max
5. **Query Optimization**: Use `@EntityGraph` for fetch plans, avoid N+1