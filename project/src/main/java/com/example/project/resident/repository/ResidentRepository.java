package com.example.project.resident.repository;

import com.example.project.resident.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByHouseholdId(Long householdId);
    List<Resident> findByFullNameContainingIgnoreCase(String fullName);
}
