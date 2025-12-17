package com.example.project.household.mapper;

import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;
import com.example.project.household.entity.Household;
/* 
import com.example.project.fee.entity.Fee;
import com.example.project.fee.entity.FeeType;
import com.example.project.fee.repository.FeeRepository;
*/
import org.springframework.stereotype.Component;

@Component
public class HouseholdMapper {

    private final FeeRepository feeRepository;

    public HouseholdMapper(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
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
        dto.setResidentCount(entity.getResidentCount());
        dto.setVehicleCount(entity.getVehicleCount());
        dto.setIsVacant(entity.getIsVacant());

        if (entity.getRoomFee() != null) {
            dto.setRoomFeeId(entity.getRoomFee().getId());
            // optional – nếu sau này DTO có thêm field
            // dto.setPrice(entity.getRoomFee().getDefaultAmount());
            // dto.setArea(entity.getRoomFee().getArea());
            // dto.setRoomFeeName(entity.getRoomFee().getName());
        }

        return dto;
    }

    /**
     * CreateDTO -> Entity
     */
    public Household toEntity(HouseholdCreateDTO dto) {
        Household entity = new Household();

        entity.setRoomNumber(dto.getRoomNumber());
        entity.setOwnerName(dto.getOwnerName());
        entity.setIsVacant(dto.getIsVacant());

        // mặc định khi tạo mới
        entity.setResidentCount(0);
        entity.setVehicleCount(0);

        Fee fee = feeRepository.findById(dto.getRoomFeeId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        if (fee.getType() != FeeType.RENT) {
            throw new ApiException(ErrorCode.INVALID_FEE_TYPE);
        }

        entity.setRoomFee(fee);

        return entity;
    }

    /**
     * Update Entity từ UpdateDTO
     */
    public void updateEntity(HouseholdUpdateDTO dto, Household entity) {

        if (dto.getOwnerName() != null) {
            entity.setOwnerName(dto.getOwnerName());
        }

        if (dto.getIsVacant() != null) {
            entity.setIsVacant(dto.getIsVacant());
        }

        if (dto.getRoomFeeId() != null) {
            Fee fee = feeRepository.findById(dto.getRoomFeeId())
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

            if (fee.getType() != FeeType.RENT) {
                throw new ApiException(ErrorCode.INVALID_FEE_TYPE);
            }

            entity.setRoomFee(fee);
        }
    }
}
