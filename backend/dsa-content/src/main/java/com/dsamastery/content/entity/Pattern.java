package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.entity.Complexity;
import com.dsamastery.common.enums.ContentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "patterns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pattern extends BaseEntity {
    
    @Column(name = "slug", unique = true, nullable = false, length = 100)
    private String slug;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "category", length = 50)
    private String category;
    
    @Column(name = "definition", columnDefinition = "TEXT", nullable = false)
    private String definition;
    
    @Column(name = "when_to_use", columnDefinition = "TEXT", nullable = false)
    private String whenToUse;
    
    @Column(name = "why_it_works", columnDefinition = "TEXT", nullable = false)
    private String whyItWorks;
    
    @Column(name = "intuition", columnDefinition = "TEXT")
    private String intuition;
    
    @Column(name = "invariant", columnDefinition = "TEXT")
    private String invariant;
    
    @Column(name = "strengths", columnDefinition = "TEXT")
    private String strengths;
    
    @Column(name = "weaknesses", columnDefinition = "TEXT")
    private String weaknesses;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "recognition_signals", columnDefinition = "JSONB")
    @Builder.Default
    private List<String> recognitionSignals = new ArrayList<>();
    
    @Column(name = "common_failure_modes", columnDefinition = "TEXT")
    private String commonFailureModes;
    
    @Column(name = "generalizations", columnDefinition = "TEXT")
    private String generalizations;
    
    @Embedded
    private Complexity complexity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;
    
    @ManyToMany(mappedBy = "patterns")
    @Builder.Default
    private Set<Problem> problems = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "pattern_templates",
        joinColumns = @JoinColumn(name = "pattern_id"),
        inverseJoinColumns = @JoinColumn(name = "template_id")
    )
    @Builder.Default
    private Set<Template> templates = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "pattern_algorithms",
        joinColumns = @JoinColumn(name = "pattern_id"),
        inverseJoinColumns = @JoinColumn(name = "algorithm_id")
    )
    @Builder.Default
    private Set<Algorithm> algorithms = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "pattern_data_structures",
        joinColumns = @JoinColumn(name = "pattern_id"),
        inverseJoinColumns = @JoinColumn(name = "data_structure_id")
    )
    @Builder.Default
    private Set<DataStructure> dataStructures = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "pattern_relationships",
        joinColumns = @JoinColumn(name = "pattern_id"),
        inverseJoinColumns = @JoinColumn(name = "related_pattern_id")
    )
    @Builder.Default
    private Set<Pattern> subPatterns = new LinkedHashSet<>();
    
    @ManyToMany(mappedBy = "subPatterns")
    @Builder.Default
    private Set<Pattern> parentPatterns = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "pattern_references",
        joinColumns = @JoinColumn(name = "pattern_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    @Builder.Default
    private Set<Reference> references = new LinkedHashSet<>();
}