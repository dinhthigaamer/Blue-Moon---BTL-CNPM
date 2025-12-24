package com.example.project.FeePayment.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

//dto này để hiển thị thông tin ra ngoài
@Data
public class FeePaymentDTO {
    private Long id;
    private Long householdId;
    private Integer roomNumber;
    private Long feeId;
    private String name;
    private BigDecimal usageAmount;
    private BigDecimal amount;
    private Integer billingYear;
    private Integer billingMonth;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Boolean mandatory;
    private Boolean paid;
    private LocalDate paidDate;
}

