package com.example.project.fee.mapper;

import com.example.project.fee.dto.FeeCreateDTO;
import com.example.project.fee.dto.FeeDTO;
import com.example.project.fee.entity.Fee;
import org.springframework.stereotype.Component;

@Component
public class FeeMapper {

    public FeeDTO toDTO(Fee entity) {
        FeeDTO dto = new FeeDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setDefaultAmount(entity.getDefaultAmount());
        dto.setPricePerUnit(entity.getPricePerUnit());
        dto.setNote(entity.getNote());
        return dto;
    }

    public Fee toEntity(FeeCreateDTO dto) {
        Fee fee = new Fee();
        fee.setName(dto.getName());
        fee.setType(dto.getType());
        fee.setDefaultAmount(dto.getDefaultAmount());
        fee.setPricePerUnit(dto.getPricePerUnit());
        fee.setNote(dto.getNote());
        return fee;
    }
    // thay bằng hàm mới chuyển từ FeeDTO sang Fee
    // public Fee toEntity(Fee dto) {
    // Fee fee = new Fee();
    // fee.setName(dto.getName());
    // fee.setType(dto.getType());
    // fee.setDefaultAmount(dto.getDefaultAmount());
    // fee.setArea(dto.getArea());
    // return fee;
    // }
}
