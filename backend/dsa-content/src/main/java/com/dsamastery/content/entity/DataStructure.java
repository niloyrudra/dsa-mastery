package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.ContentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Table(name = "data_structures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataStructure extends BaseEntity {
    
    @Column(name = "slug", unique = true, nullable = false, length = 100)
    private String slug;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "operations", columnDefinition = "JSONB")
    @Builder.Default
    private Map<String, String> operations = new LinkedHashMap<>();
    
    @Column(name = "why_useful_here", columnDefinition = "TEXT")
    private String whyUsefulHere;
    
    @Column(name = "tradeoffs", columnDefinition = "TEXT")
    private String tradeoffs;
    
    @Column(name = "alternatives", columnDefinition = "TEXT")
    private String alternatives;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;
    
    @ManyToMany(mappedBy = "dataStructures")
    @Builder.Default
    private Set<Problem> problems = new LinkedHashSet<>();
    
    @ManyToMany(mappedBy = "dataStructures")
    @Builder.Default
    private Set<Pattern> patterns = new LinkedHashSet<>();
    
    @ManyToMany(mappedBy = "dataStructures")
    @Builder.Default
    private Set<Algorithm> algorithms = new LinkedHashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "data_structure_references",
        joinColumns = @JoinColumn(name = "data_structure_id"),
        inverseJoinColumns = @JoinColumn(name = "reference_id")
    )
    @Builder.Default
    private Set<Reference> references = new LinkedHashSet<>();
}