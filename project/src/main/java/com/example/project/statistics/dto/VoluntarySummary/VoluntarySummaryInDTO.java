package com.example.project.statistics.dto.VoluntarySummary;

import jakarta.validation.constraints.NotNull;


//dto truyền vào

public class VoluntarySummaryInDTO {
    @NotNull
    private Integer year;
    @NotNull
    private Integer month;

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