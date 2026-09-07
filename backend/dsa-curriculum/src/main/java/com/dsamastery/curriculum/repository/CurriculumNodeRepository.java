package com.dsamastery.curriculum.repository;

import com.dsamastery.curriculum.entity.CurriculumNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurriculumNodeRepository extends JpaRepository<CurriculumNode, UUID> {
    
    Optional<CurriculumNode> findByProblemId(UUID problemId);
    
    Optional<CurriculumNode> findByPatternId(UUID patternId);
    
    Optional<CurriculumNode> findByAlgorithmId(UUID algorithmId);
    
    @Query("""
        SELECT cn FROM CurriculumNode cn
        WHERE cn.parent IS NULL
        ORDER BY cn.orderIndex ASC
        """)
    List<CurriculumNode> findRootNodes();
    
    @Query("""
        SELECT cn FROM CurriculumNode cn
        WHERE cn.parent.id = :parentId
        ORDER BY cn.orderIndex ASC
        """)
    List<CurriculumNode> findByParentId(@Param("parentId") UUID parentId);
}