package com.example.project.resident.entity;

import com.example.project.common.base.BaseEntity;
import com.example.project.household.entity.Household;
import jakarta.persistence.*;

@Entity
@Table(name = "residents")
public class Resident extends BaseEntity {

    @Column(nullable = false)
    private String fullName;

    private String phone;

    private Integer vehicleCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Integer vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }


}
