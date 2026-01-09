package com.example.project.household.mapper;

import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;
import com.example.project.household.entity.Household;
import com.example.project.resident.mapper.ResidentMapper;
import org.springframework.stereotype.Component;

@Component
public class HouseholdMapper {
    private final ResidentMapper residentMapper;

    public HouseholdMapper(ResidentMapper residentMapper) {
        this.residentMapper = residentMapper;
    }
    
    /**
     * Entity -> DTO
    */
   public HouseholdDTO toDTO(Household entity) {
        if (entity == null) {
            return null;
        }

        HouseholdDTO dto = new HouseholdDTO();
        dto.setId(entity.getId());
        dto.setRoomNumber(entity.getRoomNumber());
        dto.setOwnerName(entity.getOwnerName());
        dto.setOwnerCccd(entity.getOwnerCccd());
        dto.setArea(entity.getArea());
        dto.setResidentCount(entity.getResidentCount());
        dto.setCarCount(entity.getCarCount());
        dto.setBikeCount(entity.getBikeCount());
        dto.setIsVacant(entity.getIsVacant());
        if (entity.getResidents() != null) {
            dto.setResidents(entity.getResidents().stream()
                    .map(residentMapper::toDTO)
                    .toList());
        }
        /*
            if (entity.getRoomFee() != null) {
                dto.setRoomFeeId(entity.getRoomFee().getId());
                // optional – nếu sau này DTO có thêm field
                // dto.setPrice(entity.getRoomFee().getDefaultAmount());
                // dto.setArea(entity.getRoomFee().getArea());
            }
        */

        return dto;
    }

    /**
     * CreateDTO -> Entity
     */
    public Household toEntity(HouseholdCreateDTO dto) {
        Household entity = new Household();

        entity.setRoomNumber(dto.getRoomNumber());
        entity.setOwnerName(dto.getOwnerName());
        entity.setOwnerCccd(dto.getOwnerCccd());
        entity.setArea(dto.getArea());
        entity.setIsVacant(dto.getIsVacant());

        // mặc định khi tạo mới
        entity.setResidentCount(0);
        entity.setCarCount(0);
        entity.setBikeCount(0);
        return entity;
    }

    /**
     * Update Entity từ UpdateDTO
     */
    public void updateEntity(HouseholdUpdateDTO dto, Household entity) {

        if (dto.getOwnerName() != null) {
            entity.setOwnerName(dto.getOwnerName());
        }
        if (dto.getOwnerCccd() != null) {
            entity.setOwnerCccd(dto.getOwnerCccd());
        }

        if (dto.getIsVacant() != null) {
            entity.setIsVacant(dto.getIsVacant());
        }

        if (dto.getArea() != null) {
            entity.setArea(dto.getArea());
        }
    }
}
