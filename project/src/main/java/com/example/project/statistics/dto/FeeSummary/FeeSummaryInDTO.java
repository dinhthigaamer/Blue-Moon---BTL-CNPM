package com.example.project.statistics.dto.FeeSummary;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

//dto truyền vào
@Data
public class FeeSummaryInDTO {
    @NotNull
    private Long feeId;
    @NotNull
    private Integer year;
    @NotNull
    private Integer month;
    
}
