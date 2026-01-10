package com.example.project.resident.service;

import com.example.project.resident.dto.ResidentCreateDTO;
import com.example.project.resident.dto.ResidentDTO;
import com.example.project.resident.dto.ResidentUpdateDTO;
import com.example.project.resident.entity.Gender;
import com.example.project.resident.entity.ResidenceStatus;

import java.time.LocalDate;
import java.util.List;

public interface ResidentService {
    List<ResidentDTO> findAll(
            Long id,
            Long householdId,
            String roomNumber,
            String fullName,
            String phone,
            String email,
            String cccd,
            LocalDate dateOfBirth,
            String religion,
            String ethnicity,
            String occupation,
            Gender gender,
            ResidenceStatus residenceStatus,
            Integer carCount,
            Integer bikeCount
    );
    ResidentDTO findById(Long id);
    ResidentDTO create(ResidentCreateDTO dto);
    ResidentDTO update(Long id, ResidentUpdateDTO dto);
    void delete(Long id);
}
