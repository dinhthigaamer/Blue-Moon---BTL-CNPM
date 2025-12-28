package com.example.project.household.dto;

public class HouseholdUpdateDTO {
    private String ownerName;
    private Boolean isVacant;
    private Long roomFeeId;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }

    public Long getRoomFeeId() {
        return roomFeeId;
    }

    public void setRoomFeeId(Long roomFeeId) {
        this.roomFeeId = roomFeeId;
    }


}
