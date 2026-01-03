package com.example.project.statistics.dto.FeeSummary;

import jakarta.validation.constraints.NotNull;


//dto truyền vào

public class FeeSummaryInDTO {
    @NotNull
    private Long feeId;
    @NotNull
    private Integer year;
    @NotNull
    private Integer month;

    public Long getFeeId() {
        return feeId;
    }

    public void setFeeId(Long feeId) {
        this.feeId = feeId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
    
}
