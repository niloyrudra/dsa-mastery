package com.dsamastery.templates.service;

import com.dsamastery.content.entity.Template;
import com.dsamastery.content.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Template-specific business logic: instantiation, matching, cross-language variants.
 * Complements the basic CRUD in dsa-content TemplateService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateService {

    private final TemplateRepository templateRepository;

    /**
     * Instantiate a template by replacing marker placeholders with actual values.
     * Markers in template code are in format [1], [2], etc.
     */
    public String instantiateTemplate(String templateSlug, Map<Integer, String> markerValues) {
        return templateRepository.findBySlug(templateSlug)
            .map(template -> instantiate(template, markerValues))
            .orElseThrow(() -> new IllegalArgumentException("Template not found: " + templateSlug));
    }

    /**
     * Instantiate a template object with marker values.
     */
    public String instantiate(Template template, Map<Integer, String> markerValues) {
        if (template.getCode() == null) return "";
        
        String code = template.getCode();
        if (markerValues == null || markerValues.isEmpty()) {
            return code;
        }

        // Replace markers [1], [2], etc. with provided values
        for (Map.Entry<Integer, String> entry : markerValues.entrySet()) {
            String placeholder = "[" + entry.getKey() + "]";
            code = code.replace(placeholder, entry.getValue());
        }
        return code;
    }

    /**
     * Get all templates that can be used for a given problem (via problem-templates relationship).
     */
    public List<Template> getTemplatesForProblem(UUID problemId) {
        return templateRepository.findByProblemsId(problemId);
    }

    /**
     * Get templates that match the given language and pattern.
     */
    public List<Template> getTemplatesByLanguageAndPattern(String language, UUID patternId) {
        return templateRepository.findByLanguageAndPatternId(language, patternId);
    }

    /**
     * Get all language variants of a template (same purpose, different languages).
     */
    public Map<String, Template> getLanguageVariants(String templateSlug) {
        return templateRepository.findBySlug(templateSlug)
            .map(base -> {
                String purpose = base.getPurpose();
                if (purpose == null) return Map.of(base.getLanguage().name(), base);
                
                List<Template> variants = templateRepository.findByPurposeAndStatus(purpose, 
                    com.dsamastery.common.enums.ContentStatus.PUBLISHED);
                return variants.stream()
                    .collect(Collectors.toMap(
                        t -> t.getLanguage().name(),
                        t -> t,
                        (a, b) -> a
                    ));
            })
            .orElse(Map.of());
    }

    /**
     * Find templates by purpose keywords.
     */
    public List<Template> findByPurposeKeywords(Collection<String> keywords) {
        if (keywords == null || keywords.isEmpty()) return List.of();
        
        List<Template> allPublished = templateRepository.findByStatus(
            com.dsamastery.common.enums.ContentStatus.PUBLISHED);
        
        return allPublished.stream()
            .filter(t -> t.getPurpose() != null && 
                keywords.stream().anyMatch(k -> t.getPurpose().toLowerCase().contains(k.toLowerCase())))
            .collect(Collectors.toList());
    }

    /**
     * Compare templates for the same pattern across languages.
     */
    public TemplateComparison compareTemplates(UUID patternId) {
        List<Template> templates = templateRepository.findByPatternId(patternId);
        
        Map<String, List<Template>> byLanguage = templates.stream()
            .collect(Collectors.groupingBy(t -> t.getLanguage().name()));
        
        Map<String, Integer> complexityByLang = new HashMap<>();
        for (Map.Entry<String, List<Template>> entry : byLanguage.entrySet()) {
            if (!entry.getValue().isEmpty() && entry.getValue().get(0).getComplexity() != null) {
                String timeComplexity = entry.getValue().get(0).getComplexity().getTimeComplexity();
                complexityByLang.put(entry.getKey(), parseComplexity(timeComplexity));
            }
        }
        
        return new TemplateComparison(patternId, byLanguage, complexityByLang);
    }

    /**
     * Extract marker explanations from a template's code.
     * Markers are in format [1] explanation text
     */
    public Map<Integer, String> extractMarkerExplanations(Template template) {
        Map<Integer, String> markers = new LinkedHashMap<>();
        if (template.getExplanation() == null) return markers;
        
        Matcher m = Pattern.compile("^\\[(\\d+)\\]\\s+(.+)$", Pattern.MULTILINE)
            .matcher(template.getExplanation());
        while (m.find()) {
            markers.put(Integer.parseInt(m.group(1)), m.group(2).trim());
        }
        return markers;
    }

    /**
     * Get template usage statistics.
     */
    public List<TemplateStats> getTemplateStatistics() {
        List<Template> allPublished = templateRepository.findByStatus(
            com.dsamastery.common.enums.ContentStatus.PUBLISHED);
        
        return allPublished.stream()
            .map(t -> new TemplateStats(
                t.getSlug(),
                t.getName(),
                t.getLanguage().name(),
                t.getPattern() != null ? t.getPattern().getName() : "N/A",
                t.getProblems().size(),
                t.getMappings().size(),
                t.getCode() != null ? t.getCode().lines().count() : 0
            ))
            .sorted(Comparator.comparing(TemplateStats::problemCount).reversed())
            .collect(Collectors.toList());
    }

    private int parseComplexity(String complexity) {
        if (complexity == null) return 999;
        if (complexity.contains("O(1)")) return 1;
        if (complexity.contains("O(log n)")) return 2;
        if (complexity.contains("O(n)")) return 3;
        if (complexity.contains("O(n log n)")) return 4;
        if (complexity.contains("O(n²)") || complexity.contains("O(n^2)")) return 5;
        if (complexity.contains("O(2^n)")) return 6;
        return 999;
    }

    // DTOs
    public record TemplateComparison(
        UUID patternId,
        Map<String, List<Template>> templatesByLanguage,
        Map<String, Integer> complexityRankByLanguage
    ) {}

    public record TemplateStats(
        String slug,
        String name,
        String language,
        String patternName,
        int problemCount,
        int mappingCount,
        long codeLines
    ) {}
}