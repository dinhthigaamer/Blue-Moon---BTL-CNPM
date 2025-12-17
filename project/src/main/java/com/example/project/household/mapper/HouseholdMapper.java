package com.example.project.household.mapper;

import org.springframework.stereotype.Component;
import com.example.project.household.entity.Household;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;

@Component
public class HouseholdMapper {

    private final FeeRepository feeRepository;

    public HouseholdMapper(FeeRepository feeRepository) {
        this.feeRepository = feeRepository;
    }

    public HouseholdDTO toDTO(Household e) {
        HouseholdDTO dto = new HouseholdDTO();
        dto.setId(e.getId());
        dto.setRoomNumber(e.getRoomNumber());
        dto.setOwnerName(e.getOwnerName());
        dto.setResidentCount(e.getResidentCount());
        dto.setVehicleCount(e.getVehicleCount());
        dto.setIsVacant(e.getIsVacant());

        if (e.getRoomFee() != null) {
            dto.setRoomFeeId(e.getRoomFee().getId());
        }
        return dto;
    }

    public Household toEntity(HouseholdCreateDTO dto) {
        Household e = new Household();
        e.setRoomNumber(dto.getRoomNumber());
        e.setOwnerName(dto.getOwnerName());
        e.setIsVacant(dto.getIsVacant());
        e.setResidentCount(0);
        e.setVehicleCount(0);

        Fee fee = feeRepository.findById(dto.getRoomFeeId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

        if (fee.getType() != FeeType.RENT) {
            throw new ApiException(ErrorCode.INVALID_REQUEST);
        }

        e.setRoomFee(fee);
        return e;
    }

    public void updateEntity(HouseholdUpdateDTO dto, Household e) {
        if (dto.getOwnerName() != null) {
            e.setOwnerName(dto.getOwnerName());
        }
        if (dto.getIsVacant() != null) {
            e.setIsVacant(dto.getIsVacant());
        }
        if (dto.getRoomFeeId() != null) {
            Fee fee = feeRepository.findById(dto.getRoomFeeId())
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
            if (fee.getType() != FeeType.RENT) {
                throw new ApiException(ErrorCode.INVALID_REQUEST);
            }
            e.setRoomFee(fee);
        }
    }
}
