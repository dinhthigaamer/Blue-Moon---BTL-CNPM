package com.example.project.auth.repository;

import com.example.project.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    boolean existsByCccd(String cccd);

    boolean existsByEmail(String email);

    List<User> findByEnabledFalse();
}
