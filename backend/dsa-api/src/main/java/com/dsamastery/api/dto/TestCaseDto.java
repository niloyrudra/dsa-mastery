package com.dsamastery.api.dto;

import com.dsamastery.common.enums.TestCaseType;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseDto {
    private UUID id;
    private String name;
    private Object input;
    private Object expectedOutput;
    private String description;
    private TestCaseType type;
    private Boolean isHidden;
}