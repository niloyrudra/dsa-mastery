package com.dsamastery.content.service;

import com.dsamastery.content.entity.Template;
import com.dsamastery.content.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateService {
    
    private final TemplateRepository templateRepository;
    
    public List<Template> getTemplatesByPattern(UUID patternId) {
        return templateRepository.findByPatternId(patternId);
    }
    
    public List<Template> getTemplatesByLanguage(String language) {
        return templateRepository.findByLanguage(language);
    }
    
    public Optional<Template> getTemplateBySlug(String slug) {
        return templateRepository.findBySlugWithRelations(slug);
    }
    
    public Optional<Template> getTemplateById(UUID id) {
        return templateRepository.findById(id);
    }
}