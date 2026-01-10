package com.example.project.FeePayment.dto;

import java.time.LocalDate;

public class FeePaymentSearchRequest {
    private String roomNumber;
    private Long feeId;
    private Integer billingYear;
    private Integer billingMonth;
    private LocalDate startFrom;
    private LocalDate startTo;
    private LocalDate dueFrom;
    private LocalDate dueTo;
    private Boolean mandatory;
    private Boolean paid;

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

    public LocalDate getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(LocalDate startFrom) {
        this.startFrom = startFrom;
    }

    public LocalDate getStartTo() {
        return startTo;
    }

    public void setStartTo(LocalDate startTo) {
        this.startTo = startTo;
    }

    public LocalDate getDueFrom() {
        return dueFrom;
    }

    public void setDueFrom(LocalDate dueFrom) {
        this.dueFrom = dueFrom;
    }

    public LocalDate getDueTo() {
        return dueTo;
    }

    public void setDueTo(LocalDate dueTo) {
        this.dueTo = dueTo;
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

}
