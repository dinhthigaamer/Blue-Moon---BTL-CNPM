package com.example.project.statistics.dto.VoluntarySummary;

import java.math.BigDecimal;


//dto truyền ra

public class VoluntarySummaryOutDTO {
    private Integer year;
    private Integer month;
    private BigDecimal total;

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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}
