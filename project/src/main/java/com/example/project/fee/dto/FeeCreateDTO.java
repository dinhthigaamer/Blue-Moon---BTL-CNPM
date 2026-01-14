//Temporarily not in used
package com.example.project.fee.dto;

import java.math.BigDecimal;
import com.example.project.fee.entity.FeeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeeCreateDTO {
    @NotBlank(message = "Tên phí không được để trống")
    private String name;
    @NotNull(message = "Loại phí không được để trống")
    private FeeType type;
    private BigDecimal defaultAmount;
    private BigDecimal pricePerUnit;
    private String note;

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

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
