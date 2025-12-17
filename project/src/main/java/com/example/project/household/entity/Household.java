package com.example.project.household.entity;

import com.example.project.common.base.BaseEntity;
import com.example.project.fee.entity.Fee;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "households")
public class Household extends BaseEntity {

    @Column(nullable = false, unique = true)
    private Integer roomNumber;

    private String ownerName;

    private Integer residentCount;

    private Integer vehicleCount;

    private Boolean isVacant;

    @ManyToOne
    @JoinColumn(name = "room_fee_id")
    private Fee roomFee;

    @OneToMany(mappedBy = "household")
    private List<com.example.project.resident.entity.Resident> residents;

    // getter / setter (bắt buộc)
}
