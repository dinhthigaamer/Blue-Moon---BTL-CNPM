package com.example.project.resident.repository;

import com.example.project.resident.entity.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ResidentRepository extends JpaRepository<Resident, Long>, JpaSpecificationExecutor<Resident> {
    List<Resident> findByHouseholdId(Long householdId);

    List<Resident> findByFullNameContainingIgnoreCase(String fullName);

    List<Resident> findByHouseholdIdAndFullNameContainingIgnoreCase(Long householdId, String fullName);

    Optional<Resident> findByCccd(String cccd);
}
