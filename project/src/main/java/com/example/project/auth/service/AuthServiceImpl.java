package com.example.project.auth.service;

import com.example.project.auth.dto.AuthResponse;
import com.example.project.auth.dto.LoginRequestDTO;
import com.example.project.auth.dto.RegisterRequestDTO;
import com.example.project.auth.dto.UserDTO;
import com.example.project.auth.entity.User;
import com.example.project.auth.mapper.UserMapper;
import com.example.project.auth.repository.AuthRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthServiceImpl(AuthRepository authRepository,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider tokenProvider,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public AuthResponse login(LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        User user = authRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "User not found"));

        UserDTO userDTO = userMapper.toDTO(user);

        return new AuthResponse(token, userDTO);
    }

    @Override
    public UserDTO register(RegisterRequestDTO request) {

        if (authRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(ErrorCode.AUTH_USERNAME_EXISTED, "Username is already taken");
        }

        if (request.getPhone() != null && authRepository.existsByPhone(request.getPhone())) {
            throw new ApiException(ErrorCode.AUTH_PHONE_EXISTED, "Phone is already used");
        }

        if (request.getCccd() != null && authRepository.existsByCccd(request.getCccd())) {
            throw new ApiException(ErrorCode.AUTH_CCCD_EXISTED, "CCCD is already used");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);

        user = authRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "User not found"));
        return userMapper.toDTO(user);
    }
}
