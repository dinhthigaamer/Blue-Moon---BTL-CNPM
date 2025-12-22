package com.example.project.statistics.dto.FeeSummary;

import java.math.BigDecimal;
import lombok.Data;

//dto truyền ra
@Data
public class FeeSummaryOutDTO {
    private Long fee_id;
    private String name;
    private BigDecimal total;
    private Integer year;
    private Integer month;
}
