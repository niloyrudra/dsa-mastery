package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.entity.Complexity;
import com.dsamastery.common.enums.ContentStatus;
import com.dsamastery.common.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template extends BaseEntity {
    
    @Column(name = "slug", unique = true, nullable = false, length = 100)
    private String slug;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false, length = 20)
    private Language language;
    
    @Column(name = "purpose", columnDefinition = "TEXT", nullable = false)
    private String purpose;
    
    @Column(name = "when_to_reach_for_it", columnDefinition = "TEXT", nullable = false)
    private String whenToReachForIt;
    
    @Column(name = "prerequisites", columnDefinition = "TEXT")
    private String prerequisites;
    
    @Column(name = "mental_model", columnDefinition = "TEXT", nullable = false)
    private String mentalModel;
    
    @Column(name = "code", columnDefinition = "TEXT", nullable = false)
    private String code;
    
    @Column(name = "explanation", columnDefinition = "TEXT", nullable = false)
    private String explanation;
    
    @Column(name = "invariant", columnDefinition = "TEXT", nullable = false)
    private String invariant;
    
    @Column(name = "common_modifications", columnDefinition = "TEXT")
    private String commonModifications;
    
    @Column(name = "common_mistakes", columnDefinition = "TEXT")
    private String commonMistakes;
    
    @Embedded
    private Complexity complexity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Builder.Default
    private ContentStatus status = ContentStatus.DRAFT;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pattern_id")
    private Pattern pattern;
    
    @ManyToMany(mappedBy = "templates")
    @Builder.Default
    private List<Problem> problems = new ArrayList<>();
    
    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TemplateMapping> mappings = new ArrayList<>();

    /**
     * Marker explanations parsed from template authoring content ([1], [2], ...).
     * Transient: used only during content import; markers are persisted through TemplateMapping rows.
     */
    @Transient
    @Builder.Default
    private Map<Integer, String> markerExplanations = new LinkedHashMap<>();
}