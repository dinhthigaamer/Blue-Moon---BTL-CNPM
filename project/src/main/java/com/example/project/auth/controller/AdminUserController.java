package com.example.project.auth.controller;

import com.example.project.auth.dto.UserDTO;
import com.example.project.auth.entity.User;
import com.example.project.auth.mapper.UserMapper;
import com.example.project.auth.repository.AuthRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.common.response.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AuthRepository authRepository;
    private final UserMapper userMapper;

    public AdminUserController(AuthRepository authRepository, UserMapper userMapper) {
        this.authRepository = authRepository;
        this.userMapper = userMapper;
    }

    @GetMapping("/pending")
    public ApiResponse<List<UserDTO>> pending() {
        List<UserDTO> result = authRepository.findByEnabledFalse()
                .stream()
                .map(userMapper::toDTO)
                .toList();
        return ApiResponse.ok(result, "Người dùng đang chờ duyệt");
    }

    @PatchMapping("/{id}/approve")
    public ApiResponse<Object> approve(@PathVariable Long id) {
        User user = authRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "Người dùng không tồn tại"));

        // Nếu đã duyệt rồi thì thôi (idempotent)
        if (Boolean.TRUE.equals(user.getEnabled())) {
            return ApiResponse.ok(null, "Đã được duyệt");
        }

        user.setEnabled(true);
        authRepository.save(user);

        return ApiResponse.ok(null, "Đã duyệt");
    }

    @DeleteMapping("/{id}/reject")
    public ApiResponse<Object> reject(@PathVariable Long id) {
        User user = authRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "Người dùng không tồn tại"));

        if (Boolean.TRUE.equals(user.getEnabled())) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Không thể từ chối người dùng đã duyệt");
        }

        authRepository.delete(user);
        return ApiResponse.ok(null, "Đã từ chối và xóa");
    }
}
