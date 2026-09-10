package com.dsamastery.content.repository;

import com.dsamastery.content.entity.TemplateMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemplateMappingRepository extends JpaRepository<TemplateMapping, UUID> {

    List<TemplateMapping> findByTemplateId(UUID templateId);

    List<TemplateMapping> findBySolutionId(UUID solutionId);

    List<TemplateMapping> findByTemplateSlug(String templateSlug);
}