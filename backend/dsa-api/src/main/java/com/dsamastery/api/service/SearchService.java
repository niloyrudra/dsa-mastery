package com.dsamastery.api.service;

import com.dsamastery.api.dto.SearchResultDto;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.entity.Concept;
import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Template;
import com.dsamastery.content.repository.AlgorithmRepository;
import com.dsamastery.content.repository.ConceptRepository;
import com.dsamastery.content.repository.DataStructureRepository;
import com.dsamastery.content.repository.PatternRepository;
import com.dsamastery.content.repository.ProblemRepository;
import com.dsamastery.content.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Full-text search across problems, patterns, algorithms, data structures,
 * concepts and templates. Uses in-memory scoring over the content catalog
 * (small enough for a 150-problem curriculum).
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final ProblemRepository problemRepository;
    private final PatternRepository patternRepository;
    private final AlgorithmRepository algorithmRepository;
    private final DataStructureRepository dataStructureRepository;
    private final ConceptRepository conceptRepository;
    private final TemplateRepository templateRepository;

    @Transactional(readOnly = true)
    public List<SearchResultDto> search(String q, String type, int limit) {
        if (q == null || q.isBlank()) {
            return List.of();
        }
        String query = q.trim().toLowerCase();
        String requestedType = type == null ? "" : type.trim().toUpperCase();

        List<SearchHit> hits = new ArrayList<>();

        if (requestedType.isEmpty() || "PROBLEM".equals(requestedType)) {
            addProblemHits(query, hits);
        }
        if (requestedType.isEmpty() || "PATTERN".equals(requestedType)) {
            addPatternHits(query, hits);
        }
        if (requestedType.isEmpty() || "ALGORITHM".equals(requestedType)) {
            addAlgorithmHits(query, hits);
        }
        if (requestedType.isEmpty() || "DATA_STRUCTURE".equals(requestedType)) {
            addDataStructureHits(query, hits);
        }
        if (requestedType.isEmpty() || "CONCEPT".equals(requestedType)) {
            addConceptHits(query, hits);
        }
        if (requestedType.isEmpty() || "TEMPLATE".equals(requestedType)) {
            addTemplateHits(query, hits);
        }

        return hits.stream()
            .sorted(Comparator.comparingInt((SearchHit hit) -> hit.score).reversed())
            .limit(Math.max(1, Math.min(limit, 50)))
            .map(SearchHit::dto)
            .toList();
    }

    private void addProblemHits(String query, List<SearchHit> hits) {
        for (Problem p : problemRepository.findAll()) {
            StringBuilder searchable = new StringBuilder();
            searchable.append(p.getTitle()).append(' ').append(p.getSlug()).append(' ')
                .append(p.getDescription()).append(' ')
                .append(p.getReasonForSelection() == null ? "" : p.getReasonForSelection()).append(' ')
                .append(p.getStatus() != null ? p.getStatus().name() : "");
            if (p.getPatterns() != null) {
                p.getPatterns().forEach(pat -> searchable.append(' ').append(pat.getName()).append(' ').append(pat.getSlug()));
            }
            if (p.getAlgorithms() != null) {
                p.getAlgorithms().forEach(a -> searchable.append(' ').append(a.getName()).append(' ').append(a.getSlug()));
            }
            if (p.getDataStructures() != null) {
                p.getDataStructures().forEach(ds -> searchable.append(' ').append(ds.getName()).append(' ').append(ds.getSlug()));
            }
            addHit(hits, "PROBLEM", p.getId(), p.getSlug(), p.getTitle(),
                p.getDifficulty() != null ? p.getDifficulty().name() : null,
                searchable.toString(), query);
        }
    }

    private void addPatternHits(String query, List<SearchHit> hits) {
        for (Pattern p : patternRepository.findAll()) {
            String searchable = String.join(" ",
                p.getName(), p.getSlug(),
                nullToEmpty(p.getDefinition()),
                nullToEmpty(p.getWhenToUse()),
                nullToEmpty(p.getWhyItWorks()),
                nullToEmpty(p.getInvariant()),
                nullToEmpty(String.valueOf(p.getRecognitionSignals())));
            addHit(hits, "PATTERN", p.getId(), p.getSlug(), p.getName(), null, searchable, query);
        }
    }

    private void addAlgorithmHits(String query, List<SearchHit> hits) {
        for (Algorithm algorithm : algorithmRepository.findAll()) {
            String searchable = String.join(" ",
                algorithm.getName(), algorithm.getSlug(),
                nullToEmpty(algorithm.getDescription()),
                nullToEmpty(algorithm.getStepByStepReasoning()),
                nullToEmpty(algorithm.getComplexityAnalysis()));
            addHit(hits, "ALGORITHM", algorithm.getId(), algorithm.getSlug(), algorithm.getName(), null, searchable, query);
        }
    }

    private void addDataStructureHits(String query, List<SearchHit> hits) {
        for (DataStructure ds : dataStructureRepository.findAll()) {
            String searchable = String.join(" ",
                ds.getName(), ds.getSlug(),
                nullToEmpty(ds.getDescription()),
                nullToEmpty(ds.getWhyUsefulHere()),
                nullToEmpty(ds.getTradeoffs()),
                nullToEmpty(String.valueOf(ds.getOperations())));
            addHit(hits, "DATA_STRUCTURE", ds.getId(), ds.getSlug(), ds.getName(), null, searchable, query);
        }
    }

    private void addConceptHits(String query, List<SearchHit> hits) {
        for (Concept concept : conceptRepository.findAll()) {
            String searchable = String.join(" ",
                concept.getName(), concept.getSlug(),
                nullToEmpty(concept.getDescription()),
                nullToEmpty(concept.getExplanation()));
            addHit(hits, "CONCEPT", concept.getId(), concept.getSlug(), concept.getName(), null, searchable, query);
        }
    }

    private void addTemplateHits(String query, List<SearchHit> hits) {
        for (Template template : templateRepository.findAll()) {
            String searchable = String.join(" ",
                template.getName(), template.getSlug(),
                template.getLanguage() != null ? template.getLanguage().name() : "",
                nullToEmpty(template.getPurpose()),
                nullToEmpty(template.getWhenToReachForIt()),
                nullToEmpty(template.getMentalModel()),
                nullToEmpty(template.getInvariant()));
            addHit(hits, "TEMPLATE", template.getId(), template.getSlug(), template.getName(), null, searchable, query);
        }
    }

    private void addHit(List<SearchHit> hits, String type, UUID id, String slug, String title,
                        String difficulty, String searchable, String query) {
        if (searchable == null) {
            return;
        }
        String haystack = searchable.toLowerCase();
        if (!haystack.contains(query)) {
            return;
        }
        int score = 0;
        if (slug.toLowerCase().equals(query) || title.toLowerCase().equals(query)) {
            score += 100;
        } else if (slug.toLowerCase().startsWith(query) || title.toLowerCase().startsWith(query)) {
            score += 70;
        } else if (slug.toLowerCase().contains(query) || title.toLowerCase().contains(query)) {
            score += 45;
        } else {
            score += 15;
        }
        int idx = haystack.indexOf(query);
        score += Math.max(0, 50 - idx / 25);

        hits.add(new SearchHit(score, SearchResultDto.builder()
            .type(type)
            .id(id)
            .slug(slug)
            .title(title)
            .snippet(buildSnippet(searchable, query))
            .difficulty(difficulty)
            .build()));
    }

    private String buildSnippet(String searchable, String query) {
        int idx = searchable.toLowerCase().indexOf(query);
        if (idx < 0) {
            idx = 0;
        }
        int start = Math.max(0, idx - 60);
        int end = Math.min(searchable.length(), idx + query.length() + 120);
        String snippet = searchable.substring(start, end).trim();
        return (start > 0 ? "..." : "") + snippet + (end < searchable.length() ? "..." : "");
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private record SearchHit(int score, SearchResultDto dto) {}
}