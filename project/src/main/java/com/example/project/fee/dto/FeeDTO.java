package com.example.project.fee.dto;

import java.math.BigDecimal;
import com.example.project.fee.entity.FeeType;
import lombok.Data;

@Data
public class FeeDTO {
    //private Long id;
    private String name;
    private FeeType type;
    private BigDecimal defaultAmount;
    private BigDecimal pricePerUnit;
    private String note;
}
