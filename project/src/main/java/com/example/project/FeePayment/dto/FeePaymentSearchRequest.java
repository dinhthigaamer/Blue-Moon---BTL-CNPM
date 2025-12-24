package com.example.project.FeePayment.dto;
import java.time.LocalDate;


import lombok.Data;

@Data
public class FeePaymentSearchRequest {
    private Long householdId;
    private Long feeId;
    private Integer billingYear;
    private Integer billingMonth;
    private LocalDate startFrom;
    private LocalDate startTo;
    private LocalDate dueFrom;
    private LocalDate dueTo;
    private Boolean mandatory;
    private Boolean paid;
}

