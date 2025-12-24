package com.example.project.household.repository;

import com.example.project.household.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, Long> {

    Optional<Household> findByRoomNumber(Integer roomNumber);
}
