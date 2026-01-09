package com.example.project.FeePayment.repository;

import com.example.project.FeePayment.entity.FeePayment;
import com.example.project.fee.entity.Fee;
import com.example.project.household.entity.Household;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long>, JpaSpecificationExecutor<FeePayment> {
    boolean existsByHouseholdAndFeeAndBillingYearAndBillingMonth(Household household, Fee fee, Integer billingYear,
            Integer billingMonth);

    List<FeePayment> findByHouseholdAndBillingMonthAndBillingYear(Household household, Integer billingMonth,
            Integer billingYear);
}
