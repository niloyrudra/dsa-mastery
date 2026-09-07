package com.dsamastery.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateMappingDto {
    private Integer templateMarker;
    private String solutionCodeSnippet;
    private String explanation;
}