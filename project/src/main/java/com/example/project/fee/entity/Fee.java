package com.example.project.fee.entity;

import java.math.BigDecimal;
import com.example.project.common.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "fees")

@EqualsAndHashCode(callSuper = true)
public class Fee extends BaseEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private FeeType type;

    //dành cho các khoản cố định
    private BigDecimal defaultAmount;

    //dùng cho khoản không cố định theo đơn vị sử dụng
    private BigDecimal pricePerUnit;
    
    //chú thích
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


