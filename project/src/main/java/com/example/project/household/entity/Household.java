package com.example.project.household.entity;

import com.example.project.common.base.BaseEntity;
import com.example.project.resident.entity.Resident;
import com.example.project.FeePayment.entity.FeePayment;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.*;

@Entity
@Table(name = "households")
@EqualsAndHashCode(callSuper = true)
public class Household extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String roomNumber;
    private String ownerName;
    @Column(length = 12)
    private String ownerCccd;
    private Double area;
    private Integer residentCount;
    private Integer carCount;
    private Integer bikeCount;
    private Boolean isVacant;

    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY)
    private List<FeePayment> roomFeesPayments = new ArrayList<>();

    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY)
    private List<Resident> residents = new ArrayList<>();

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
