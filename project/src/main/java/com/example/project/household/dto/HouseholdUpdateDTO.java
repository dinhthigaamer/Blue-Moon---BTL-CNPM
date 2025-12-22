package com.example.project.household.dto;

import lombok.Data;

@Data
public class HouseholdUpdateDTO {
    private String ownerName;
    private Boolean isVacant;
    private Long roomFeeId;
}
