package com.example.project.household.dto;

public class HouseholdCreateDTO {

    private Integer roomNumber;
    private String ownerName;
    private Boolean isVacant;
    //private Long roomFeeId;

    //Getter
    public Integer getRoomNumber() {
        return roomNumber;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public Boolean getIsVacant() {
        return isVacant;
    }
    //bỏ
    // public Long getRoomFeeId() {
    //     return roomFeeId;
    // }

    //Setter
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }
    // public void setRoomFeeId(Long roomFeeId) {
    //     this.roomFeeId = roomFeeId;
    // }
}
