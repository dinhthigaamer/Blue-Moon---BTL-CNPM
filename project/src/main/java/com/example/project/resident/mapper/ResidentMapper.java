package com.example.project.resident.mapper;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.household.entity.Household;
import com.example.project.household.repository.HouseholdRepository;
import com.example.project.resident.dto.ResidentCreateDTO;
import com.example.project.resident.dto.ResidentDTO;
import com.example.project.resident.dto.ResidentUpdateDTO;
import com.example.project.resident.entity.Resident;
import org.springframework.stereotype.Component;

@Component
public class ResidentMapper {

    private final HouseholdRepository householdRepository;

    public ResidentMapper(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    public ResidentDTO toDTO(Resident entity) {
        ResidentDTO dto = new ResidentDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setCccd(entity.getCccd());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setReligion(entity.getReligion());
        dto.setEthnicity(entity.getEthnicity());
        dto.setOccupation(entity.getOccupation());
        dto.setGender(entity.getGender());
        dto.setResidenceStatus(entity.getResidenceStatus());
        dto.setCarCount(entity.getCarCount());
        dto.setBikeCount(entity.getBikeCount());

        if (entity.getHousehold() != null) {
            dto.setHouseholdId(entity.getHousehold().getId());
            dto.setRoomNumber(entity.getHousehold().getRoomNumber());
        }
        return dto;
    }

    public Resident toEntity(ResidentCreateDTO dto) {
        Resident r = new Resident();
        r.setFullName(dto.getFullName());
        r.setPhone(dto.getPhone());
        r.setEmail(dto.getEmail());
        r.setCccd(dto.getCccd());
        r.setDateOfBirth(dto.getDateOfBirth());
        r.setReligion(dto.getReligion());
        r.setEthnicity(dto.getEthnicity());
        r.setOccupation(dto.getOccupation());
        r.setGender(dto.getGender());
        r.setResidenceStatus(dto.getResidenceStatus());
        r.setCarCount(dto.getCarCount() == null ? 0 : dto.getCarCount());
        r.setBikeCount(dto.getBikeCount() == null ? 0 : dto.getBikeCount());

        Household household = householdRepository.findByRoomNumber(dto.getRoomNumber())
                .orElseThrow(() -> new ApiException(
                        ErrorCode.NOT_FOUND,
                        "Hộ dân có roomNumber " + dto.getRoomNumber() + " không tồn tại"
                ));
        r.setHousehold(household);

        return r;
    }

    public void updateEntity(ResidentUpdateDTO dto, Resident entity) {
        if (dto.getFullName() != null) entity.setFullName(dto.getFullName());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getCccd() != null) entity.setCccd(dto.getCccd());
        if (dto.getDateOfBirth() != null) entity.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getReligion() != null) entity.setReligion(dto.getReligion());
        if (dto.getEthnicity() != null) entity.setEthnicity(dto.getEthnicity());
        if (dto.getOccupation() != null) entity.setOccupation(dto.getOccupation());
        if (dto.getGender() != null) entity.setGender(dto.getGender());
        if (dto.getResidenceStatus() != null) entity.setResidenceStatus(dto.getResidenceStatus());
        if (dto.getCarCount() != null) entity.setCarCount(dto.getCarCount());
        if (dto.getBikeCount() != null) entity.setBikeCount(dto.getBikeCount());

        if (dto.getRoomNumber() != null) {
            Household household = householdRepository.findByRoomNumber(dto.getRoomNumber())
                    .orElseThrow(() -> new ApiException(
                            ErrorCode.NOT_FOUND,
                            "Hộ dân có roomNumber " + dto.getRoomNumber() + " không tồn tại"
                    ));
            entity.setHousehold(household);
        }
    }
}
