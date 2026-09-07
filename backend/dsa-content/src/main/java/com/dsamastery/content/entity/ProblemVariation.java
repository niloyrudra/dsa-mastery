package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "problem_variations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemVariation extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_problem_id", nullable = false)
    private Problem baseProblem;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(name = "what_changed", columnDefinition = "TEXT", nullable = false)
    private String whatChanged;
    
    @Column(name = "original_approach_works", nullable = false)
    @Builder.Default
    private Boolean originalApproachWorks = false;
    
    @Column(name = "what_breaks", columnDefinition = "TEXT")
    private String whatBreaks;
    
    @Column(name = "new_constraint_matters", columnDefinition = "TEXT")
    private String newConstraintMatters;
    
    @Column(name = "data_structure_changes", nullable = false)
    @Builder.Default
    private Boolean dataStructureChanges = false;
    
    @Column(name = "algorithm_changes", nullable = false)
    @Builder.Default
    private Boolean algorithmChanges = false;
    
    @Column(name = "complexity_changes", columnDefinition = "TEXT")
    private String complexityChanges;
    
    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Solution> solutions = new ArrayList<>();
    
    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TestCase> testCases = new ArrayList<>();
}