package com.example.project.statistics.dto.FeeSummary;

import java.math.BigDecimal;


//dto truyền ra

public class FeeSummaryOutDTO {
    private Long fee_id;
    private String name;
    private BigDecimal total;
    private Integer year;
    private Integer month;

    public Long getFee_id() {
        return fee_id;
    }

    public void setFee_id(Long fee_id) {
        this.fee_id = fee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
