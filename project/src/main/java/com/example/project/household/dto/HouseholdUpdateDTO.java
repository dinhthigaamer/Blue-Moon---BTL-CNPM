package com.example.project.household.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class HouseholdUpdateDTO {
    private String ownerName;
    private String ownerCccd;
    @Schema(description = "Diện tích căn hộ (m2)")
    private Double area;
    private Boolean isVacant;
    private Long roomFeeId;

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

    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Long getRoomFeeId() {
        return roomFeeId;
    }

    public void setRoomFeeId(Long roomFeeId) {
        this.roomFeeId = roomFeeId;
    }


}
