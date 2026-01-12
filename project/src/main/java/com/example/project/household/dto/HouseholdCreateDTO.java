package com.example.project.household.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class HouseholdCreateDTO {

    private String roomNumber;
    private String ownerName;
    @Pattern(regexp = "\\d{12}", message = "CCCD phải gồm đúng 12 chữ số")
    private String ownerCccd;
    @Schema(description = "Diện tích căn hộ (m2)")
    @NotNull(message = "Diện tích căn hộ không được để trống")
    private Double area;
    private Boolean isVacant;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerCccd() {
        return ownerCccd;
    }

    public void setOwnerCccd(String ownerCccd) {
        this.ownerCccd = ownerCccd;
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
