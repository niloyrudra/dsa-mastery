package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.entity.Complexity;
import com.dsamastery.common.enums.Approach;
import com.dsamastery.common.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "solutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solution extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "approach", nullable = false, length = 10)
    private Approach approach;
    
    @Column(name = "explanation", columnDefinition = "TEXT", nullable = false)
    private String explanation;
    
    @Column(name = "why_it_works", columnDefinition = "TEXT", nullable = false)
    private String whyItWorks;
    
    @Column(name = "why_not_ideal", columnDefinition = "TEXT")
    private String whyNotIdeal;
    
    @Column(name = "bottleneck_remaining", columnDefinition = "TEXT")
    private String bottleneckRemaining;
    
    @Column(name = "insight_to_next", columnDefinition = "TEXT")
    private String insightToNext;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "timeComplexity", column = @Column(name = "time_complexity")),
        @AttributeOverride(name = "spaceComplexity", column = @Column(name = "space_complexity")),
        @AttributeOverride(name = "timeReasoning", column = @Column(name = "time_reasoning")),
        @AttributeOverride(name = "spaceReasoning", column = @Column(name = "space_reasoning")),
        @AttributeOverride(name = "bestCaseTime", column = @Column(name = "best_case_time")),
        @AttributeOverride(name = "worstCaseTime", column = @Column(name = "worst_case_time")),
        @AttributeOverride(name = "averageCaseTime", column = @Column(name = "average_case_time"))
    })
    private Complexity complexity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false, length = 20)
    private Language language;
    
    @Column(name = "code", columnDefinition = "TEXT", nullable = false)
    private String code;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private Template template;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_id")
    private ProblemVariation variation;
    
    @OneToMany(mappedBy = "solution", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<TemplateMapping> mappings = new ArrayList<>();
}