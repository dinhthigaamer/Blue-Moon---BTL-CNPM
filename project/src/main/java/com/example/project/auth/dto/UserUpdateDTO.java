package com.example.project.auth.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String fullName;
    private String email;
    private String phone;
    private String cccd;
    private String oldPassword;
    private String newPassword;
}
