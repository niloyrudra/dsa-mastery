package com.dsamastery.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemStats {
    private Integer total;
    private Integer mastered;
    private Integer learning;
    private Integer notStarted;
}