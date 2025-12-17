package com.example.project.auth.service;

import com.example.project.auth.dto.AuthResponse;
import com.example.project.auth.dto.LoginRequestDTO;
import com.example.project.auth.dto.RegisterRequestDTO;
import com.example.project.auth.dto.UserDTO;

public interface AuthService {

    AuthResponse login(LoginRequestDTO request);

    UserDTO register(RegisterRequestDTO request);

    UserDTO getCurrentUser();
}
