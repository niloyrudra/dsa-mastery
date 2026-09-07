package com.dsamastery.api.dto;

import com.dsamastery.common.enums.ContentStatus;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataStructureDetailDto {
    private UUID id;
    private String slug;
    private String name;
    private String description;
    private Map<String, String> operations;
    private String whyUsefulHere;
    private String tradeoffs;
    private String alternatives;
    private ContentStatus status;
    private List<ProblemReferenceDto> problems;
    private List<PatternSummaryDto> patterns;
    private List<AlgorithmSummaryDto> algorithms;
}