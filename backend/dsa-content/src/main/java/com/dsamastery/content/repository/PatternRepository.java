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
    
    boolean existsBySlug(String slug);
    
    List<Pattern> findByStatusOrderByNameAsc(ContentStatus status);
    
    List<Pattern> findByStatus(ContentStatus status);
    
    List<Pattern> findByCategoryAndStatusOrderByNameAsc(String category, ContentStatus status);
    
    List<Pattern> findByCategoryAndStatus(String category, ContentStatus status);
    
    @Query("""
        SELECT DISTINCT p FROM Pattern p
        WHERE p.slug = :slug
        """)
    Optional<Pattern> findBySlugWithRelations(@Param("slug") String slug);
}