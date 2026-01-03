package com.example.project.household.entity;

import com.example.project.common.base.BaseEntity;
import com.example.project.resident.entity.Resident;
import com.example.project.FeePayment.entity.FeePayment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;

@Entity
@Table(name = "households")
@EqualsAndHashCode(callSuper = true)
public class Household extends BaseEntity {

    @Column(unique = true, nullable = false)
    private Integer roomNumber;
    private String ownerName;
    private Double area;
    private Integer residentCount;
    private Integer vehicleCount;
    private Boolean isVacant;
    
    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY)
    private List<FeePayment> roomFeesPayments = new ArrayList<>();

    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY)
    private List<Resident> residents = new ArrayList<>();

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

    public List<FeePayment> getRoomFeesPayments() {
        return roomFeesPayments;
    }

    public void setRoomFeesPayments(List<FeePayment> roomFeesPayments) {
        this.roomFeesPayments = roomFeesPayments;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }

}
