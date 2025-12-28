package com.example.project.FeePayment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.example.project.common.base.BaseEntity;
import com.example.project.fee.entity.Fee;
import com.example.project.household.entity.Household;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "fee_payments")
@EqualsAndHashCode(callSuper = true)
public class FeePayment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee; 
    private String name;
    // Usage amount for variable fees
    private BigDecimal usageAmount;
    // Total amount to be paid
    private BigDecimal amount;
    //năm thu
    private Integer billingYear;
    //Tháng thu
    private Integer billingMonth;
    //Ngày bắt đầu nhận thu
    private LocalDate startDate;
    //Hạn nộp
    private LocalDate dueDate;
    //Bắt buộc hay không
    private Boolean mandatory;
    //Trạng thái đã nộp hay chưa
    private Boolean paid;
    //Ngày nộp
    private LocalDate paidDate;

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUsageAmount() {
        return usageAmount;
    }

    public void setUsageAmount(BigDecimal usageAmount) {
        this.usageAmount = usageAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

