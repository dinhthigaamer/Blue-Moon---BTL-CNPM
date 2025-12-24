package com.example.project.statistics.dto.MonthlyRevenue;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

//dto vào 
@Data
public class MonthlyRevenueInDTO {
    @NotNull
    private Integer year;
    @NotNull
    private Integer month;
}
