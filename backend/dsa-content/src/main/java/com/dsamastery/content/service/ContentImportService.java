package com.dsamastery.content.service;

import com.dsamastery.content.entity.*;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.repository.*;
import com.dsamastery.common.enums.*;
import com.dsamastery.common.entity.Complexity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentImportService {

    private final ProblemRepository problemRepository;
    private final PatternRepository patternRepository;
    private final AlgorithmRepository algorithmRepository;
    private final DataStructureRepository dataStructureRepository;
    private final ConceptRepository conceptRepository;
    private final TemplateRepository templateRepository;
    private final SolutionRepository solutionRepository;
    private final TestCaseRepository testCaseRepository;
    private final ReferenceRepository referenceRepository;
    private final LearningObjectiveRepository learningObjectiveRepository;
    private final ProblemVariationRepository variationRepository;
    private final TemplateMappingRepository templateMappingRepository;
    private final Yaml yaml = new Yaml();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final java.util.regex.Pattern frontmatterPattern = java.util.regex.Pattern.compile("^---\\n(.*?)\\n---", java.util.regex.Pattern.DOTALL);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public ImportResult importContent(Path contentRoot) throws IOException {
        ImportResult result = new ImportResult();
        
        // 1. Import patterns
        importPatterns(contentRoot.resolve("patterns"), result);
        
        // 2. Import algorithms
        importAlgorithms(contentRoot.resolve("algorithms"), result);
        
        // 3. Import data structures
        importDataStructures(contentRoot.resolve("data-structures"), result);
        
        // 4. Import concepts
        importConcepts(contentRoot.resolve("concepts"), result);
        
        // 5. Import templates
        importTemplates(contentRoot.resolve("templates"), result);
        
        // 6. Import problems
        importProblems(contentRoot.resolve("problems"), result);
        
        return result;
    }

    private void importPatterns(Path patternsDir, ImportResult result) throws IOException {
        if (!Files.exists(patternsDir)) {
            log.warn("Patterns directory not found: {}", patternsDir);
            return;
        }
        
        try (var stream = Files.list(patternsDir)) {
            stream.filter(Files::isDirectory)
                .forEach(patternDir -> {
                    try {
                        importPattern(patternDir, result);
                    } catch (Exception e) {
                        result.addError("pattern", patternDir.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importPattern(Path patternDir, ImportResult result) throws IOException {
        Path patternFile = patternDir.resolve("pattern.md");
        if (!Files.exists(patternFile)) {
            result.addError("pattern", patternDir.getFileName().toString(), "pattern.md not found");
            return;
        }
        
        String content = Files.readString(patternFile);
        Map<String, Object> frontmatter = parseFrontmatter(content);
        String markdownContent = extractMarkdownContent(content);
        
        // Parse markdown sections from the body
        Map<String, String> sections = parseMarkdownSections(markdownContent);
        
        String slug = (String) frontmatter.get("id");
        
        // Find existing pattern or create new one
        Pattern pattern = patternRepository.findBySlug(slug).orElse(new Pattern());
        boolean isNew = pattern.getId() == null;
        
        // Update pattern fields
        pattern.setSlug(slug);
        pattern.setName((String) frontmatter.get("name"));
        pattern.setCategory((String) frontmatter.get("category"));
        pattern.setDefinition(sections.getOrDefault("definition", markdownContent));
        pattern.setWhenToUse(sections.getOrDefault("when_to_use", ""));
        pattern.setWhyItWorks(sections.getOrDefault("why_it_works", ""));
        pattern.setIntuition(sections.getOrDefault("intuition", ""));
        pattern.setInvariant(sections.getOrDefault("invariant", ""));
        pattern.setStrengths(sections.getOrDefault("strengths", ""));
        pattern.setWeaknesses(sections.getOrDefault("weaknesses", ""));
        pattern.setRecognitionSignals(getStringList(frontmatter, "recognition_signals"));
        pattern.setCommonFailureModes(sections.getOrDefault("common_failure_modes", ""));
        pattern.setGeneralizations(sections.getOrDefault("generalizations", ""));
        pattern.setStatus(ContentStatus.valueOf((String) frontmatter.getOrDefault("status", "DRAFT")));
        
        // Set complexity if present
        if (frontmatter.containsKey("time_complexity")) {
            Complexity complexity = new Complexity();
            complexity.setTimeComplexity((String) frontmatter.get("time_complexity"));
            complexity.setSpaceComplexity((String) frontmatter.get("space_complexity"));
            complexity.setTimeReasoning((String) frontmatter.get("time_reasoning"));
            complexity.setSpaceReasoning((String) frontmatter.get("space_reasoning"));
            pattern.setComplexity(complexity);
        }
        
        patternRepository.save(pattern);
        result.addImported("pattern", slug);
        
        // Import pattern references
        importReferences(patternDir.resolve("references.yaml"), "PATTERN", pattern.getId(), result);
        
        // Import pattern templates
        importPatternTemplates(patternDir.resolve("templates"), pattern, result);
        
        // Link relationships from frontmatter
        linkPatternRelationships(pattern, frontmatter, result);
    }
    
    private Map<String, String> parseMarkdownSections(String markdownContent) {
        Map<String, String> sections = new LinkedHashMap<>();
        if (markdownContent == null || markdownContent.isBlank()) {
            sections.put("definition", "");
            return sections;
        }
        
        // Split by ## headings
        String[] parts = markdownContent.split("(?m)^##\\s+");
        
        String definition = parts[0].trim();
        sections.put("definition", definition);
        
        // Section name mapping from markdown heading to field name
        Map<String, String> headingToField = Map.ofEntries(
            Map.entry("When to Use", "when_to_use"),
            Map.entry("Why It Works", "why_it_works"),
            Map.entry("Intuition", "intuition"),
            Map.entry("Invariant", "invariant"),
            Map.entry("Strengths", "strengths"),
            Map.entry("Weaknesses", "weaknesses"),
            Map.entry("Common Failure Modes", "common_failure_modes"),
            Map.entry("Generalizations", "generalizations"),
            Map.entry("Complexity", "complexity")
        );
        
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            int newlineIndex = part.indexOf('\n');
            if (newlineIndex > 0) {
                String heading = part.substring(0, newlineIndex).trim();
                String body = part.substring(newlineIndex + 1).trim();
                
                String fieldName = headingToField.get(heading);
                if (fieldName != null) {
                    sections.put(fieldName, body);
                }
            }
        }
        
        return sections;
    }
    
    private void linkPatternRelationships(Pattern pattern, Map<String, Object> frontmatter, ImportResult result) {
        // Link templates
        List<String> templateSlugs = getStringList(frontmatter, "templates");
        for (String templateSlug : templateSlugs) {
            templateRepository.findBySlug(templateSlug).ifPresent(template -> {
                pattern.getTemplates().add(template);
            });
        }
        
        // Link algorithms
        List<String> algoSlugs = getStringList(frontmatter, "algorithms");
        for (String algoSlug : algoSlugs) {
            algorithmRepository.findBySlug(algoSlug).ifPresent(algo -> {
                pattern.getAlgorithms().add(algo);
            });
        }
        
        // Link data structures
        List<String> dsSlugs = getStringList(frontmatter, "dataStructures");
        for (String dsSlug : dsSlugs) {
            dataStructureRepository.findBySlug(dsSlug).ifPresent(ds -> {
                pattern.getDataStructures().add(ds);
            });
        }
        
        // Link problems (these are problem slugs from frontmatter)
        List<String> problemSlugs = getStringList(frontmatter, "problems");
        for (String problemSlug : problemSlugs) {
            problemRepository.findBySlug(problemSlug).ifPresent(problem -> {
                pattern.getProblems().add(problem);
            });
        }
        
        // Link sub-patterns - using native query to set required 'type' column
        List<String> subPatternSlugs = getStringList(frontmatter, "relatedPatterns");
        for (String subPatternSlug : subPatternSlugs) {
            patternRepository.findBySlug(subPatternSlug).ifPresent(subPattern -> {
                try {
                    entityManager.createNativeQuery("""
                        INSERT INTO pattern_relationships (pattern_id, related_pattern_id, type)
                        VALUES (:patternId, :relatedPatternId, 'SUB_PATTERN')
                        ON CONFLICT DO NOTHING
                        """)
                        .setParameter("patternId", pattern.getId())
                        .setParameter("relatedPatternId", subPattern.getId())
                        .executeUpdate();
                } catch (Exception e) {
                    log.warn("Failed to link sub-pattern {} -> {}: {}", pattern.getSlug(), subPatternSlug, e.getMessage());
                }
            });
        }
        
patternRepository.save(pattern);
    }
    
    private void importPatternTemplates(Path templatesDir, Pattern pattern, ImportResult result) throws IOException {
        if (!Files.exists(templatesDir)) return;
        
        try (var stream = Files.list(templatesDir)) {
            stream.filter(path -> path.toString().endsWith(".md"))
                .forEach(templateFile -> {
                    try {
                        importTemplate(templateFile, result, pattern, null);
                    } catch (Exception e) {
                        result.addError("template", templateFile.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importAlgorithms(Path algorithmsDir, ImportResult result) throws IOException {
        if (!Files.exists(algorithmsDir)) {
            log.warn("Algorithms directory not found: {}", algorithmsDir);
            return;
        }
        
        try (var stream = Files.list(algorithmsDir)) {
            stream.filter(Files::isDirectory)
                .forEach(algoDir -> {
                    try {
                        importAlgorithm(algoDir, result);
                    } catch (Exception e) {
                        result.addError("algorithm", algoDir.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importAlgorithm(Path algoDir, ImportResult result) throws IOException {
        Path algoFile = algoDir.resolve("algorithm.md");
        if (!Files.exists(algoFile)) {
            result.addError("algorithm", algoDir.getFileName().toString(), "algorithm.md not found");
            return;
        }
        
        String content = Files.readString(algoFile);
        Map<String, Object> frontmatter = parseFrontmatter(content);
        String markdownContent = extractMarkdownContent(content);
        
        String slug = (String) frontmatter.get("id");
        if (algorithmRepository.existsBySlug(slug)) {
            log.info("Algorithm already exists: {}", slug);
            return;
        }
        
        Algorithm algorithm = Algorithm.builder()
            .slug(slug)
            .name((String) frontmatter.get("name"))
            .description(markdownContent)
            .stepByStepReasoning(getStringOrEmpty(frontmatter, "step_by_step_reasoning"))
            .complexityAnalysis(getStringOrEmpty(frontmatter, "complexity_analysis"))
            .status(ContentStatus.valueOf((String) frontmatter.getOrDefault("status", "DRAFT")))
            .build();
        
        if (frontmatter.containsKey("time_complexity")) {
            Complexity complexity = new Complexity();
            complexity.setTimeComplexity((String) frontmatter.get("time_complexity"));
            complexity.setSpaceComplexity((String) frontmatter.get("space_complexity"));
            complexity.setTimeReasoning((String) frontmatter.get("time_reasoning"));
            complexity.setSpaceReasoning((String) frontmatter.get("space_reasoning"));
            algorithm.setComplexity(complexity);
        }
        
        algorithmRepository.save(algorithm);
        result.addImported("algorithm", slug);
        
        importReferences(algoDir.resolve("references.yaml"), "ALGORITHM", algorithm.getId(), result);
        importAlgorithmTemplates(algoDir.resolve("templates"), algorithm, result);
    }

    private void importAlgorithmTemplates(Path templatesDir, Algorithm algorithm, ImportResult result) throws IOException {
        if (!Files.exists(templatesDir)) return;
        
        try (var stream = Files.list(templatesDir)) {
            stream.filter(path -> path.toString().endsWith(".md"))
                .forEach(templateFile -> {
                    try {
                        importTemplate(templateFile, result, null, algorithm);
                    } catch (Exception e) {
                        result.addError("template", templateFile.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importDataStructures(Path dsDir, ImportResult result) throws IOException {
        if (!Files.exists(dsDir)) {
            log.warn("Data structures directory not found: {}", dsDir);
            return;
        }
        
        try (var stream = Files.list(dsDir)) {
            stream.filter(Files::isDirectory)
                .forEach(dsDirItem -> {
                    try {
                        importDataStructure(dsDirItem, result);
                    } catch (Exception e) {
                        result.addError("data-structure", dsDirItem.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importDataStructure(Path dsDir, ImportResult result) throws IOException {
        Path dsFile = dsDir.resolve("data-structure.md");
        if (!Files.exists(dsFile)) {
            result.addError("data-structure", dsDir.getFileName().toString(), "data-structure.md not found");
            return;
        }
        
        String content = Files.readString(dsFile);
        Map<String, Object> frontmatter = parseFrontmatter(content);
        String markdownContent = extractMarkdownContent(content);
        
        String slug = (String) frontmatter.get("id");
        if (dataStructureRepository.existsBySlug(slug)) {
            log.info("Data structure already exists: {}", slug);
            return;
        }
        
        DataStructure ds = DataStructure.builder()
            .slug(slug)
            .name((String) frontmatter.get("name"))
            .description(markdownContent)
            .whyUsefulHere(getStringOrEmpty(frontmatter, "why_useful_here"))
            .tradeoffs(getStringOrEmpty(frontmatter, "tradeoffs"))
            .alternatives(getStringOrEmpty(frontmatter, "alternatives"))
            .status(ContentStatus.valueOf((String) frontmatter.getOrDefault("status", "DRAFT")))
            .build();
        
        if (frontmatter.containsKey("operations")) {
            Object opsObj = frontmatter.get("operations");
            Map<String, String> ops;
            if (opsObj instanceof String) {
                ops = yaml.load((String) opsObj);
            } else if (opsObj instanceof Map) {
                ops = (Map<String, String>) opsObj;
            } else {
                ops = new LinkedHashMap<>();
            }
            ds.setOperations(ops);
        }
        
        dataStructureRepository.save(ds);
        result.addImported("data-structure", slug);
        
        importReferences(dsDir.resolve("references.yaml"), "DATA_STRUCTURE", ds.getId(), result);
    }

    private void importConcepts(Path conceptsDir, ImportResult result) throws IOException {
        if (!Files.exists(conceptsDir)) {
            log.warn("Concepts directory not found: {}", conceptsDir);
            return;
        }
        
        try (var stream = Files.list(conceptsDir)) {
            stream.filter(Files::isDirectory)
                .forEach(conceptDir -> {
                    try {
                        importConcept(conceptDir, result);
                    } catch (Exception e) {
                        result.addError("concept", conceptDir.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importConcept(Path conceptDir, ImportResult result) throws IOException {
        Path conceptFile = conceptDir.resolve("concept.md");
        if (!Files.exists(conceptFile)) {
            result.addError("concept", conceptDir.getFileName().toString(), "concept.md not found");
            return;
        }
        
        String content = Files.readString(conceptFile);
        Map<String, Object> frontmatter = parseFrontmatter(content);
        String markdownContent = extractMarkdownContent(content);
        
        String slug = (String) frontmatter.get("id");
        if (conceptRepository.existsBySlug(slug)) {
            log.info("Concept already exists: {}", slug);
            return;
        }
        
        Concept concept = Concept.builder()
            .slug(slug)
            .name((String) frontmatter.get("name"))
            .description(markdownContent)
            .status(ContentStatus.valueOf((String) frontmatter.getOrDefault("status", "DRAFT")))
            .build();
        
        conceptRepository.save(concept);
        result.addImported("concept", slug);
        
        importReferences(conceptDir.resolve("references.yaml"), "CONCEPT", concept.getId(), result);
    }

    private void importTemplates(Path templatesDir, ImportResult result) throws IOException {
        if (!Files.exists(templatesDir)) {
            log.warn("Templates directory not found: {}", templatesDir);
            return;
        }
        
        try (var stream = Files.list(templatesDir)) {
            stream.filter(path -> path.toString().endsWith(".md"))
                .forEach(templateFile -> {
                    try {
                        importTemplate(templateFile, result, null, null);
                    } catch (Exception e) {
                        result.addError("template", templateFile.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private Template importTemplate(Path templateFile, ImportResult result, Pattern pattern, Algorithm algorithm) throws IOException {
        String content = Files.readString(templateFile);
        Map<String, Object> frontmatter = parseFrontmatter(content);
        String markdownContent = extractMarkdownContent(content);
        
        String codeBlock = extractCodeBlock(markdownContent);
        Map<Integer, String> markerExplanations = parseMarkerExplanations(markdownContent);
        
        String slug = (String) frontmatter.get("id");
        if (templateRepository.existsBySlug(slug)) {
            log.info("Template already exists: {}", slug);
            return null;
        }
        
        Template template = Template.builder()
            .slug(slug)
            .name((String) frontmatter.get("name"))
            .language(Language.valueOf(getStringOrDefault(frontmatter, "language", "TYPESCRIPT")))
            .purpose((String) frontmatter.get("purpose"))
            .whenToReachForIt((String) frontmatter.get("when_to_reach_for_it"))
            .prerequisites(getStringOrEmpty(frontmatter, "prerequisites"))
            .mentalModel((String) frontmatter.get("mental_model"))
            .code(codeBlock.isEmpty() ? markdownContent : codeBlock)
            .explanation(getStringOrDefault(frontmatter, "explanation", markdownContent))
            .invariant(getStringOrEmpty(frontmatter, "invariant"))
            .commonModifications(getStringOrEmpty(frontmatter, "common_modifications"))
            .commonMistakes(getStringOrEmpty(frontmatter, "common_mistakes"))
            .status(ContentStatus.valueOf(getStringOrDefault(frontmatter, "status", "DRAFT")))
            .build();
        template.setMarkerExplanations(markerExplanations);
        
        if (frontmatter.containsKey("time_complexity")) {
            Complexity complexity = new Complexity();
            complexity.setTimeComplexity((String) frontmatter.get("time_complexity"));
            complexity.setSpaceComplexity((String) frontmatter.get("space_complexity"));
            complexity.setTimeReasoning((String) frontmatter.get("time_reasoning"));
            complexity.setSpaceReasoning((String) frontmatter.get("space_reasoning"));
            template.setComplexity(complexity);
        }
        
        if (pattern != null) {
            template.setPattern(pattern);
        }
        
        // Link to pattern referenced in frontmatter, if not already set
        String patternSlug = (String) frontmatter.get("pattern");
        if (template.getPattern() == null && patternSlug != null && !patternSlug.isBlank()) {
            patternRepository.findBySlug(patternSlug).ifPresent(template::setPattern);
        }
        
        templateRepository.save(template);
        result.addImported("template", slug);
        
        if (algorithm != null) {
            algorithm.getTemplates().add(template);
        }
        
        return template;
    }

    /**
     * Extract marker explanations ([1], [2], ...) from the template body.
     * Maps each numbered marker to its explanation line.
     */
    private Map<Integer, String> parseMarkerExplanations(String markdownContent) {
        Map<Integer, String> markerExplanations = new LinkedHashMap<>();
        if (markdownContent == null || markdownContent.isBlank()) {
            return markerExplanations;
        }
        Matcher m = java.util.regex.Pattern
            .compile("^\\[(\\d+)\\]\\s+(.+)$", java.util.regex.Pattern.MULTILINE)
            .matcher(markdownContent);
        while (m.find()) {
            markerExplanations.put(Integer.parseInt(m.group(1)), m.group(2).trim());
        }
        return markerExplanations;
    }

    /**
     * Extract the first fenced code block from a markdown template body.
     */
    private String extractCodeBlock(String markdownContent) {
        if (markdownContent == null) return "";
        Matcher m = java.util.regex.Pattern
            .compile("```[a-zA-Z0-9]*\\s*\\r?\\n(.*?)```", java.util.regex.Pattern.DOTALL)
            .matcher(markdownContent);
        return m.find() ? m.group(1).strip() : "";
    }

    private void importProblems(Path problemsDir, ImportResult result) throws IOException {
        if (!Files.exists(problemsDir)) {
            log.warn("Problems directory not found: {}", problemsDir);
            return;
        }
        
        try (var stream = Files.list(problemsDir)) {
            stream.filter(Files::isDirectory)
                .forEach(problemDir -> {
                    try {
                        importProblem(problemDir, result);
                    } catch (Exception e) {
                        result.addError("problem", problemDir.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importProblem(Path problemDir, ImportResult result) throws IOException {
        Path problemFile = problemDir.resolve("problem.md");
        if (!Files.exists(problemFile)) {
            result.addError("problem", problemDir.getFileName().toString(), "problem.md not found");
            return;
        }
        
        String content = Files.readString(problemFile);
        Map<String, Object> frontmatter = parseFrontmatter(content);
        String markdownContent = extractMarkdownContent(content);
        
        String slug = (String) frontmatter.get("id");
        Problem problem = problemRepository.findBySlug(slug).orElse(null);
        boolean isNew = problem == null;
        
        if (isNew) {
            problem = Problem.builder()
                .slug(slug)
                .title((String) frontmatter.get("title"))
                .description(markdownContent)
                .externalUrl((String) frontmatter.get("sourceUrl"))
                .difficulty(Difficulty.valueOf(getStringOrDefault(frontmatter, "difficulty", "EASY")))
                .status(ContentStatus.valueOf(getStringOrDefault(frontmatter, "status", "DRAFT")))
                .orderIndex(getInteger(frontmatter, "order_index", 0))
                .reasonForSelection((String) frontmatter.get("reason_for_selection"))
                .build();
            
            problemRepository.save(problem);
            result.addImported("problem", slug);
        } else {
            // Update existing problem fields
            problem.setTitle((String) frontmatter.get("title"));
            problem.setDescription(markdownContent);
            problem.setExternalUrl((String) frontmatter.get("sourceUrl"));
            problem.setDifficulty(Difficulty.valueOf(getStringOrDefault(frontmatter, "difficulty", "EASY")));
            problem.setStatus(ContentStatus.valueOf(getStringOrDefault(frontmatter, "status", "DRAFT")));
            problem.setOrderIndex(getInteger(frontmatter, "order_index", 0));
            problem.setReasonForSelection((String) frontmatter.get("reason_for_selection"));
            problemRepository.save(problem);
            log.info("Updated existing problem: {}", slug);
        }
        
        // Import problem-local templates and link them to the problem
        importProblemTemplates(problemDir.resolve("templates"), problem, result);
        
        // Import learning objectives declared in frontmatter
        importLearningObjectives(frontmatter, problem);
        
        // Link relationships (for both new and existing problems)
        linkProblemRelationships(problem, frontmatter, result);
        
        // Import solutions
        importSolutions(problemDir.resolve("solutions"), problem, result);
        
        // Import test cases
        importTestCases(problemDir.resolve("test-cases.yaml"), problem, null, result);
        
        // Import problem references
        importReferences(problemDir.resolve("references.yaml"), "PROBLEM", problem.getId(), result);
        
        // Import problem variations
        importVariations(problemDir.resolve("variations"), problem, result);
    }

    private void importProblemTemplates(Path templatesDir, Problem problem, ImportResult result) throws IOException {
        if (!Files.exists(templatesDir)) return;
        
        try (var stream = Files.list(templatesDir)) {
            stream.filter(path -> path.toString().endsWith(".md"))
                .forEach(templateFile -> {
                    try {
                        Template template = importTemplate(templateFile, result, null, null);
                        if (template != null) {
                            problem.addTemplate(template);
                        }
                    } catch (Exception e) {
                        result.addError("template", templateFile.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importLearningObjectives(Map<String, Object> frontmatter, Problem problem) {
        Object value = frontmatter.get("learningObjectives");
        if (!(value instanceof List<?> objectives)) return;
        for (Object obj : objectives) {
            if (!(obj instanceof Map<?, ?> map)) continue;
            Map<String, Object> lo = new HashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                lo.put(String.valueOf(entry.getKey()), entry.getValue());
            }
            LearningObjective learningObjective = LearningObjective.builder()
                .problem(problem)
                .description(getStringOrEmpty(lo, "description"))
                .bloomLevel(BloomLevel.valueOf(getStringOrDefault(lo, "bloom_level", "UNDERSTAND")))
                .build();
            problem.getLearningObjectives().add(learningObjective);
            learningObjectiveRepository.save(learningObjective);
        }
    }

    private void linkProblemRelationships(Problem problem, Map<String, Object> frontmatter, ImportResult result) {
        // Patterns
        List<String> patternSlugs = getStringList(frontmatter, "patterns");
        for (String patternSlug : patternSlugs) {
            patternRepository.findBySlug(patternSlug).ifPresent(pattern -> {
                problem.addPattern(pattern);
            });
        }
        
        // Algorithms
        List<String> algoSlugs = getStringList(frontmatter, "algorithms");
        for (String algoSlug : algoSlugs) {
            algorithmRepository.findBySlug(algoSlug).ifPresent(algo -> {
                problem.addAlgorithm(algo);
            });
        }
        
        // Data Structures
        List<String> dsSlugs = getStringList(frontmatter, "dataStructures");
        for (String dsSlug : dsSlugs) {
            dataStructureRepository.findBySlug(dsSlug).ifPresent(ds -> {
                problem.addDataStructure(ds);
            });
        }
        
        // Templates
        List<String> templateSlugs = getStringList(frontmatter, "templates");
        for (String templateSlug : templateSlugs) {
            templateRepository.findBySlug(templateSlug).ifPresent(template -> {
                problem.addTemplate(template);
            });
        }
        
        // Prerequisites (concepts)
        List<String> prereqSlugs = getStringList(frontmatter, "prerequisites");
        for (String prereqSlug : prereqSlugs) {
            conceptRepository.findBySlug(prereqSlug).ifPresent(concept -> {
                problem.addPrerequisite(concept);
            });
        }
        
        // Related Problems
        List<String> relatedSlugs = getStringList(frontmatter, "relatedProblems");
        for (String relatedSlug : relatedSlugs) {
            problemRepository.findBySlug(relatedSlug).ifPresent(related -> {
                problem.addRelatedProblem(related);
            });
        }
        
        problemRepository.save(problem);
    }

    private void importSolutions(Path solutionsDir, Problem problem, ImportResult result) throws IOException {
        if (!Files.exists(solutionsDir)) return;
        
        int approachOrder = 0;
        try (var stream = Files.list(solutionsDir)) {
            List<Path> solutionFiles = stream
                .filter(path -> path.toString().endsWith(".ts") || path.toString().endsWith(".js") || path.toString().endsWith(".py"))
                .sorted()
                .collect(Collectors.toList());
            
            for (Path solutionFile : solutionFiles) {
                String code = Files.readString(solutionFile);
                String fileName = solutionFile.getFileName().toString();
                String approachName = fileName.substring(0, fileName.lastIndexOf('.'));
                
                // Determine approach from filename or use order
                Approach approach = detectApproach(fileName, approachOrder);
                
                // Check if solution already exists for this problem and approach
                if (solutionRepository.existsByProblemIdAndApproach(problem.getId(), approach)) {
                    log.info("Solution already exists for problem {} with approach {}, skipping", problem.getSlug(), approach);
                    result.addImported("solution", problem.getSlug() + "-" + approach);
                    continue;
                }
                
                Solution solution = Solution.builder()
                    .problem(problem)
                    .name(approachName)
                    .approach(approach)
                    .explanation("") // Would need separate markdown files for detailed explanations
                    .whyItWorks("")
                    .language(detectLanguage(solutionFile))
                    .code(code)
                    .complexity(new Complexity(
                        "O(n)",     // time_complexity - default
                        "O(1)",     // space_complexity - default
                        "",         // time_reasoning
                        "",         // space_reasoning
                        "",         // best_case_time
                        "",         // worst_case_time
                        ""          // average_case_time
                    ))
                    .build();
                
                // Explicitly set UUID before save to avoid DB generator conflicts
                solution.setId(UUID.randomUUID());
                
                try {
                    solutionRepository.saveAndFlush(solution);
                    createTemplateMappings(problem, solution);
                    approachOrder++;
                } catch (Exception e) {
                    log.warn("Failed to save solution for {} ({}): {}", problem.getSlug(), approach, e.getMessage());
                    result.addError("solution", approachName, e.getMessage());
                }
            }
        }
    }

    private void createTemplateMappings(Problem problem, Solution solution) {
        for (Template template : problem.getTemplates()) {
            for (Map.Entry<Integer, String> entry : template.getMarkerExplanations().entrySet()) {
                TemplateMapping mapping = TemplateMapping.builder()
                    .template(template)
                    .solution(solution)
                    .templateMarker(entry.getKey())
                    .solutionCodeSnippet(solution.getCode())
                    .explanation(entry.getValue())
                    .build();
                template.getMappings().add(mapping);
                solution.getMappings().add(mapping);
                templateMappingRepository.save(mapping);
            }
        }
    }

    private void importTestCases(Path testCasesFile, Problem problem, ProblemVariation variation, ImportResult result) throws IOException {
        if (!Files.exists(testCasesFile)) return;
        
        List<Map<String, Object>> testCases = yaml.load(Files.readString(testCasesFile));
        if (testCases == null) return;
        
        for (Map<String, Object> tc : testCases) {
            Object inputObj = tc.get("input");
            Object expectedObj = tc.get("expected");
            
            String inputJson = inputObj != null ? toJson(inputObj) : "{}";
            String expectedJson = expectedObj != null ? toJson(expectedObj) : "{}";
            
            TestCase testCase = TestCase.builder()
                .problem(problem)
                .variation(variation)
                .name((String) tc.get("name"))
                .input(inputJson)
                .expectedOutput(expectedJson)
                .description((String) tc.getOrDefault("description", ""))
                .type(TestCaseType.valueOf((String) tc.getOrDefault("type", "BASIC")))
                .isHidden(getBoolean(tc, "hidden", false))
                .build();
            
            testCaseRepository.save(testCase);
        }
    }
    
    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Failed to serialize to JSON: {}", e.getMessage());
            return "{}";
        }
    }

    private void importVariations(Path variationsDir, Problem problem, ImportResult result) throws IOException {
        if (!Files.exists(variationsDir)) return;
        
        try (var stream = Files.list(variationsDir)) {
            stream.filter(Files::isDirectory)
                .forEach(varDir -> {
                    try {
                        importVariation(varDir, problem, result);
                    } catch (Exception e) {
                        result.addError("variation", varDir.getFileName().toString(), e.getMessage());
                    }
                });
        }
    }

    private void importVariation(Path varDir, Problem problem, ImportResult result) throws IOException {
        Path varFile = varDir.resolve("variation.md");
        if (!Files.exists(varFile)) return;
        
        String content = Files.readString(varFile);
        Map<String, Object> frontmatter = parseFrontmatter(content);
        String markdownContent = extractMarkdownContent(content);
        
        String name = (String) frontmatter.get("name");
        
        ProblemVariation variation = ProblemVariation.builder()
            .baseProblem(problem)
            .name(name)
            .description(markdownContent)
            .whatChanged(getStringOrEmpty(frontmatter, "what_changed"))
            .originalApproachWorks(getBoolean(frontmatter, "original_approach_works", false))
            .whatBreaks(getStringOrEmpty(frontmatter, "what_breaks"))
            .newConstraintMatters(getStringOrEmpty(frontmatter, "new_constraint_matters"))
            .dataStructureChanges(getBoolean(frontmatter, "data_structure_changes", false))
            .algorithmChanges(getBoolean(frontmatter, "algorithm_changes", false))
            .complexityChanges(getStringOrEmpty(frontmatter, "complexity_changes"))
            .build();
        
        problem.getVariations().add(variation);
        problemRepository.save(problem);
        
        // Import variation test cases
        importTestCases(varDir.resolve("test-cases.yaml"), problem, variation, result);
    }

    private void importReferences(Path refFile, String entityType, UUID entityId, ImportResult result) throws IOException {
        if (!Files.exists(refFile)) return;
        
        List<Map<String, Object>> refs = yaml.load(Files.readString(refFile));
        if (refs == null) return;
        
        for (Map<String, Object> ref : refs) {
            Reference reference = Reference.builder()
                .title((String) ref.get("title"))
                .url((String) ref.get("url"))
                .type(ReferenceType.valueOf((String) ref.getOrDefault("type", "TECH_RESOURCE")))
                .relevantConcept((String) ref.get("relevant_concept"))
                .verifies((String) ref.get("verifies"))
                .build();
            
            referenceRepository.save(reference);
            
            // Link to entity
            switch (entityType) {
                case "PATTERN" -> patternRepository.findById(entityId).ifPresent(p -> p.getReferences().add(reference));
                case "ALGORITHM" -> algorithmRepository.findById(entityId).ifPresent(a -> a.getReferences().add(reference));
                case "DATA_STRUCTURE" -> dataStructureRepository.findById(entityId).ifPresent(ds -> ds.getReferences().add(reference));
                case "PROBLEM" -> problemRepository.findById(entityId).ifPresent(p -> p.getReferences().add(reference));
                case "CONCEPT" -> conceptRepository.findById(entityId).ifPresent(c -> c.getReferences().add(reference));
            }
        }
    }

    // Helper methods
    private Map<String, Object> parseFrontmatter(String content) {
        Matcher matcher = frontmatterPattern.matcher(content);
        if (matcher.find()) {
            String yamlContent = matcher.group(1);
            return yaml.load(yamlContent);
        }
        return new HashMap<>();
    }

    private String extractMarkdownContent(String content) {
        Matcher matcher = frontmatterPattern.matcher(content);
        if (matcher.find()) {
            return content.substring(matcher.end()).trim();
        }
        return content;
    }

    private String getStringOrEmpty(Map<String, Object> map, String key) {
        return (String) map.getOrDefault(key, "");
    }

    private String getStringOrDefault(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    private boolean getBoolean(Map<String, Object> map, String key, boolean defaultValue) {
        Object value = map.get(key);
        if (value == null) return defaultValue;
        if (value instanceof Boolean b) return b;
        return Boolean.parseBoolean(value.toString());
    }

    private int getInteger(Map<String, Object> map, String key, int defaultValue) {
        Object value = map.get(key);
        if (value == null) return defaultValue;
        if (value instanceof Number n) return n.intValue();
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private List<String> getStringList(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            return new ArrayList<>();
        }
        if (value instanceof List) {
            return ((List<?>) value).stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        }
        if (value instanceof String) {
            // Handle single string value (YAML might parse single-item list as string)
            String str = (String) value;
            // Try to parse as comma-separated or newline-separated
            if (str.contains(",") || str.contains("\n")) {
                return Arrays.stream(str.split("[,\n]"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            }
            return List.of(str);
        }
        return new ArrayList<>();
    }

    private Approach detectApproach(String fileName, int order) {
        String upper = fileName.toUpperCase();
        if (upper.contains("WORST") || upper.contains("BRUTE") || upper.contains("NAIVE") || upper.contains("FORCE")) return Approach.WORST;
        if (upper.contains("BETTER")) return Approach.BETTER;
        if (upper.contains("BEST") || upper.contains("OPTIMAL")) return Approach.BEST;
        return Approach.values()[Math.min(order, Approach.values().length - 1)];
    }

    private Language detectLanguage(Path file) {
        String fileName = file.getFileName().toString();
        if (fileName.endsWith(".ts")) return Language.TYPESCRIPT;
        if (fileName.endsWith(".js")) return Language.JAVASCRIPT;
        if (fileName.endsWith(".py")) return Language.PYTHON;
        return Language.TYPESCRIPT;
    }

    // Result class
    public static class ImportResult {
        private int imported = 0;
        private final List<String> errors = new ArrayList<>();
        private final Map<String, Integer> counts = new HashMap<>();

        public void addImported(String type, String name) {
            imported++;
            counts.merge(type, 1, Integer::sum);
        }

        public void addError(String type, String name, String error) {
            errors.add(String.format("[%s] %s: %s", type, name, error));
        }

        public int getImported() { return imported; }
        public List<String> getErrors() { return errors; }
        public Map<String, Integer> getCounts() { return counts; }
        
        @Override
        public String toString() {
            return String.format("ImportResult{imported=%d, errors=%d, counts=%s}", imported, errors.size(), counts);
        }
    }
}