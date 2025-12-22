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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "fee_payments")
@Data
@EqualsAndHashCode(callSuper = true)
public class FeePayment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee; 
    // Usage amount for variable fees
    private String name;
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
}

