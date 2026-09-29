package com.dsamastery.api.dto;

import com.dsamastery.common.enums.NodeType;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurriculumNodeDto {
    private UUID id;
    private String name;
    private String description;
    private NodeType type;
    private Integer orderIndex;
    private Integer depth;
    private UUID problemId;
    private String problemSlug;
    private UUID patternId;
    private String patternSlug;
    private UUID algorithmId;
    private String algorithmSlug;
    private List<CurriculumNodeDto> children;
}