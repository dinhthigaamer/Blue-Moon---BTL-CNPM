package com.example.project.household.service;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;
import com.example.project.household.entity.Household;
import com.example.project.household.mapper.HouseholdMapper;
import com.example.project.household.repository.HouseholdRepository;
import com.example.project.household.service.HouseholdService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<HouseholdDTO> findAll(Integer page, Boolean isActive) {
        List<Household> households = householdRepository.findAll();
        List<HouseholdDTO> result = new ArrayList<>();

        for (Household h : households) {
            result.add(householdMapper.toDTO(h));
        }
        return result;
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
        householdRepository.save(household);
        return householdMapper.toDTO(household);
    }

    @Override
    public HouseholdDTO update(Long id, HouseholdUpdateDTO dto) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        householdMapper.updateEntity(dto, household);
        householdRepository.save(household);

        return householdMapper.toDTO(household);
    }

    @Override
    public void delete(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        householdRepository.delete(household);
    }
}
