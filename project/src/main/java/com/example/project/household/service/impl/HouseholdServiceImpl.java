package com.example.project.household.service.impl;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.household.dto.*;
import com.example.project.household.entity.Household;
import com.example.project.household.mapper.HouseholdMapper;
import com.example.project.household.repository.HouseholdRepository;
import com.example.project.household.service.HouseholdService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
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
    public List<HouseholdDTO> findAll(
            Long id,
            String roomNumber,
            String ownerName,
            Double area,
            Integer residentCount,
            Integer carCount,
            Integer bikeCount,
            Boolean isVacant
    ) {
        Specification<Household> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if (roomNumber != null) {
                predicates.add(cb.equal(root.get("roomNumber"), roomNumber));
            }
            if (ownerName != null && !ownerName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("ownerName")), "%" + ownerName.toLowerCase() + "%"));
            }
            if (area != null) {
                predicates.add(cb.equal(root.get("area"), area));
            }
            if (residentCount != null) {
                predicates.add(cb.equal(root.get("residentCount"), residentCount));
            }
            if (carCount != null) {
                predicates.add(cb.equal(root.get("carCount"), carCount));
            }
            if (bikeCount != null) {
                predicates.add(cb.equal(root.get("bikeCount"), bikeCount));
            }
            if (isVacant != null) {
                predicates.add(cb.equal(root.get("isVacant"), isVacant));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return householdRepository.findAll(spec).stream()
                .map(householdMapper::toDTO)
                .toList();
    }

    @Override
    public HouseholdDTO findById(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hộ dân có ID " + id + " không tồn tại"));

        return householdMapper.toDTO(household);
    }

    @Override
    public HouseholdDTO findByRoomNumber(String roomNumber) {
        Household household = householdRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hộ dân có roomNumber " + roomNumber + " không tồn tại"));

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
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hộ dân có ID " + id + " không tồn tại"));

        householdMapper.updateEntity(dto, household);
        Household updated = householdRepository.save(household);

        return householdMapper.toDTO(updated);
    }

    @Override
    public HouseholdDTO updateByRoomNumber(String roomNumber, HouseholdUpdateDTO dto) {
        Household household = householdRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hộ dân có roomNumber " + roomNumber + " không tồn tại"));

        householdMapper.updateEntity(dto, household);
        Household updated = householdRepository.save(household);

        return householdMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hộ dân có ID " + id + " không tồn tại"));

        householdRepository.delete(household);
    }

    @Override
    public void deleteByRoomNumber(String roomNumber) {
        Household household = householdRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hộ dân có roomNumber " + roomNumber + " không tồn tại"));

        householdRepository.delete(household);
    }
}
