package com.dsamastery.curriculum.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.NodeType;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "curriculum_nodes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurriculumNode extends BaseEntity {
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private NodeType type;
    
    @Column(name = "order_index", nullable = false)
    @Builder.Default
    private Integer orderIndex = 0;
    
    @Column(name = "depth", nullable = false)
    @Builder.Default
    private Integer depth = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CurriculumNode parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CurriculumNode> children = new ArrayList<>();
    
    @Column(name = "problem_id")
    private UUID problemId;
    
    @Column(name = "pattern_id")
    private UUID patternId;
    
    @Column(name = "algorithm_id")
    private UUID algorithmId;
}