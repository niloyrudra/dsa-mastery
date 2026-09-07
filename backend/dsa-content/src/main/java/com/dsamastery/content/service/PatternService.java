package com.dsamastery.content.service;

import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.repository.PatternRepository;
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
public class PatternService {
    
    private final PatternRepository patternRepository;
    
    public List<Pattern> getPublishedPatterns() {
        return patternRepository.findByStatusOrderByNameAsc(ContentStatus.PUBLISHED);
    }
    
    public Optional<Pattern> getPatternBySlug(String slug) {
        return patternRepository.findBySlugWithRelations(slug);
    }
    
    public Optional<Pattern> getPatternById(UUID id) {
        return patternRepository.findById(id);
    }
}