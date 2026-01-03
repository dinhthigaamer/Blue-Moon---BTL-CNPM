package com.example.project.auth.service;

import com.example.project.auth.dto.AuthResponse;
import com.example.project.auth.dto.LoginRequestDTO;
import com.example.project.auth.dto.RegisterRequestDTO;
import com.example.project.auth.dto.UserDTO;
import com.example.project.auth.dto.UserUpdateDTO;

public interface AuthService {

    AuthResponse login(LoginRequestDTO request);

    UserDTO register(RegisterRequestDTO request);

    UserDTO getCurrentUser();
    UserDTO updateCurrentUser(UserUpdateDTO request);

    void requestForgotPasswordOtp(String phone);
    void confirmForgotPassword(String phone, String otp, String newPassword);

}
