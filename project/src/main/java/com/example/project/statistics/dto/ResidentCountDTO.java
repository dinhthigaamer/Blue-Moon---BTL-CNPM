package com.example.project.statistics.dto;


//Đếm số cư dân và hộ dân

public class ResidentCountDTO {
    private Integer totalResidents;
    private Integer totalHouseholds;

    public Integer getTotalResidents() {
        return totalResidents;
    }

    public void setTotalResidents(Integer totalResidents) {
        this.totalResidents = totalResidents;
    }

    public Integer getTotalHouseholds() {
        return totalHouseholds;
    }

    public void setTotalHouseholds(Integer totalHouseholds) {
        this.totalHouseholds = totalHouseholds;
    }

}
