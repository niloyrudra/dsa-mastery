package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.ContentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "concepts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concept extends BaseEntity {
    
    @Column(name = "slug", unique = true, nullable = false, length = 100)
    private String slug;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;
    
    @ManyToMany
    @JoinTable(
        name = "concept_prerequisites",
        joinColumns = @JoinColumn(name = "concept_id"),
        inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    @Builder.Default
    private Set<Concept> prerequisites = new LinkedHashSet<>();
    
    @ManyToMany(mappedBy = "prerequisites")
    @Builder.Default
    private Set<Problem> problems = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "concept_patterns",
        joinColumns = @JoinColumn(name = "concept_id"),
        inverseJoinColumns = @JoinColumn(name = "pattern_id")
    )
    @Builder.Default
    private Set<Pattern> patterns = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "concept_references",
        joinColumns = @JoinColumn(name = "concept_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    @Builder.Default
    private Set<Reference> references = new LinkedHashSet<>();
}