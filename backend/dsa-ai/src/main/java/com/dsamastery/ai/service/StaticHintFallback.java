package com.dsamastery.ai.service;

import com.dsamastery.common.enums.MessageType;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.Solution;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Static, offline fallback for the AI tutor. Composes genuinely useful
 * pedagogical responses directly from the problem's own content
 * (patterns, templates, solutions, variations) so the tutor keeps working
 * when Ollama is unavailable.
 */
@Component
public class StaticHintFallback {

    public String generate(Problem problem, String userMessage, MessageType type, int attemptCount) {
        if (problem == null) {
            return "I can't find the problem you're working on. Could you share the problem statement or its slug?";
        }
        return switch (type) {
            case HINT -> socraticHint(problem, attemptCount);
            case CODE_REVIEW -> codeReview(problem);
            case COMPLEXITY_ANALYSIS -> complexityAnalysis(problem);
            case VARIATION -> variations(problem);
            default -> conceptExplanation(problem, userMessage);
        };
    }

    private String socraticHint(Problem problem, int attemptCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Let's break this down step by step. I'll guide you with questions rather than give the answer away.\n\n");
        if (!problem.getPatterns().isEmpty()) {
            Pattern pattern = problem.getPatterns().stream().findFirst().orElse(null);
            sb.append("**Recognition.** ").append(problem.getTitle())
                .append(" fits the **").append(pattern.getName()).append("** pattern. ")
                .append("Why do you think that pattern applies here? Look for ")
                .append(signals(pattern)).append(".\n\n");
            if (pattern.getWhenToUse() != null && !pattern.getWhenToUse().isBlank()) {
                sb.append("**When to use it:** ").append(pattern.getWhenToUse()).append("\n\n");
            }
        }
        if (attemptCount == 0) {
            sb.append("**Question 1:** Forget code for a minute — if you had to solve this by hand, what would your first step be?");
        } else if (attemptCount <= 2) {
            if (!problem.getDataStructures().isEmpty()) {
                var ds = problem.getDataStructures().stream().findFirst().orElse(null);
                sb.append("**Question 2:** Which data structure could keep useful information about elements you've already seen? O(1) lookup needs often point to a **")
                    .append(ds.getName()).append("**.)");
            } else {
                sb.append("**Question 2:** What is the naive approach, and what exactly is wasteful about it?");
            }
        } else {
            Solution best = bestSolution(problem);
            if (best != null && best.getInsightToNext() != null && !best.getInsightToNext().isBlank()) {
                sb.append("**Next step:** ").append(best.getInsightToNext());
            } else {
                sb.append("**Next step:** Once you can state the brute-force algorithm, ask: which repeated work can I avoid?");
            }
        }
        return sb.toString();
    }

    private String codeReview(Problem problem) {
        StringBuilder sb = new StringBuilder();
        sb.append("## Code Review\n\n");
        
        Solution best = bestSolution(problem);
        if (best == null) {
            sb.append("No reference solution available for comparison.\n");
            return sb.toString();
        }
        
        sb.append("**Optimal Approach:** ").append(best.getName()).append(" (").append(best.getApproach()).append(")\n\n");
        sb.append("**Time Complexity:** ").append(best.getComplexity() != null ? best.getComplexity().getTimeComplexity() : "N/A").append("\n");
        sb.append("**Space Complexity:** ").append(best.getComplexity() != null ? best.getComplexity().getSpaceComplexity() : "N/A").append("\n\n");
        
        sb.append("### Key Points to Check\n");
        sb.append("1. **Edge cases handled?** Empty input, single element, duplicates, large values\n");
        sb.append("2. **Correct loop bounds?** Off-by-one errors are common\n");
        sb.append("3. **Proper initialization?** Variables set before use\n");
        sb.append("4. **Return type matches?** Indices vs values, empty result handling\n\n");
        
        if (best.getWhyNotIdeal() != null && !best.getWhyNotIdeal().isBlank()) {
            sb.append("### Common Issues in This Problem\n");
            sb.append(best.getWhyNotIdeal()).append("\n\n");
        }
        
        sb.append("### Suggested Improvements\n");
        if (best.getBottleneckRemaining() != null && !best.getBottleneckRemaining().isBlank()) {
            sb.append("- **Bottleneck:** ").append(best.getBottleneckRemaining()).append("\n");
        }
        if (best.getInsightToNext() != null && !best.getInsightToNext().isBlank()) {
            sb.append("- **Next optimization:** ").append(best.getInsightToNext()).append("\n");
        }
        
        return sb.toString();
    }

    private String complexityAnalysis(Problem problem) {
        StringBuilder sb = new StringBuilder();
        sb.append("## Complexity Analysis\n\n");
        
        Set<Solution> solutions = problem.getSolutions();
        if (solutions.isEmpty()) {
            sb.append("No solutions available for complexity analysis.\n");
            return sb.toString();
        }
        
        solutions.stream()
            .sorted(Comparator.comparing(Solution::getApproach))
            .forEach(s -> {
                sb.append("### ").append(s.getName()).append(" (").append(s.getApproach()).append(")\n");
                sb.append("**Time:** ").append(s.getComplexity() != null ? s.getComplexity().getTimeComplexity() : "N/A").append("\n");
                sb.append("**Space:** ").append(s.getComplexity() != null ? s.getComplexity().getSpaceComplexity() : "N/A").append("\n\n");
                
                if (s.getComplexity() != null) {
                    if (s.getComplexity().getTimeReasoning() != null && !s.getComplexity().getTimeReasoning().isBlank()) {
                        sb.append("*Time reasoning:* ").append(s.getComplexity().getTimeReasoning()).append("\n\n");
                    }
                    if (s.getComplexity().getSpaceReasoning() != null && !s.getComplexity().getSpaceReasoning().isBlank()) {
                        sb.append("*Space reasoning:* ").append(s.getComplexity().getSpaceReasoning()).append("\n\n");
                    }
                }
                
                if (s.getComplexity() != null && s.getComplexity().getBestCaseTime() != null && !s.getComplexity().getBestCaseTime().isBlank()) {
                    sb.append("**Best case:** ").append(s.getComplexity().getBestCaseTime()).append("\n");
                }
                if (s.getComplexity() != null && s.getComplexity().getWorstCaseTime() != null && !s.getComplexity().getWorstCaseTime().isBlank()) {
                    sb.append("**Worst case:** ").append(s.getComplexity().getWorstCaseTime()).append("\n");
                }
                if (s.getComplexity() != null && s.getComplexity().getAverageCaseTime() != null && !s.getComplexity().getAverageCaseTime().isBlank()) {
                    sb.append("**Average case:** ").append(s.getComplexity().getAverageCaseTime()).append("\n");
                }
                sb.append("\n");
            });
        
        return sb.toString();
    }

    private String variations(Problem problem) {
        StringBuilder sb = new StringBuilder();
        sb.append("## Problem Variations\n\n");
        
        if (problem.getVariations().isEmpty()) {
            sb.append("No variations defined for this problem.\n");
            return sb.toString();
        }
        
        int i = 1;
        for (var v : problem.getVariations()) {
            sb.append("### Variation ").append(i++).append(": ").append(v.getName()).append("\n\n");
            sb.append("**What changed:** ").append(v.getWhatChanged() != null ? v.getWhatChanged() : "Not specified").append("\n\n");
            sb.append("**Original approach works:** ").append(v.getOriginalApproachWorks() ? "Yes" : "No").append("\n");
            
            if (v.getWhatBreaks() != null && !v.getWhatBreaks().isBlank()) {
                sb.append("**What breaks:** ").append(v.getWhatBreaks()).append("\n");
            }
            if (v.getNewConstraintMatters() != null && !v.getNewConstraintMatters().isBlank()) {
                sb.append("**New constraint:** ").append(v.getNewConstraintMatters()).append("\n");
            }
            if (v.getComplexityChanges() != null && !v.getComplexityChanges().isBlank()) {
                sb.append("**Complexity changes:** ").append(v.getComplexityChanges()).append("\n");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }

    private String conceptExplanation(Problem problem, String userMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("## Concept Explanation\n\n");
        
        if (!problem.getPatterns().isEmpty()) {
            Pattern pattern = problem.getPatterns().stream().findFirst().orElse(null);
            sb.append("### ").append(pattern.getName()).append(" Pattern\n\n");
            if (pattern.getDefinition() != null && !pattern.getDefinition().isBlank()) {
                sb.append(pattern.getDefinition()).append("\n\n");
            }
            if (pattern.getWhyItWorks() != null && !pattern.getWhyItWorks().isBlank()) {
                sb.append("**Why it works:** ").append(pattern.getWhyItWorks()).append("\n\n");
            }
            if (pattern.getIntuition() != null && !pattern.getIntuition().isBlank()) {
                sb.append("**Intuition:** ").append(pattern.getIntuition()).append("\n\n");
            }
            if (pattern.getInvariant() != null && !pattern.getInvariant().isBlank()) {
                sb.append("**Invariant:** ").append(pattern.getInvariant()).append("\n\n");
            }
        }
        
        if (!problem.getDataStructures().isEmpty()) {
            sb.append("### Key Data Structures\n\n");
            for (var ds : problem.getDataStructures()) {
                sb.append("**").append(ds.getName()).append("**");
                if (ds.getWhyUsefulHere() != null && !ds.getWhyUsefulHere().isBlank()) {
                    sb.append(": ").append(ds.getWhyUsefulHere());
                }
                sb.append("\n\n");
            }
        }
        
        if (!problem.getPrerequisites().isEmpty()) {
            sb.append("### Prerequisites\n\n");
            for (var prereq : problem.getPrerequisites()) {
                sb.append("- **").append(prereq.getName()).append("**");
                if (prereq.getExplanation() != null && !prereq.getExplanation().isBlank()) {
                    sb.append(": ").append(prereq.getExplanation());
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        
        sb.append("*Need more detail? Ask a specific question about any concept above.*\n");
        return sb.toString();
    }

    private String signals(Pattern pattern) {
        if (pattern.getRecognitionSignals() == null || pattern.getRecognitionSignals().isEmpty()) {
            return "structural patterns in the input (sorted order, pairs, subarrays, etc.)";
        }
        return String.join(", ", pattern.getRecognitionSignals());
    }

    private Solution bestSolution(Problem problem) {
        return problem.getSolutions().stream()
            .filter(s -> s.getApproach() != null && s.getApproach().name().equals("BEST"))
            .findFirst()
            .orElseGet(() -> problem.getSolutions().stream()
                .max(Comparator.comparing(s -> s.getComplexity() != null ? s.getComplexity().getTimeComplexity() : ""))
                .orElse(null));
    }
}