package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.entity.Complexity;
import com.dsamastery.common.enums.ContentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "algorithms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Algorithm extends BaseEntity {
    
    @Column(name = "slug", unique = true, nullable = false, length = 100)
    private String slug;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(name = "step_by_step_reasoning", columnDefinition = "TEXT", nullable = false)
    private String stepByStepReasoning;
    
    @Column(name = "complexity_analysis", columnDefinition = "TEXT", nullable = false)
    private String complexityAnalysis;
    
    @Embedded
    private Complexity complexity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;
    
    @ManyToMany(mappedBy = "algorithms")
    @Builder.Default
    private Set<Problem> problems = new LinkedHashSet<>();
    
    @ManyToMany(mappedBy = "algorithms")
    @Builder.Default
    private Set<Pattern> patterns = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "algorithm_data_structures",
        joinColumns = @JoinColumn(name = "algorithm_id"),
        inverseJoinColumns = @JoinColumn(name = "data_structure_id")
    )
    @Builder.Default
    private Set<DataStructure> dataStructures = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "algorithm_templates",
        joinColumns = @JoinColumn(name = "algorithm_id"),
        inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    @Builder.Default
    private Set<Template> templates = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "algorithm_references",
        joinColumns = @JoinColumn(name = "algorithm_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    @Builder.Default
    private Set<Reference> references = new LinkedHashSet<>();
}