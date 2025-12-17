package com.example.project.household.dto;

public class HouseholdCreateDTO {

    private Integer roomNumber;
    private String ownerName;
    private Boolean isVacant;
    private Long roomFeeId;

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

    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }

    public Long roomFeeId(){
        return roomFeeId;
    }
}
