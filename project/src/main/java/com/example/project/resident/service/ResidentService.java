package com.example.project.resident.service;

import com.example.project.resident.dto.ResidentCreateDTO;
import com.example.project.resident.dto.ResidentDTO;
import com.example.project.resident.dto.ResidentUpdateDTO;

import java.util.List;

public interface ResidentService {
    List<ResidentDTO> findAll(Long householdId, Integer roomNumber);
    ResidentDTO findById(Long id);
    ResidentDTO create(ResidentCreateDTO dto);
    ResidentDTO update(Long id, ResidentUpdateDTO dto);
    void delete(Long id);
}
