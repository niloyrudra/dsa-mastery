CREATE TABLE IF NOT EXISTS problem_references (
    problem_id UUID NOT NULL REFERENCES problems(id),
    reference_id UUID NOT NULL REFERENCES entity_references(id),
    PRIMARY KEY (problem_id, reference_id)
);

CREATE TABLE IF NOT EXISTS problem_prerequisites (
    problem_id UUID NOT NULL REFERENCES problems(id),
    prerequisite_id UUID NOT NULL REFERENCES problems(id),
    PRIMARY KEY (problem_id, prerequisite_id)
);

CREATE TABLE IF NOT EXISTS problem_related (
    problem_id UUID NOT NULL REFERENCES problems(id),
    related_id UUID NOT NULL REFERENCES problems(id),
    PRIMARY KEY (problem_id, related_id)
);

CREATE TABLE IF NOT EXISTS pattern_relationships (
    pattern_id UUID NOT NULL REFERENCES patterns(id),
    related_id UUID NOT NULL REFERENCES patterns(id),
    PRIMARY KEY (pattern_id, related_id)
);

CREATE TABLE IF NOT EXISTS algorithm_data_structures (
    algorithm_id UUID NOT NULL REFERENCES algorithms(id),
    data_structure_id UUID NOT NULL REFERENCES data_structures(id),
    PRIMARY KEY (algorithm_id, data_structure_id)
);

CREATE TABLE IF NOT EXISTS concept_patterns (
    concept_id UUID NOT NULL REFERENCES concepts(id),
    pattern_id UUID NOT NULL REFERENCES patterns(id),
    PRIMARY KEY (concept_id, pattern_id)
);

CREATE TABLE IF NOT EXISTS concept_prerequisites (
    concept_id UUID NOT NULL REFERENCES concepts(id),
    prerequisite_id UUID NOT NULL REFERENCES concepts(id),
    PRIMARY KEY (concept_id, prerequisite_id)
);

CREATE TABLE IF NOT EXISTS relationships (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    source_type VARCHAR(50) NOT NULL,
    source_id UUID NOT NULL,
    target_type VARCHAR(50) NOT NULL,
    target_id UUID NOT NULL,
    relationship_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);