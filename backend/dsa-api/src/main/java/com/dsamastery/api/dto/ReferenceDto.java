package com.dsamastery.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceDto {
    private UUID id;
    private String title;
    private String url;
    private String type;
    private String relevantConcept;
    private String verifies;
}