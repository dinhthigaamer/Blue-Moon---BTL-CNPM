package com.example.project.household.dto;

import com.example.project.resident.dto.ResidentDTO;
import java.util.List;

public class HouseholdDTO {

    private Long id;
    private Integer roomNumber;
    private String ownerName;
    private Integer residentCount;
    private Integer vehicleCount;
    private Boolean isVacant;  
    private List<ResidentDTO> residents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getResidentCount() {
        return residentCount;
    }

    public void setResidentCount(Integer residentCount) {
        this.residentCount = residentCount;
    }

    public Integer getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Integer vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean isVacant) {
        this.isVacant = isVacant;
    }

    public List<ResidentDTO> getResidents() {
        return residents;
    }

    public void setResidents(List<ResidentDTO> residents) {
        this.residents = residents;
    }
     

}
