package com.example.project.household.dto;

import lombok.Data;

@Data
public class HouseholdDTO {

    private Long id;
    private Integer roomNumber;
    private String ownerName;
    private Integer residentCount;
    private Integer vehicleCount;
    private Boolean isVacant;   

}
