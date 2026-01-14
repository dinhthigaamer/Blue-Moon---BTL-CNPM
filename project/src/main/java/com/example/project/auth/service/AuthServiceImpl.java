package com.example.project.auth.service;

import com.example.project.auth.dto.*;
import com.example.project.auth.entity.PasswordResetOtp;
import com.example.project.auth.entity.User;
import com.example.project.auth.mapper.UserMapper;
import com.example.project.auth.repository.AuthRepository;
import com.example.project.auth.repository.PasswordResetOtpRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.integration.mail.GmailOtpMailService;
import com.example.project.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final PasswordResetOtpRepository passwordResetOtpRepository;
    private final GmailOtpMailService gmailOtpMailService;

    public AuthServiceImpl(AuthRepository authRepository,
                           AuthenticationManager authenticationManager,
                           JwtTokenProvider tokenProvider,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper,
                           PasswordResetOtpRepository passwordResetOtpRepository,
                           GmailOtpMailService gmailOtpMailService) {
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.passwordResetOtpRepository = passwordResetOtpRepository;
        this.gmailOtpMailService = gmailOtpMailService;
    }

    @Override
    public AuthResponse login(LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);

            User user = authRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "Người dùng không tồn tại"));

            UserDTO userDTO = userMapper.toDTO(user);
            return new AuthResponse(token, userDTO);

        } catch (DisabledException e) {
            throw new ApiException(ErrorCode.ACCOUNT_NOT_APPROVED, "Tài khoản đang chờ admin duyệt");
        } catch (AuthenticationException e) {
            throw new ApiException(ErrorCode.AUTH_INVALID_CREDENTIALS, "Sai tài khoản hoặc mật khẩu");
        }
    }


    @Override
    public UserDTO register(RegisterRequestDTO request) {

        if (authRepository.existsByUsername(request.getUsername())) {
            throw new ApiException(ErrorCode.AUTH_USERNAME_EXISTED, "Tên đăng nhập đã được sử dụng");
        }

        if (authRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorCode.AUTH_EMAIL_EXISTED, "Email đã được sử dụng");
        }

        if (request.getPhone() != null && authRepository.existsByPhone(request.getPhone())) {
            throw new ApiException(ErrorCode.AUTH_PHONE_EXISTED, "Số điện thoại đã được sử dụng");
        }

        if (request.getCccd() != null && authRepository.existsByCccd(request.getCccd())) {
            throw new ApiException(ErrorCode.AUTH_CCCD_EXISTED, "CCCD đã được sử dụng");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);

        user = authRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "Người dùng không tồn tại"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateCurrentUser(UserUpdateDTO request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "Người dùng không tồn tại"));

        if (request.getPhone() != null && !request.getPhone().equals(user.getPhone())) {
            if (authRepository.existsByPhone(request.getPhone())) {
                throw new ApiException(ErrorCode.AUTH_PHONE_EXISTED, "Số điện thoại đã được sử dụng");
            }
            user.setPhone(request.getPhone());
        }

        if (request.getCccd() != null && !request.getCccd().equals(user.getCccd())) {
            if (authRepository.existsByCccd(request.getCccd())) {
                throw new ApiException(ErrorCode.AUTH_CCCD_EXISTED, "CCCD đã được sử dụng");
            }
            user.setCccd(request.getCccd());
        }

        if (request.getFullName() != null) user.setFullName(request.getFullName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());

        boolean hasOldPassword = request.getOldPassword() != null && !request.getOldPassword().isBlank();
        boolean hasNewPassword = request.getNewPassword() != null && !request.getNewPassword().isBlank();

        if (hasOldPassword || hasNewPassword) {

            if (!hasOldPassword || !hasNewPassword) {
                throw new ApiException(ErrorCode.BAD_REQUEST, "Phải cung cấp cả mật khẩu cũ và mật khẩu mới");
            }

            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new ApiException(ErrorCode.AUTH_INVALID_PASSWORD, "Mật khẩu cũ không đúng");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        User saved = authRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public void requestForgotPasswordOtp(String email) {

        if (email == null || email.isBlank()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Email là bắt buộc");
        }

        User user = authRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "User not found"));

        String otp = String.valueOf((int)(Math.random() * 900000) + 100000);
        int expireMinutes = 5;

        PasswordResetOtp entity = new PasswordResetOtp();
        entity.setEmail(email);
        entity.setOtp(otp);
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(expireMinutes));
        entity.setUsed(false);

        passwordResetOtpRepository.save(entity);

        gmailOtpMailService.sendOtpEmail(email, otp, expireMinutes);
    }


    @Override
    public void confirmForgotPassword(String email, String otp, String newPassword) {

        if (email == null || email.isBlank()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Email là bắt buộc");
        }
        if (otp == null || otp.isBlank()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "OTP là bắt buộc");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Mật khẩu mới là bắt buộc");
        }

        PasswordResetOtp last =
                passwordResetOtpRepository.findTopByEmailOrderByIdDesc(email)
                        .orElseThrow(() ->
                                new ApiException(ErrorCode.AUTH_OTP_NOT_FOUND, "OTP không tìm thấy"));

        if (last.isUsed()) {
            throw new ApiException(ErrorCode.AUTH_OTP_INVALID, "OTP đã được sử dụng");
        }

        if (LocalDateTime.now().isAfter(last.getExpiresAt())) {
            throw new ApiException(ErrorCode.AUTH_OTP_EXPIRED, "OTP đã hết hạn");
        }

        if (!last.getOtp().equals(otp)) {
            throw new ApiException(ErrorCode.AUTH_OTP_INVALID, "OTP không hợp lệ");
        }

        User user = authRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "Người dùng không tồn tại"));

        user.setPassword(passwordEncoder.encode(newPassword));
        authRepository.save(user);

        last.setUsed(true);
        passwordResetOtpRepository.save(last);

        System.out.println("IMPT");
    }

}
