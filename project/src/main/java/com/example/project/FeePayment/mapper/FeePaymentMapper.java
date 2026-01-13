package com.example.project.FeePayment.mapper;

import com.example.project.FeePayment.dto.FeePaymentDTO;
import com.example.project.FeePayment.entity.FeePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeePaymentMapper {

    // chuyển từ entity sang dto hiển thị ra ngoài
    public FeePaymentDTO toDTO(FeePayment e) {
        FeePaymentDTO dto = new FeePaymentDTO();
        dto.setId(e.getId());
        dto.setRoomNumber(e.getHousehold().getRoomNumber());
        dto.setFeeId(e.getFee().getId());
        dto.setName(e.getName());
        dto.setUsageAmount(e.getUsageAmount());
        dto.setAmount(e.getAmount());
        dto.setBillingYear(e.getBillingYear());
        dto.setBillingMonth(e.getBillingMonth());
        dto.setStartDate(e.getStartDate());
        dto.setDueDate(e.getDueDate());
        dto.setMandatory(e.getMandatory());
        dto.setPaid(e.getPaid());
        dto.setPaidDate(e.getPaidDate());
        return dto;
    }
}
