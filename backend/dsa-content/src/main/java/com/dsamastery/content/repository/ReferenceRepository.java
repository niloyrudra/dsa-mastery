package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, UUID> {
    
    List<Reference> findByProblemsId(UUID problemId);
    
    List<Reference> findByPatternsId(UUID patternId);
    
    List<Reference> findByAlgorithmsId(UUID algorithmId);
}