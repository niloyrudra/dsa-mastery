package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Concept;
import com.dsamastery.common.enums.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, UUID> {
    
    Optional<Concept> findBySlug(String slug);
    
    List<Concept> findByStatusOrderByNameAsc(ContentStatus status);
    
    @Query("""
        SELECT DISTINCT c FROM Concept c
        LEFT JOIN FETCH c.prerequisites
        LEFT JOIN FETCH c.patterns
        LEFT JOIN FETCH c.problems
        WHERE c.slug = :slug
        """)
    Optional<Concept> findBySlugWithRelations(@Param("slug") String slug);
}