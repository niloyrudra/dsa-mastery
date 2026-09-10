package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Problem;
import com.dsamastery.common.enums.ContentStatus;
import org.springframework.data.jpa.repository.EntityGraph;
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
    
    @EntityGraph(attributePaths = {
        "patterns",
        "algorithms", 
        "dataStructures",
        "templates",
        "variations",
        "solutions",
        "testCases",
        "learningObjectives",
        "references",
        "relatedProblems",
        "prerequisites"
    })
    @Query("SELECT DISTINCT p FROM Problem p WHERE p.slug = :slug")
    Optional<Problem> findBySlugWithAllRelations(@Param("slug") String slug);
    
    @Query("""
        SELECT p FROM Problem p
        WHERE p.status = :status
        ORDER BY p.orderIndex ASC
        """)
    List<Problem> findPublishedOrdered(@Param("status") ContentStatus status);
}