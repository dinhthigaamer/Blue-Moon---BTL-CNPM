package com.example.project.statistics.dto.MonthlyRevenue;
import java.math.BigDecimal;
import lombok.Data;

//dto truyền ra
@Data
public class MonthlyRevenueOutDTO {
    private Integer year;
    private Integer month;
    private BigDecimal total;
}
