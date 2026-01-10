package com.example.project.resident.service.impl;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.household.entity.Household;
import com.example.project.household.repository.HouseholdRepository;
import com.example.project.resident.dto.ResidentCreateDTO;
import com.example.project.resident.dto.ResidentDTO;
import com.example.project.resident.dto.ResidentUpdateDTO;
import com.example.project.resident.entity.Resident;
import com.example.project.resident.entity.Gender;
import com.example.project.resident.entity.ResidenceStatus;
import com.example.project.resident.mapper.ResidentMapper;
import com.example.project.resident.service.ResidentService;
import com.example.project.resident.repository.ResidentRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<ResidentDTO> findAll(
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
    ) {
        Specification<Resident> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(cb.equal(root.get("id"), id));
            }
            if (householdId != null) {
                predicates.add(cb.equal(root.get("household").get("id"), householdId));
            }
            if (roomNumber != null) {
                predicates.add(cb.equal(root.get("household").get("roomNumber"), roomNumber));
            }
            if (fullName != null && !fullName.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%"));
            }
            if (phone != null && !phone.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("phone")), "%" + phone.toLowerCase() + "%"));
            }
            if (email != null && !email.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }
            if (cccd != null && !cccd.isBlank()) {
                predicates.add(cb.equal(root.get("cccd"), cccd));
            }
            if (dateOfBirth != null) {
                predicates.add(cb.equal(root.get("dateOfBirth"), dateOfBirth));
            }
            if (religion != null && !religion.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("religion")), "%" + religion.toLowerCase() + "%"));
            }
            if (ethnicity != null && !ethnicity.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("ethnicity")), "%" + ethnicity.toLowerCase() + "%"));
            }
            if (occupation != null && !occupation.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("occupation")), "%" + occupation.toLowerCase() + "%"));
            }
            if (gender != null) {
                predicates.add(cb.equal(root.get("gender"), gender));
            }
            if (residenceStatus != null) {
                predicates.add(cb.equal(root.get("residenceStatus"), residenceStatus));
            }
            if (carCount != null) {
                predicates.add(cb.equal(root.get("carCount"), carCount));
            }
            if (bikeCount != null) {
                predicates.add(cb.equal(root.get("bikeCount"), bikeCount));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return residentRepository.findAll(spec).stream()
                .map(residentMapper::toDTO)
                .toList();
    }

    @Override
    public ResidentDTO findById(Long id) {
        Resident r = residentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Cư dân có ID " + id + " không tồn tại"));
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
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Cư dân có ID " + id + " không tồn tại"));

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
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Cư dân có ID " + id + " không tồn tại"));

        Long householdId = resident.getHousehold().getId();
        residentRepository.delete(resident);

        recalcHousehold(householdId);
    }

    private void recalcHousehold(Long householdId) {
        Household h = householdRepository.findById(householdId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hộ dân có ID " + householdId + " không tồn tại"));

        List<Resident> residents = residentRepository.findByHouseholdId(householdId);

        int residentCount = residents.size();
        int carCount = residents.stream()
                .map(r -> r.getCarCount() == null ? 0 : r.getCarCount())
                .reduce(0, Integer::sum);
        int bikeCount = residents.stream()
                .map(r -> r.getBikeCount() == null ? 0 : r.getBikeCount())
                .reduce(0, Integer::sum);

        h.setResidentCount(residentCount);
        h.setCarCount(carCount);
        h.setBikeCount(bikeCount);

        householdRepository.save(h);
    }
}
