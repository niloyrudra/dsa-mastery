package com.dsamastery.api.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDto {
    private String name;
    private Boolean passed;
    private String input;
    private String expected;
    private String actual;
    private Long durationMs;
}