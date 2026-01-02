package com.example.project.household.dto;

import io.swagger.v3.oas.annotations.media.Schema;
public class HouseholdCreateDTO {

    private Integer roomNumber;
    private String ownerName;
    @Schema(description = "Diện tích căn hộ (m2)")
    private Double area;
    private Boolean isVacant;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }
    

}
