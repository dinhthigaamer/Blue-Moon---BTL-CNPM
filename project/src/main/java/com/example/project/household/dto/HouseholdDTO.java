package com.example.project.household.dto;

import com.example.project.resident.dto.ResidentDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class HouseholdDTO {

    private Long id;
    private String roomNumber;
    private String ownerName;
    private String ownerCccd;
    @Schema(description = "Diện tích căn hộ (m2)")
    private Double area;
    private Integer residentCount;
    private Integer carCount;
    private Integer bikeCount;
    private Boolean isVacant;  
    private List<ResidentDTO> residents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getResidentCount() {
        return residentCount;
    }

    public void setResidentCount(Integer residentCount) {
        this.residentCount = residentCount;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public Integer getBikeCount() {
        return bikeCount;
    }

    public void setBikeCount(Integer bikeCount) {
        this.bikeCount = bikeCount;
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
