package com.dsamastery.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatternSummaryDto {
    private UUID id;
    private String slug;
    private String name;
}