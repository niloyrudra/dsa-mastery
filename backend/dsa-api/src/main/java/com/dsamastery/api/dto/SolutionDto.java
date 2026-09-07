package com.dsamastery.api.dto;

import com.dsamastery.common.enums.Approach;
import com.dsamastery.common.enums.Language;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionDto {
    private UUID id;
    private String name;
    private Approach approach;
    private String explanation;
    private String whyItWorks;
    private String whyNotIdeal;
    private String bottleneckRemaining;
    private String insightToNext;
    private ComplexityDto complexity;
    private Language language;
    private String code;
}