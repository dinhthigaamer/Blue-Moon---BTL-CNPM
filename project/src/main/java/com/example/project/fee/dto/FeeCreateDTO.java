package com.example.project.fee.dto;
import java.math.BigDecimal;
import com.example.project.fee.entity.FeeType;  

import lombok.Data;

@Data
public class FeeCreateDTO {
    private String name;
    private FeeType type;
    private BigDecimal defaultAmount;
    private Double area;
}
