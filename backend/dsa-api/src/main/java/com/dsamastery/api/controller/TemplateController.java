package com.dsamastery.api.controller;

import com.dsamastery.api.dto.TemplateDetailDto;
import com.dsamastery.api.mapper.TemplateMapper;
import com.dsamastery.content.entity.Template;
import com.dsamastery.content.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/templates")
@RequiredArgsConstructor
public class TemplateController {
    
    private final TemplateService templateService;
    private final TemplateMapper templateMapper;
    
    @GetMapping
    public ResponseEntity<List<TemplateSummaryDto>> getTemplates(
            @RequestParam(required = false) UUID pattern,
            @RequestParam(required = false) String language) {
        
        List<Template> templates;
        if (pattern != null) {
            templates = templateService.getTemplatesByPattern(pattern);
        } else if (language != null) {
            templates = templateService.getTemplatesByLanguage(language);
        } else {
            // Return all published templates - would need to add method to service
            templates = templateService.getTemplatesByPattern(null); // placeholder
        }
        
        return ResponseEntity.ok(templates.stream()
            .map(this::toSummary)
            .toList());
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<TemplateDetailDto> getTemplate(@PathVariable String slug) {
        Optional<Template> templateOpt = templateService.getTemplateBySlug(slug);
        if (templateOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(templateMapper.toDetailDto(templateOpt.get()));
    }
    
    private TemplateSummaryDto toSummary(Template t) {
        return new TemplateSummaryDto(
            t.getId(),
            t.getSlug(),
            t.getName(),
            t.getLanguage().name(),
            t.getPurpose(),
            t.getPattern() != null ? t.getPattern().getName() : null
        );
    }
    
    record TemplateSummaryDto(
        UUID id,
        String slug,
        String name,
        String language,
        String purpose,
        String patternName
    ) {}
}