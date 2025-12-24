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
@Data
@EqualsAndHashCode(callSuper = true)
public class Household extends BaseEntity {

    @Column(unique = true, nullable = false)
    private Integer roomNumber;
    private String ownerName;
    private Integer residentCount;
    private Integer vehicleCount;
    private Boolean isVacant;
    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY)
    private List<FeePayment> roomFeesPayments = new ArrayList<>();

    @OneToMany(mappedBy = "household", fetch = FetchType.LAZY)
    private List<Resident> residents = new ArrayList<>();

}
