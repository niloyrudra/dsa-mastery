package com.dsamastery.content.service;

import com.dsamastery.content.entity.Concept;
import com.dsamastery.content.repository.ConceptRepository;
import com.dsamastery.common.enums.ContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConceptService {
    
    private final ConceptRepository conceptRepository;
    
    public List<Concept> getPublishedConcepts() {
        return conceptRepository.findByStatusOrderByNameAsc(ContentStatus.PUBLISHED);
    }
    
    public Optional<Concept> getConceptBySlug(String slug) {
        return conceptRepository.findBySlugWithRelations(slug);
    }
    
    public Optional<Concept> getConceptById(UUID id) {
        return conceptRepository.findById(id);
    }
}