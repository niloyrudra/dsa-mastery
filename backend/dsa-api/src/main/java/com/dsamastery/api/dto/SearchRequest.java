package com.dsamastery.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private String q;
    private String type;
    private Integer limit;
}