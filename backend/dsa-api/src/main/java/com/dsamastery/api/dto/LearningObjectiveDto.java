package com.dsamastery.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningObjectiveDto {
    private UUID id;
    private String description;
    private String bloomLevel;
}