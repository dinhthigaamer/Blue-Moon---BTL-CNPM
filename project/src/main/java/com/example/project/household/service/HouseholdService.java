package com.example.project.household.service;

import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;

import java.util.List;

public interface HouseholdService {

    List<HouseholdDTO> findAll(
            Long id,
            String roomNumber,
            String ownerName,
            Double area,
            Integer residentCount,
            Integer carCount,
            Integer bikeCount,
            Boolean isVacant
    );

    HouseholdDTO findById(Long id);
    HouseholdDTO findByRoomNumber(String roomNumber);

    HouseholdDTO create(HouseholdCreateDTO dto);

    HouseholdDTO update(Long id, HouseholdUpdateDTO dto);
    HouseholdDTO updateByRoomNumber(String roomNumber, HouseholdUpdateDTO dto);

    void delete(Long id);
    void deleteByRoomNumber(String roomNumber);
}
