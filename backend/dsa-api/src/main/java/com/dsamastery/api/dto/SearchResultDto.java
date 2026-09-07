package com.dsamastery.api.dto;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultDto {
    private String type;
    private UUID id;
    private String slug;
    private String title;
    private String snippet;
    private String difficulty;
}