package com.example.project.FeePayment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.PositiveOrZero;

//dto này để câp nhật bản ghi
public class FeePaymentUpdateDTO {
    private String roomNumber;
    private Long feeId;
    @PositiveOrZero(message = "Trường mức sử dụng phải lớn hơn hoặc bằng 0")
    private BigDecimal usageAmount;
    @PositiveOrZero(message = "Trường số tiền đóng góp phải lớn hơn hoặc bằng 0")
    private BigDecimal voluntaryAmount;
    private Integer billingYear;
    private Integer billingMonth;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Boolean mandatory;
    private Boolean paid;
    private LocalDate paidDate;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public BigDecimal getUsageAmount() {
        return usageAmount;
    }

    public void setUsageAmount(BigDecimal usageAmount) {
        this.usageAmount = usageAmount;
    }

    public BigDecimal getVoluntaryAmount() {
        return voluntaryAmount;
    }

    public void setVoluntaryAmount(BigDecimal voluntaryAmount) {
        this.voluntaryAmount = voluntaryAmount;
    }

    public Integer getBillingYear() {
        return billingYear;
    }

    public void setBillingYear(Integer billingYear) {
        this.billingYear = billingYear;
    }

    public Integer getBillingMonth() {
        return billingMonth;
    }

    public void setBillingMonth(Integer billingMonth) {
        this.billingMonth = billingMonth;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public LocalDate getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }

}
