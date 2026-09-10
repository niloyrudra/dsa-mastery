package com.dsamastery.curriculum.service;

import com.dsamastery.curriculum.entity.CurriculumNode;
import com.dsamastery.curriculum.repository.CurriculumNodeRepository;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.repository.ProblemRepository;
import com.dsamastery.content.repository.PatternRepository;
import com.dsamastery.content.repository.AlgorithmRepository;
import com.dsamastery.common.enums.NodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Imports curriculum structure from curriculum.yaml into the database.
 * Creates the hierarchical tree of CurriculumNode entities.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CurriculumImportService {

    private final CurriculumNodeRepository curriculumNodeRepository;
    private final ProblemRepository problemRepository;
    private final PatternRepository patternRepository;
    private final AlgorithmRepository algorithmRepository;

    private final Yaml yaml = new Yaml();

    @Transactional
    public ImportResult importCurriculum(Path curriculumFile) throws IOException {
        ImportResult result = new ImportResult();

        if (!Files.exists(curriculumFile)) {
            result.addError("curriculum", curriculumFile.toString(), "File not found");
            return result;
        }

        String content = Files.readString(curriculumFile);
        List<Map<String, Object>> curriculumData = yaml.load(content);

        if (curriculumData == null || curriculumData.isEmpty()) {
            result.addError("curriculum", curriculumFile.toString(), "Empty or invalid YAML");
            return result;
        }

        // Clear existing curriculum (optional - could be made configurable)
        curriculumNodeRepository.deleteAll();
        log.info("Cleared existing curriculum nodes");

        // Import root level nodes
        int orderIndex = 0;
        for (Map<String, Object> nodeData : curriculumData) {
            importNode(nodeData, null, 0, orderIndex++, result);
        }

        log.info("Curriculum import completed: {}", result);
        return result;
    }

    private void importNode(Map<String, Object> nodeData, CurriculumNode parent, int depth, int orderIndex, ImportResult result) {
        String name = (String) nodeData.get("name");
        String typeStr = (String) nodeData.get("type");
        String description = (String) nodeData.get("description");

        if (name == null || typeStr == null) {
            result.addError("node", name != null ? name : "unknown", "Missing name or type");
            return;
        }

        NodeType type;
        try {
            type = NodeType.valueOf(typeStr);
        } catch (IllegalArgumentException e) {
            result.addError("node", name, "Invalid type: " + typeStr);
            return;
        }

        UUID problemId = null;
        UUID patternId = null;
        UUID algorithmId = null;

        if (type == NodeType.PROBLEM) {
            String problemSlug = (String) nodeData.get("problemId");
            if (problemSlug != null) {
                Optional<Problem> problem = problemRepository.findBySlug(problemSlug);
                if (problem.isPresent()) {
                    problemId = problem.get().getId();
                } else {
                    result.addError("problem", problemSlug, "Problem not found in database");
                }
            }
        } else if (type == NodeType.PATTERN) {
            String patternSlug = (String) nodeData.get("patternId");
            if (patternSlug != null) {
                Optional<Pattern> pattern = patternRepository.findBySlug(patternSlug);
                if (pattern.isPresent()) {
                    patternId = pattern.get().getId();
                } else {
                    result.addError("pattern", patternSlug, "Pattern not found in database");
                }
            }
        } else if (type == NodeType.ALGORITHM) {
            String algorithmSlug = (String) nodeData.get("algorithmId");
            if (algorithmSlug != null) {
                Optional<Algorithm> algorithm = algorithmRepository.findBySlug(algorithmSlug);
                if (algorithm.isPresent()) {
                    algorithmId = algorithm.get().getId();
                } else {
                    result.addError("algorithm", algorithmSlug, "Algorithm not found in database");
                }
            }
        }

        // Check if node already exists (by name + parent + type)
        Optional<CurriculumNode> existing = findExistingNode(name, parent, type);
        CurriculumNode node;
        if (existing.isPresent()) {
            node = existing.get();
            node.setOrderIndex(orderIndex);
            node.setDepth(depth);
            node.setDescription(description);
            node.setProblemId(problemId);
            node.setPatternId(patternId);
            node.setAlgorithmId(algorithmId);
            curriculumNodeRepository.save(node);
        } else {
            node = CurriculumNode.builder()
                .name(name)
                .description(description)
                .type(type)
                .orderIndex(orderIndex)
                .depth(depth)
                .parent(parent)
                .problemId(problemId)
                .patternId(patternId)
                .algorithmId(algorithmId)
                .build();
            curriculumNodeRepository.save(node);
        }

        result.addImported("curriculum_node", name);

        // Import children
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> children = (List<Map<String, Object>>) nodeData.get("children");
        if (children != null && !children.isEmpty()) {
            int childOrder = 0;
            for (Map<String, Object> childData : children) {
                importNode(childData, node, depth + 1, childOrder++, result);
            }
        }
    }

    private Optional<CurriculumNode> findExistingNode(String name, CurriculumNode parent, NodeType type) {
        if (parent == null) {
            return curriculumNodeRepository.findRootNodes().stream()
                .filter(n -> n.getName().equals(name) && n.getType() == type)
                .findFirst();
        } else {
            return curriculumNodeRepository.findByParentId(parent.getId()).stream()
                .filter(n -> n.getName().equals(name) && n.getType() == type)
                .findFirst();
        }
    }

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