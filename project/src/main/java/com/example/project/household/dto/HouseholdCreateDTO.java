package com.example.project.household.dto;

import lombok.Data;

@Data
public class HouseholdCreateDTO {

    private Integer roomNumber;
    private String ownerName;
    private Boolean isVacant;

}
