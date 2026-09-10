CREATE TABLE entity_references (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(500) NOT NULL,
    url VARCHAR(1000) NOT NULL,
    type VARCHAR(30) NOT NULL,
    relevant_concept VARCHAR(200),
    verifies VARCHAR(200),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE algorithm_references (
    algorithm_id UUID NOT NULL REFERENCES algorithms(id),
    reference_id UUID NOT NULL REFERENCES entity_references(id),
    PRIMARY KEY (algorithm_id, reference_id)
);

CREATE TABLE pattern_references (
    pattern_id UUID NOT NULL REFERENCES patterns(id),
    reference_id UUID NOT NULL REFERENCES entity_references(id),
    PRIMARY KEY (pattern_id, reference_id)
);