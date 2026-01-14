package com.example.project.FeePayment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

//dto này để tạo mới bản ghi

public class FeePaymentCreateDTO {
    @NotNull(message = "số phòng không được để trống")
    private String roomNumber;
    @NotNull(message = "feeId không được để trống")
    private Long feeId;
    @PositiveOrZero(message = "trường mức sử dụng phải lớn hơn hoặc bằng 0")
    private BigDecimal usageAmount;
    @PositiveOrZero(message = "trường số tiền đóng góp phải lớn hơn hoặc bằng 0")
    private BigDecimal voluntaryAmount;
    @NotNull(message = "năm thu không được để trống")
    private Integer billingYear;
    @NotNull(message = "tháng thu không được để trống")
    private Integer billingMonth;
    private LocalDate startDate;
    private LocalDate dueDate;
    @NotNull(message = "Trường mandatory không được để trống")
    private Boolean mandatory;

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

}