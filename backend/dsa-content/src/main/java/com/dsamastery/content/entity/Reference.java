package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.ReferenceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "entity_references")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reference extends BaseEntity {
    
    @Column(name = "title", nullable = false, length = 500)
    private String title;
    
    @Column(name = "url", nullable = false, length = 1000)
    private String url;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private ReferenceType type;
    
    @Column(name = "relevant_concept", length = 200)
    private String relevantConcept;
    
    @Column(name = "verifies", columnDefinition = "TEXT")
    private String verifies;
    
    @ManyToMany(mappedBy = "references")
    @Builder.Default
    private Set<Problem> problems = new LinkedHashSet<>();
    
    @ManyToMany(mappedBy = "references")
    @Builder.Default
    private Set<Pattern> patterns = new LinkedHashSet<>();
    
    @ManyToMany(mappedBy = "references")
    @Builder.Default
    private Set<Algorithm> algorithms = new LinkedHashSet<>();
}