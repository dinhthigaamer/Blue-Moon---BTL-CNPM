package com.example.project.household.repository;

import com.example.project.household.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, Long>, JpaSpecificationExecutor<Household> {

    Optional<Household> findByRoomNumber(String roomNumber);

    long countByIsVacantFalse();
}
