package com.example.project.household.dto;

public class HouseholdDTO {

    private Long id;
    private Integer roomNumber;
    private String ownerName;
    private Integer residentCount;
    private Integer vehicleCount;
    private Boolean isVacant;
    private Long roomFeeId;
    //Getter
    public Long getId() {
        return id;
    }
    public Integer getRoomNumber() {
        return roomNumber;
    }
    public String getOwnerName() {
        return ownerName;
    }
    public Integer getResidentCount() {
        return residentCount;
    }
    public Integer getVehicleCount() {
        return vehicleCount;
    }
    public Boolean getIsVacant() {
        return isVacant;
    }
    public Long getRoomFeeId() {
        return roomFeeId;
    }
    
    //Setter
    public void setId(Long id) {
        this.id = id;
    }
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public void setResidentCount(Integer residentCount) {
        this.residentCount = residentCount;
    }
    public void setVehicleCount(Integer vehicleCount) {
        this.vehicleCount = vehicleCount;
    }
    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }
    public void setRoomFeeId(Long roomFeeId) {
        this.roomFeeId = roomFeeId;
    }

}
