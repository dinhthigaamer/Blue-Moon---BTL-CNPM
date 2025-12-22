package com.example.project.statistics.dto.VoluntarySummary;

import java.math.BigDecimal;
import lombok.Data;

//dto truyền ra
@Data
public class VoluntarySummaryOutDTO {
    private Integer year;
    private Integer month;
    private BigDecimal total;
}
