package com.example.project.FeePayment.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;


//dto này để câp nhật bản ghi
@Data
public class FeePaymentUpdateDTO {
    private Long householdId;
    private Long feeId;
    private String name;
    private BigDecimal usageAmount;
    private BigDecimal voluntaryAmount;
    private Integer billingYear;
    private Integer billingMonth;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Boolean mandatory;
    private Boolean paid;
    private LocalDate paidDate;
}
