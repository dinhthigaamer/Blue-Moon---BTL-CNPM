package com.example.project.statistics.dto.VoluntarySummary;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

//dto truyền vào
@Data
public class VoluntarySummaryInDTO {
    @NotNull
    private Integer year;
    @NotNull
    private Integer month;
}