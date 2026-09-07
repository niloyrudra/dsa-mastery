package com.dsamastery.api.dto;

import lombok.*;

import java.util.UUID;

/**
 * Lightweight reference to a problem, used inside detail DTOs
 * (e.g. related problems, example problems in a template).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemReferenceDto {
    private UUID id;
    private String slug;
    private String title;
    private String difficulty;
}