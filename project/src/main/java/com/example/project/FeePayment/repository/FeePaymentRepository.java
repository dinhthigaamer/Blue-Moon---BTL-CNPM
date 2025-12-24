package com.example.project.FeePayment.repository;

import com.example.project.FeePayment.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long>, JpaSpecificationExecutor<FeePayment> {
}
