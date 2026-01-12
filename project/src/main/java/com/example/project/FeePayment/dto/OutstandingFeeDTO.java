package com.example.project.FeePayment.dto;

import java.math.BigDecimal;

public class OutstandingFeeDTO {
    private String roomNumber;
    private BigDecimal totalOutstandingAmount;

    // Constructors, getters, setters
    public OutstandingFeeDTO(String roomNumber, BigDecimal totalOutstandingAmount) {
        this.roomNumber = roomNumber;
        this.totalOutstandingAmount = totalOutstandingAmount;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getTotalOutstandingAmount() {
        return totalOutstandingAmount;
    }

    public void setTotalOutstandingAmount(BigDecimal totalOutstandingAmount) {
        this.totalOutstandingAmount = totalOutstandingAmount;
    }
}