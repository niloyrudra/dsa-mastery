package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Problem;
import com.dsamastery.common.enums.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, UUID> {
    
    Optional<Problem> findBySlug(String slug);

    boolean existsBySlug(String slug);
    
    List<Problem> findByStatusOrderByOrderIndexAsc(ContentStatus status);
    
    List<Problem> findByPatternsSlug(String patternSlug);
    
    List<Problem> findByAlgorithmsSlug(String algorithmSlug);
    
    List<Problem> findByDataStructuresSlug(String dataStructureSlug);
    
    @Query("""
        SELECT DISTINCT p FROM Problem p
        LEFT JOIN FETCH p.patterns
        LEFT JOIN FETCH p.algorithms
        LEFT JOIN FETCH p.dataStructures
        LEFT JOIN FETCH p.templates
        WHERE p.slug = :slug
        """)
    Optional<Problem> findBySlugWithRelations(@Param("slug") String slug);
    
    @Query("""
        SELECT DISTINCT p FROM Problem p
        LEFT JOIN FETCH p.variations
        LEFT JOIN FETCH p.solutions
        LEFT JOIN FETCH p.testCases
        LEFT JOIN FETCH p.learningObjectives
        LEFT JOIN FETCH p.references
        LEFT JOIN FETCH p.relatedProblems
        LEFT JOIN FETCH p.prerequisites
        WHERE p.slug = :slug
        """)
    Optional<Problem> findBySlugWithDetails(@Param("slug") String slug);
    
    @Query("""
        SELECT p FROM Problem p
        WHERE p.status = :status
        ORDER BY p.orderIndex ASC
        """)
    List<Problem> findPublishedOrdered(@Param("status") ContentStatus status);
}