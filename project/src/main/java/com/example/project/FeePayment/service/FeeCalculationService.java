package com.example.project.FeePayment.service;

import com.example.project.fee.entity.Fee;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;


@Service
public class FeeCalculationService {

    public BigDecimal calculateFee(Fee fee, BigDecimal usageAmount, BigDecimal voluntaryAmount) {

        return switch (fee.getType()) {

            case MANAGEMENT_FEE ->
                fee.getDefaultAmount();

            case SERVICE_FEE ->
                fee.getPricePerUnit()
                   .multiply(usageAmount);

            case PARKING ->
                fee.getPricePerUnit()
                   .multiply(usageAmount);

            case ELECTRICITY, WATER ->
                fee.getPricePerUnit()
                   .multiply(usageAmount);

            case OPTIONAL ->
                voluntaryAmount;

        };
    }
}

