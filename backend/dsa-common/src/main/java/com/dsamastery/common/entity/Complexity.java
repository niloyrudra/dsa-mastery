package com.dsamastery.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complexity {
    
    @Column(name = "time_complexity", length = 100)
    private String timeComplexity;
    
    @Column(name = "space_complexity", length = 100)
    private String spaceComplexity;
    
    @Column(name = "time_reasoning", columnDefinition = "TEXT")
    private String timeReasoning;
    
    @Column(name = "space_reasoning", columnDefinition = "TEXT")
    private String spaceReasoning;
    
    @Column(name = "best_case_time", length = 100)
    private String bestCaseTime;
    
    @Column(name = "worst_case_time", length = 100)
    private String worstCaseTime;
    
    @Column(name = "average_case_time", length = 100)
    private String averageCaseTime;
}