package com.example.project.FeePayment.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;


//dto này để tạo mới bản ghi
@Data
public class FeePaymentCreateDTO {
    private Long householdId;
    private Long feeId;
    private BigDecimal usageAmount;
    private BigDecimal voluntaryAmount;
    private Integer billingYear;
    private Integer billingMonth;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Boolean mandatory;
}