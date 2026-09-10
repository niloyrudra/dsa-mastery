package com.dsamastery.ai.service;

import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Assembles a compact, token-budgeted description of a problem and its
 * associated pedagogical content (patterns, templates, solutions, test cases)
 * to use as grounding context for AI tutor prompts.
 */
@Component
@RequiredArgsConstructor
public class ProblemContextBuilder {

    private final ProblemService problemService;

    private static final int DEFAULT_MAX_CHARS = 4000;

    public Optional<String> buildContext(java.util.UUID problemId) {
        return buildContext(problemId, DEFAULT_MAX_CHARS);
    }

    public Optional<String> buildContext(java.util.UUID problemId, int maxChars) {
        return problemService.getProblemById(problemId)
            .map(problem -> build(problem, maxChars));
    }

    public String build(Problem problem) {
        return build(problem, DEFAULT_MAX_CHARS);
    }

    private String build(Problem problem, int maxChars) {
        StringBuilder sb = new StringBuilder(2048);

        sb.append("PROBLEM: ").append(problem.getTitle())
            .append(" (").append(problem.getDifficulty()).append(")\n")
            .append(problem.getDescription()).append('\n');

        if (problem.getPatterns() != null && !problem.getPatterns().isEmpty()) {
            sb.append("\nPATTERNS:\n");
            problem.getPatterns().forEach(p -> sb.append(" - ").append(p.getName())
                .append(": ").append(nz(p.getDefinition()))
                .append(p.getInvariant() != null && !p.getInvariant().isBlank()
                    ? " Invariant: " + p.getInvariant() : "")
                .append('\n'));
        }

        if (problem.getTemplates() != null && !problem.getTemplates().isEmpty()) {
            sb.append("\nTEMPLATES:\n");
            problem.getTemplates().forEach(t -> sb.append(" - ").append(t.getName())
                .append(": ").append(nz(t.getMentalModel())).append('\n'));
        }

        if (problem.getSolutions() != null && !problem.getSolutions().isEmpty()) {
            sb.append("\nSOLUTIONS:\n");
            problem.getSolutions().stream()
                .sorted((a, b) -> a.getApproach().compareTo(b.getApproach()))
                .forEach(s -> sb.append(" - ").append(s.getName())
                    .append(" (").append(s.getApproach()).append("): ")
                    .append(nz(s.getExplanation()))
                    .append(" [").append(nz(s.getComplexity() != null ? s.getComplexity().getTimeComplexity() : null))
                    .append(" time, ")
                    .append(nz(s.getComplexity() != null ? s.getComplexity().getSpaceComplexity() : null))
                    .append(" space]\n"));
        }

        if (problem.getDataStructures() != null && !problem.getDataStructures().isEmpty()) {
            sb.append("\nDATA STRUCTURES:\n");
            problem.getDataStructures().forEach(ds -> sb.append(" - ").append(ds.getName()).append('\n'));
        }

        String text = sb.toString();
        return text.length() <= maxChars ? text : text.substring(0, maxChars);
    }

    private static String nz(String value) {
        return value == null ? "" : value.trim();
    }
}