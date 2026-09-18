package com.dsamastery.api.mapper;

import com.dsamastery.api.dto.AlgorithmSummaryDto;
import com.dsamastery.api.dto.ComplexityDto;
import com.dsamastery.api.dto.ConceptSummaryDto;
import com.dsamastery.api.dto.DataStructureSummaryDto;
import com.dsamastery.api.dto.LearningObjectiveDto;
import com.dsamastery.api.dto.MasteryDto;
import com.dsamastery.api.dto.PatternSummaryDto;
import com.dsamastery.api.dto.ProblemChapterDto;
import com.dsamastery.api.dto.ProblemReferenceDto;
import com.dsamastery.api.dto.ProblemSummaryDto;
import com.dsamastery.api.dto.ProblemVariationDto;
import com.dsamastery.api.dto.ReferenceDto;
import com.dsamastery.api.dto.SolutionDto;
import com.dsamastery.api.dto.TemplateSummaryDto;
import com.dsamastery.api.dto.TestCaseDto;
import com.dsamastery.content.entity.Algorithm;
import com.dsamastery.content.entity.Concept;
import com.dsamastery.content.entity.DataStructure;
import com.dsamastery.content.entity.LearningObjective;
import com.dsamastery.content.entity.Pattern;
import com.dsamastery.content.entity.Problem;
import com.dsamastery.content.entity.ProblemVariation;
import com.dsamastery.content.entity.Reference;
import com.dsamastery.content.entity.Solution;
import com.dsamastery.content.entity.TestCase;
import com.dsamastery.content.entity.Template;
import com.dsamastery.mastery.entity.MasteryRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProblemMapper {
    
    ProblemMapper INSTANCE = Mappers.getMapper(ProblemMapper.class);
    
    @Mapping(target = "id", source = "problem.id")
    @Mapping(target = "patterns", source = "problem.patterns", qualifiedByName = "toPatternSummary")
    @Mapping(target = "algorithms", source = "problem.algorithms", qualifiedByName = "toAlgorithmSummary")
    @Mapping(target = "dataStructures", source = "problem.dataStructures", qualifiedByName = "toDataStructureSummary")
    @Mapping(target = "templates", source = "problem.templates", qualifiedByName = "toTemplateSummary")
    @Mapping(target = "variations", source = "problem.variations", qualifiedByName = "toVariationDto")
    @Mapping(target = "solutions", source = "problem.solutions", qualifiedByName = "toSolutionDto")
    @Mapping(target = "testCases", source = "problem.testCases", qualifiedByName = "toTestCaseDto")
    @Mapping(target = "learningObjectives", source = "problem.learningObjectives", qualifiedByName = "toLearningObjectiveDto")
    @Mapping(target = "references", source = "problem.references", qualifiedByName = "toReferenceDto")
    @Mapping(target = "relatedProblems", source = "problem.relatedProblems", qualifiedByName = "toProblemSummary")
    @Mapping(target = "prerequisites", source = "problem.prerequisites", qualifiedByName = "toConceptSummary")
    @Mapping(target = "mastery", source = "mastery", qualifiedByName = "toMasteryDto")
    ProblemChapterDto toChapterDto(Problem problem, MasteryRecord mastery);
    
    @Mapping(target = "id", source = "problem.id")
    @Mapping(target = "patterns", source = "problem.patterns", qualifiedByName = "toPatternSlugs")
    @Mapping(target = "algorithms", source = "problem.algorithms", qualifiedByName = "toAlgorithmSlugs")
    @Mapping(target = "dataStructures", source = "problem.dataStructures", qualifiedByName = "toDataStructureSlugs")
    @Mapping(target = "masteryStatus", source = "mastery", qualifiedByName = "toMasteryStatus")
    ProblemSummaryDto toSummaryDto(Problem problem, MasteryRecord mastery);
    
    @Named("toPatternSummary")
    default List<PatternSummaryDto> toPatternSummary(Set<Pattern> patterns) {
        if (patterns == null) return List.of();
        return patterns.stream()
            .map(p -> PatternSummaryDto.builder()
                .id(p.getId())
                .slug(p.getSlug())
                .name(p.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toPatternSlugs")
    default List<String> toPatternSlugs(Set<Pattern> patterns) {
        if (patterns == null) return List.of();
        return patterns.stream().map(Pattern::getSlug).collect(Collectors.toList());
    }
    
    @Named("toAlgorithmSummary")
    default List<AlgorithmSummaryDto> toAlgorithmSummary(Set<Algorithm> algorithms) {
        if (algorithms == null) return List.of();
        return algorithms.stream()
            .map(a -> AlgorithmSummaryDto.builder()
                .id(a.getId())
                .slug(a.getSlug())
                .name(a.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toAlgorithmSlugs")
    default List<String> toAlgorithmSlugs(Set<Algorithm> algorithms) {
        if (algorithms == null) return List.of();
        return algorithms.stream().map(Algorithm::getSlug).collect(Collectors.toList());
    }
    
    @Named("toDataStructureSummary")
    default List<DataStructureSummaryDto> toDataStructureSummary(Set<DataStructure> dss) {
        if (dss == null) return List.of();
        return dss.stream()
            .map(d -> DataStructureSummaryDto.builder()
                .id(d.getId())
                .slug(d.getSlug())
                .name(d.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toDataStructureSlugs")
    default List<String> toDataStructureSlugs(Set<DataStructure> dss) {
        if (dss == null) return List.of();
        return dss.stream().map(DataStructure::getSlug).collect(Collectors.toList());
    }
    
    @Named("toTemplateSummary")
    default List<TemplateSummaryDto> toTemplateSummary(Set<Template> templates) {
        if (templates == null) return List.of();
        return templates.stream()
            .map(t -> TemplateSummaryDto.builder()
                .id(t.getId())
                .slug(t.getSlug())
                .name(t.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toVariationDto")
    default List<ProblemVariationDto> toVariationDto(Set<ProblemVariation> variations) {
        if (variations == null) return List.of();
        return variations.stream()
            .map(v -> ProblemVariationDto.builder()
                .id(v.getId())
                .name(v.getName())
                .description(v.getDescription())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toSolutionDto")
    default List<SolutionDto> toSolutionDto(Set<Solution> solutions) {
        if (solutions == null) return List.of();
        return solutions.stream()
            .map(s -> SolutionDto.builder()
                .id(s.getId())
                .name(s.getName())
                .approach(s.getApproach())
                .explanation(s.getExplanation())
                .whyItWorks(s.getWhyItWorks())
                .whyNotIdeal(s.getWhyNotIdeal())
                .bottleneckRemaining(s.getBottleneckRemaining())
                .insightToNext(s.getInsightToNext())
                .complexity(toComplexityDto(s.getComplexity()))
                .language(s.getLanguage())
                .code(s.getCode())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toTestCaseDto")
    default List<TestCaseDto> toTestCaseDto(Set<TestCase> testCases) {
        if (testCases == null) return List.of();
        return testCases.stream()
            .map(t -> TestCaseDto.builder()
                .id(t.getId())
                .name(t.getName())
                .input(t.getInput())
                .expectedOutput(t.getExpectedOutput())
                .description(t.getDescription())
                .type(t.getType())
                .isHidden(t.getIsHidden())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toLearningObjectiveDto")
    default List<LearningObjectiveDto> toLearningObjectiveDto(Set<LearningObjective> objectives) {
        if (objectives == null) return List.of();
        return objectives.stream()
            .map(o -> LearningObjectiveDto.builder()
                .id(o.getId())
                .description(o.getDescription())
                .bloomLevel(o.getBloomLevel().name())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toReferenceDto")
    default List<ReferenceDto> toReferenceDto(Set<Reference> references) {
        if (references == null) return List.of();
        return references.stream()
            .map(r -> ReferenceDto.builder()
                .id(r.getId())
                .title(r.getTitle())
                .url(r.getUrl())
                .type(r.getType().name())
                .relevantConcept(r.getRelevantConcept())
                .verifies(r.getVerifies())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toProblemSummary")
    default List<ProblemReferenceDto> toProblemSummary(Set<Problem> problems) {
        if (problems == null) return List.of();
        return problems.stream()
            .map(p -> ProblemReferenceDto.builder()
                .id(p.getId())
                .slug(p.getSlug())
                .title(p.getTitle())
                .difficulty(p.getDifficulty().name())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toConceptSummary")
    default List<ConceptSummaryDto> toConceptSummary(Set<Concept> concepts) {
        if (concepts == null) return List.of();
        return concepts.stream()
            .map(c -> ConceptSummaryDto.builder()
                .id(c.getId())
                .slug(c.getSlug())
                .name(c.getName())
                .build())
            .collect(Collectors.toList());
    }
    
    @Named("toMasteryDto")
    default MasteryDto toMasteryDto(MasteryRecord mastery) {
        if (mastery == null) {
            return MasteryDto.builder()
                .status(com.dsamastery.common.enums.MasteryStatus.NOT_STARTED)
                .score(0)
                .viewed(false)
                .explanationViewed(false)
                .templateViewed(false)
                .attempts(0)
                .passedTests(false)
                .solutionRevealed(false)
                .optimalUnderstood(false)
                .variationsAttempted(false)
                .build();
        }
        return MasteryDto.builder()
            .status(mastery.getStatus())
            .score(mastery.getMasteryScore())
            .viewed(mastery.getViewed())
            .explanationViewed(mastery.getExplanationViewed())
            .templateViewed(mastery.getTemplateViewed())
            .attempts(mastery.getAttempts())
            .passedTests(mastery.getPassedTests())
            .solutionRevealed(mastery.getSolutionRevealed())
            .optimalUnderstood(mastery.getOptimalUnderstood())
            .variationsAttempted(mastery.getVariationsAttempted())
            .build();
    }
    
    @Named("toMasteryStatus")
    default com.dsamastery.common.enums.MasteryStatus toMasteryStatus(MasteryRecord mastery) {
        return mastery != null ? mastery.getStatus() : com.dsamastery.common.enums.MasteryStatus.NOT_STARTED;
    }
    
    @Named("toComplexityDto")
    default ComplexityDto toComplexityDto(com.dsamastery.common.entity.Complexity c) {
        if (c == null) return null;
        return ComplexityDto.builder()
            .timeComplexity(c.getTimeComplexity())
            .spaceComplexity(c.getSpaceComplexity())
            .timeReasoning(c.getTimeReasoning())
            .spaceReasoning(c.getSpaceReasoning())
            .bestCaseTime(c.getBestCaseTime())
            .worstCaseTime(c.getWorstCaseTime())
            .averageCaseTime(c.getAverageCaseTime())
            .build();
    }
}