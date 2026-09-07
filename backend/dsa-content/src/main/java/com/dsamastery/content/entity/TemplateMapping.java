package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "template_mappings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateMapping extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;
    
    @Column(name = "template_marker", nullable = false)
    private Integer templateMarker;
    
    @Column(name = "solution_code_snippet", columnDefinition = "TEXT", nullable = false)
    private String solutionCodeSnippet;
    
    @Column(name = "explanation", columnDefinition = "TEXT", nullable = false)
    private String explanation;
}