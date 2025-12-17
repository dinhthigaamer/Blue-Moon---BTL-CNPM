package com.example.project.security;

import com.example.project.auth.entity.User;
import com.example.project.auth.repository.AuthRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    public CustomUserDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(ErrorCode.AUTH_USER_NOT_FOUND, "User not found"));
        return new CustomUserDetails(user);
    }
}
