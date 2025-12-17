package com.example.project.auth.controller;

import com.example.project.auth.dto.AuthResponse;
import com.example.project.auth.dto.LoginRequestDTO;
import com.example.project.auth.dto.RegisterRequestDTO;
import com.example.project.auth.dto.UserDTO;
import com.example.project.auth.service.AuthService;
import com.example.project.common.response.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody RegisterRequestDTO request) {
        return ApiResponse.ok(authService.register(request), "Register success");
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequestDTO request) {
        return ApiResponse.ok(authService.login(request), "Login success");
    }

    @GetMapping("/me")
    public ApiResponse<UserDTO> getCurrentUser() {
        return ApiResponse.ok(authService.getCurrentUser());
    }
}
