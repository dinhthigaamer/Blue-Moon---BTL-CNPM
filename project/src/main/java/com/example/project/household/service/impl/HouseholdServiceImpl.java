package com.example.project.household.service.impl;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.household.dto.*;
import com.example.project.household.entity.Household;
import com.example.project.household.mapper.HouseholdMapper;
import com.example.project.household.repository.HouseholdRepository;
import com.example.project.household.service.HouseholdService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;
    private final HouseholdMapper householdMapper;

    public HouseholdServiceImpl(HouseholdRepository householdRepository,
                                HouseholdMapper householdMapper) {
        this.householdRepository = householdRepository;
        this.householdMapper = householdMapper;
    }

    @Override
    public List<HouseholdDTO> findAll(Boolean isVacant) {

        List<Household> households = householdRepository.findAll();

        return households.stream()
                .filter(h -> isVacant == null || h.getIsVacant().equals(isVacant))
                .map(householdMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HouseholdDTO findById(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        return householdMapper.toDTO(household);
    }

    @Override
    public HouseholdDTO create(HouseholdCreateDTO dto) {
        Household household = householdMapper.toEntity(dto);
        Household saved = householdRepository.save(household);
        return householdMapper.toDTO(saved);
    }

    @Override
    public HouseholdDTO update(Long id, HouseholdUpdateDTO dto) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        householdMapper.updateEntity(dto, household);
        Household updated = householdRepository.save(household);

        return householdMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        householdRepository.delete(household);
    }
}
