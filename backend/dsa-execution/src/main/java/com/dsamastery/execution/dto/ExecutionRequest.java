package com.dsamastery.execution.dto;

import com.dsamastery.common.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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
    private List<String> testCaseNames;
}