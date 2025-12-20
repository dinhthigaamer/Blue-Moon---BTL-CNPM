package com.example.project.household.entity;

import com.example.project.common.base.BaseEntity;
import com.example.project.resident.entity.Resident;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "households")
public class Household extends BaseEntity {

    @Column(unique = true, nullable = false)
    private Integer roomNumber;

    private String ownerName;
    private Integer residentCount;
    private Integer vehicleCount;
    private Boolean isVacant;
    /* 
    @ManyToOne
    @JoinColumn(name = "room_fee_id")
    private Fee roomFee;
    */

    @OneToMany(mappedBy = "household")
    private List<Resident> residents = new ArrayList<>();
    
    public Household() {}

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
    /*
    public Fee getRoomFee() {
        return roomFee;
    }
    
    public void setRoomFee(Fee roomFee) {
        this.roomFee = roomFee;
    }
    
    public List<Resident> getResidents() {
        return residents;
    }
    
    public void setResidents(List<Resident> residents) {
        this.residents = residents;
    }
    */
}
