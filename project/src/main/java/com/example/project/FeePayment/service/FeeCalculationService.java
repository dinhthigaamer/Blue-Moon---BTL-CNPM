package com.example.project.FeePayment.service;

import com.example.project.fee.entity.Fee;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class FeeCalculationService {

    public BigDecimal calculateFee(Fee fee, BigDecimal usageAmount, BigDecimal voluntaryAmount) {

        return switch (fee.getType()) {

            case MANAGEMENT_FEE, SERVICE_FEE, GUI_XE_MAY, GUI_XE_O_TO, ELECTRICITY, WATER ->
                fee.getPricePerUnit()
                        .multiply(usageAmount);

            case OPTIONAL ->
                voluntaryAmount;

        };
    }
}
