package com.dsamastery.content.repository;

import com.dsamastery.content.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<Template, UUID> {
    
    Optional<Template> findBySlug(String slug);
    
    boolean existsBySlug(String slug);
    
    List<Template> findByPatternId(UUID patternId);
    
    List<Template> findByLanguage(String language);
    
    @Query("""
        SELECT DISTINCT t FROM Template t
        LEFT JOIN FETCH t.pattern
        LEFT JOIN FETCH t.mappings
        LEFT JOIN FETCH t.problems
        WHERE t.slug = :slug
        """)
    Optional<Template> findBySlugWithRelations(@Param("slug") String slug);
}