-- V3: Add join tables referenced by the JPA entity model that were not
-- included in V1.
--
-- (The Reference entity maps to `entity_references`, which V1 now creates
-- directly; this migration only adds the two entity-referenced join tables
-- that V1 omitted. Hibernate would auto-create them with ddl-auto=update,
-- but defining them via Flyway keeps the schema fully managed.)

CREATE TABLE IF NOT EXISTS data_structure_references (
    data_structure_id UUID NOT NULL REFERENCES data_structures(id) ON DELETE CASCADE,
    reference_id UUID NOT NULL REFERENCES entity_references(id) ON DELETE CASCADE,
    PRIMARY KEY (data_structure_id, reference_id)
);

CREATE INDEX idx_ds_ref_reference ON data_structure_references(reference_id);

CREATE TABLE IF NOT EXISTS concept_references (
    concept_id UUID NOT NULL REFERENCES concepts(id) ON DELETE CASCADE,
    reference_id UUID NOT NULL REFERENCES entity_references(id) ON DELETE CASCADE,
    PRIMARY KEY (concept_id, reference_id)
);

CREATE INDEX idx_concept_ref_reference ON concept_references(reference_id);