//Temporarily not in used
package com.example.project.fee.dto;
import java.math.BigDecimal;
import com.example.project.fee.entity.FeeType;  


public class FeeCreateDTO {
    private String name;
    private FeeType type;
    private BigDecimal defaultAmount;
    private Double area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeeType getType() {
        return type;
    }

    public void setType(FeeType type) {
        this.type = type;
    }

    public BigDecimal getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(BigDecimal defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

}
