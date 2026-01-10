package com.example.project.auth.controller;

import com.example.project.auth.dto.AuthResponse;
import com.example.project.auth.dto.LoginRequestDTO;
import com.example.project.auth.dto.RegisterRequestDTO;
import com.example.project.auth.dto.UserDTO;
import com.example.project.auth.service.AuthService;
import com.example.project.common.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import com.example.project.auth.dto.UserUpdateDTO;
import com.example.project.auth.dto.ForgotPasswordRequestOTPDTO;
import com.example.project.auth.dto.ForgotPasswordConfirmDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody RegisterRequestDTO request) {
        return ApiResponse.ok(authService.register(request), "Đăng ký thành công");
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequestDTO request) {
        return ApiResponse.ok(authService.login(request), "Đăng nhập thành công");
    }

    @GetMapping("/me")
    public ApiResponse<UserDTO> getCurrentUser() {
        return ApiResponse.ok(authService.getCurrentUser());
    }

    @PutMapping("/me")
    public ApiResponse<UserDTO> updateMe(@RequestBody UserUpdateDTO request) {
        return ApiResponse.ok(authService.updateCurrentUser(request), "Cập nhật thành công");
    }

    @PostMapping("/forgot-password/request-otp")
    public ApiResponse<Object> requestOtp(@RequestBody ForgotPasswordRequestOTPDTO req) {
        authService.requestForgotPasswordOtp(req.getEmail());
        return ApiResponse.ok(null, "OTP đã gửi đến email");
    }

    @PostMapping("/forgot-password/confirm")
    public ApiResponse<Object> confirm(@RequestBody ForgotPasswordConfirmDTO req) {
        authService.confirmForgotPassword(
                req.getEmail(),
                req.getOtp(),
                req.getNewPassword()
        );
        return ApiResponse.ok(null, "Đặt lại mật khẩu thành công");
    }

}
