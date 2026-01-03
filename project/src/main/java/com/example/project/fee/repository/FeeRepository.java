package com.example.project.fee.repository;
import com.example.project.fee.entity.Fee;
import com.example.project.fee.entity.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    List<Fee> findByType(FeeType type);
}
