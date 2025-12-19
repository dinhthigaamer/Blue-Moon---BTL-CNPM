package com.example.project.resident.service.impl;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.household.entity.Household;
import com.example.project.household.repository.HouseholdRepository;
import com.example.project.resident.dto.ResidentCreateDTO;
import com.example.project.resident.dto.ResidentDTO;
import com.example.project.resident.dto.ResidentUpdateDTO;
import com.example.project.resident.entity.Resident;
import com.example.project.resident.mapper.ResidentMapper;
import com.example.project.resident.service.ResidentService;
import com.example.project.resident.repository.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;
    private final HouseholdRepository householdRepository;
    private final ResidentMapper residentMapper;

    public ResidentServiceImpl(
            ResidentRepository residentRepository,
            HouseholdRepository householdRepository,
            ResidentMapper residentMapper
    ) {
        this.residentRepository = residentRepository;
        this.householdRepository = householdRepository;
        this.residentMapper = residentMapper;
    }

    @Override
    public List<ResidentDTO> findAll(Long householdId, Integer roomNumber) {
        List<Resident> residents;

        if (householdId != null) {
            residents = residentRepository.findByHouseholdId(householdId);
        } else if (roomNumber != null) {
            Household h = householdRepository.findByRoomNumber(roomNumber)
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
            residents = residentRepository.findByHouseholdId(h.getId());
        } else {
            residents = residentRepository.findAll();
        }

        return residents.stream().map(residentMapper::toDTO).toList();
    }

    @Override
    public ResidentDTO findById(Long id) {
        Resident r = residentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
        return residentMapper.toDTO(r);
    }

    @Override
    @Transactional
    public ResidentDTO create(ResidentCreateDTO dto) {
        Resident r = residentMapper.toEntity(dto);
        Resident saved = residentRepository.save(r);

        recalcHousehold(saved.getHousehold().getId());
        return residentMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public ResidentDTO update(Long id, ResidentUpdateDTO dto) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        Long oldHouseholdId = resident.getHousehold().getId();

        residentMapper.updateEntity(dto, resident);
        Resident saved = residentRepository.save(resident);

        Long newHouseholdId = saved.getHousehold().getId();
        recalcHousehold(oldHouseholdId);
        if (!newHouseholdId.equals(oldHouseholdId)) {
            recalcHousehold(newHouseholdId);
        }

        return residentMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Resident resident = residentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        Long householdId = resident.getHousehold().getId();
        residentRepository.delete(resident);

        recalcHousehold(householdId);
    }

    private void recalcHousehold(Long householdId) {
        Household h = householdRepository.findById(householdId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        List<Resident> residents = residentRepository.findByHouseholdId(householdId);

        int residentCount = residents.size();
        int vehicleCount = residents.stream()
                .map(r -> r.getVehicleCount() == null ? 0 : r.getVehicleCount())
                .reduce(0, Integer::sum);

        h.setResidentCount(residentCount);
        h.setVehicleCount(vehicleCount);

        householdRepository.save(h);
    }
}
