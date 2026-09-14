package com.dsamastery.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewStatsDto {

    private int dueCount;
    private int overdueCount;
    private int learningCount;
    private int masteredCount;
    private long[] dailyDueNext7Days;
}