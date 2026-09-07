package com.dsamastery.api.dto;

import com.dsamastery.common.enums.Language;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionRequest {
    private UUID problemId;
    private Language language;
    private String code;
    private List<String> testCases;
}