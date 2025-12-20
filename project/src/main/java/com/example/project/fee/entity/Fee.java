package com.example.project.fee.entity;

import java.math.BigDecimal;
import com.example.project.common.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fees")
@Data
public class Fee extends BaseEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private FeeType type;

    //dành cho các khoản cố định
    private BigDecimal defaultAmount;

    //dùng cho khoản không cố định theo đơn vị sử dụng
    private BigDecimal pricePerUnit;
    

}


