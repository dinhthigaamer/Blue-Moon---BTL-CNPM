package com.example.project.statistics.dto;

public class ResidentAndHouseholdCountDTO {
    private Long residentCount;
    private Long householdCount;

    public ResidentAndHouseholdCountDTO() {}

    public ResidentAndHouseholdCountDTO(Long residentCount, Long householdCount) {
        this.residentCount = residentCount;
        this.householdCount = householdCount;
    }

    public Long getResidentCount() {
        return residentCount;
    }

    public void setResidentCount(Long residentCount) {
        this.residentCount = residentCount;
    }

    public Long getHouseholdCount() {
        return householdCount;
    }

    public void setHouseholdCount(Long householdCount) {
        this.householdCount = householdCount;
    }
}