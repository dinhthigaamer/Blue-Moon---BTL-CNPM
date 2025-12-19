package com.example.project.resident.dto;

import lombok.Data;

@Data
public class ResidentUpdateDTO {
    private String fullName;
    private String phone;
    private Integer vehicleCount;
    private Long householdId; // optional: đổi hộ khẩu (chuyển phòng)

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Integer vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }


}
