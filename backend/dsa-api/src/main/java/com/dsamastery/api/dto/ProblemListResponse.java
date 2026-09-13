package com.dsamastery.api.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemListResponse {
    private List<ProblemSummaryDto> data;
    private String nextCursor;
    private Boolean hasMore;
    private Integer total;
}