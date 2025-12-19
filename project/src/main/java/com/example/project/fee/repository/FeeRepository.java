package com.example.project.fee.repository;
import com.example.project.fee.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    Optional<Fee> findByType(String type);
}
