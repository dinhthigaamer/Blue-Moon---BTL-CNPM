package com.example.project.resident.dto;

import com.example.project.resident.entity.ResidenceStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import java.time.LocalDate;


public class ResidentCreateDTO {
    @NotBlank(message = "Họ tên là bắt buộc")
    private String fullName;
    private String phone;
    @Email(message = "Email không hợp lệ")
    private String email;
    @Pattern(regexp = "\\d{12}", message = "CCCD phải gồm đúng 12 chữ số")
    private String cccd;
    private LocalDate dateOfBirth;
    private String religion;
    private String ethnicity;
    private String occupation;
    private ResidenceStatus residenceStatus;
    private Integer carCount;
    private Integer bikeCount;
    @NotNull(message = "HouseholdId là bắt buộc")
    private Long householdId; // bắt buộc

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public ResidenceStatus getResidenceStatus() {
        return residenceStatus;
    }

    public void setResidenceStatus(ResidenceStatus residenceStatus) {
        this.residenceStatus = residenceStatus;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public Integer getBikeCount() {
        return bikeCount;
    }

    public void setBikeCount(Integer bikeCount) {
        this.bikeCount = bikeCount;
    }

    public Long getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

}
