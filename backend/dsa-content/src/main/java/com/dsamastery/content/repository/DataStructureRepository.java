package com.dsamastery.content.repository;

import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.common.enums.ContentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataStructureRepository extends JpaRepository<DataStructure, UUID> {
    
    Optional<DataStructure> findBySlug(String slug);
    
    List<DataStructure> findByStatusOrderByNameAsc(ContentStatus status);
    
    @Query("""
        SELECT DISTINCT d FROM DataStructure d
        LEFT JOIN FETCH d.problems
        LEFT JOIN FETCH d.patterns
        LEFT JOIN FETCH d.algorithms
        WHERE d.slug = :slug
        """)
    Optional<DataStructure> findBySlugWithRelations(@Param("slug") String slug);
}