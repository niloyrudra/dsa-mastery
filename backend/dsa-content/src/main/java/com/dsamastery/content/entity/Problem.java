package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.entity.Complexity;
import com.dsamastery.common.enums.ContentStatus;
import com.dsamastery.common.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem extends BaseEntity {
    
    @Column(name = "slug", unique = true, nullable = false, length = 200)
    private String slug;
    
    @Column(name = "title", nullable = false, length = 500)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(name = "external_url", length = 1000)
    private String externalUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false, length = 10)
    private Difficulty difficulty;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;
    
    @Column(name = "order_index", nullable = false)
    @Builder.Default
    private Integer orderIndex = 0;
    
    @Column(name = "reason_for_selection", columnDefinition = "TEXT")
    private String reasonForSelection;
    
    @ManyToMany
    @JoinTable(
        name = "problem_patterns",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "pattern_id")
    )
    @Builder.Default
    private List<Pattern> patterns = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "problem_algorithms",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "algorithm_id")
    )
    @Builder.Default
    private List<Algorithm> algorithms = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "problem_data_structures",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "data_structure_id")
    )
    @Builder.Default
    private List<DataStructure> dataStructures = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "problem_templates",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    @Builder.Default
    private List<Template> templates = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "problem_related",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "related_problem_id")
    )
    @Builder.Default
    private List<Problem> relatedProblems = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "problem_prerequisites",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "concept_id")
    )
    @Builder.Default
    private List<Concept> prerequisites = new ArrayList<>();
    
    @OneToMany(mappedBy = "baseProblem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProblemVariation> variations = new ArrayList<>();
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Solution> solutions = new ArrayList<>();
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TestCase> testCases = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "problem_references",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    @Builder.Default
    private List<Reference> references = new ArrayList<>();
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LearningObjective> learningObjectives = new ArrayList<>();
    
    // Helper methods
    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
        pattern.getProblems().add(this);
    }
    
    public void addAlgorithm(Algorithm algorithm) {
        algorithms.add(algorithm);
        algorithm.getProblems().add(this);
    }
    
    public void addDataStructure(DataStructure ds) {
        dataStructures.add(ds);
        ds.getProblems().add(this);
    }
    
    public void addTemplate(Template template) {
        templates.add(template);
        template.getProblems().add(this);
    }
    
    public void addRelatedProblem(Problem related) {
        relatedProblems.add(related);
    }
    
    public void addPrerequisite(Concept concept) {
        prerequisites.add(concept);
        concept.getProblems().add(this);
    }
}