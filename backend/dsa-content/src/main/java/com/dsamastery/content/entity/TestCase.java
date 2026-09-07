package com.dsamastery.content.entity;

import com.dsamastery.common.entity.BaseEntity;
import com.dsamastery.common.enums.TestCaseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "test_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variation_id")
    private ProblemVariation variation;
    
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "input", columnDefinition = "JSONB", nullable = false)
    private Object input;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "expected_output", columnDefinition = "JSONB", nullable = false)
    private Object expectedOutput;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private TestCaseType type;
    
    @Column(name = "is_hidden", nullable = false)
    @Builder.Default
    private Boolean isHidden = false;
}