package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.entity.Complexity;
import com.dsamastery.common.enums.ContentStatus;
import com.dsamastery.common.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "problem_patterns",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "pattern_id")
    )
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<Pattern> patterns = new LinkedHashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "problem_algorithms",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "algorithm_id")
    )
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<Algorithm> algorithms = new LinkedHashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "problem_data_structures",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "data_structure_id")
    )
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<DataStructure> dataStructures = new LinkedHashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "problem_templates",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<Template> templates = new LinkedHashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "problem_related",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "related_problem_id")
    )
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<Problem> relatedProblems = new LinkedHashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "problem_prerequisites",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "concept_id")
    )
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<Concept> prerequisites = new LinkedHashSet<>();
    
    @OneToMany(mappedBy = "baseProblem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<ProblemVariation> variations = new LinkedHashSet<>();
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<Solution> solutions = new LinkedHashSet<>();
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<TestCase> testCases = new LinkedHashSet<>();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "problem_references",
        joinColumns = @JoinColumn(name = "problem_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<Reference> references = new LinkedHashSet<>();
    
    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private Set<LearningObjective> learningObjectives = new LinkedHashSet<>();
    
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