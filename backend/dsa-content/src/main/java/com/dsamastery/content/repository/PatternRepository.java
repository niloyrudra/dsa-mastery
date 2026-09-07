package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Pattern;
import com.dsamastery.common.enums.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatternRepository extends JpaRepository<Pattern, UUID> {
    
    Optional<Pattern> findBySlug(String slug);
    
    List<Pattern> findByStatusOrderByNameAsc(ContentStatus status);
    
    @Query("""
        SELECT DISTINCT p FROM Pattern p
        LEFT JOIN FETCH p.templates
        LEFT JOIN FETCH p.algorithms
        LEFT JOIN FETCH p.dataStructures
        LEFT JOIN FETCH p.subPatterns
        LEFT JOIN FETCH p.problems
        LEFT JOIN FETCH p.references
        WHERE p.slug = :slug
        """)
    Optional<Pattern> findBySlugWithRelations(@Param("slug") String slug);
}