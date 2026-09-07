package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.common.enums.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, UUID> {
    
    Optional<Algorithm> findBySlug(String slug);
    
    List<Algorithm> findByStatusOrderByNameAsc(ContentStatus status);
    
    @Query("""
        SELECT DISTINCT a FROM Algorithm a
        LEFT JOIN FETCH a.dataStructures
        LEFT JOIN FETCH a.templates
        LEFT JOIN FETCH a.problems
        LEFT JOIN FETCH a.patterns
        LEFT JOIN FETCH a.references
        WHERE a.slug = :slug
        """)
    Optional<Algorithm> findBySlugWithRelations(@Param("slug") String slug);
}