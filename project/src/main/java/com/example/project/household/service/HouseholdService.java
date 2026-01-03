package com.example.project.household.service;

import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;

import java.util.List;

public interface HouseholdService {

    List<HouseholdDTO> findAll(
            Long id,
            Integer roomNumber,
            String ownerName,
            Double area,
            Integer residentCount,
            Integer vehicleCount,
            Boolean isVacant
    );

    HouseholdDTO findById(Long id);

    HouseholdDTO create(HouseholdCreateDTO dto);

    HouseholdDTO update(Long id, HouseholdUpdateDTO dto);

    void delete(Long id);
}
