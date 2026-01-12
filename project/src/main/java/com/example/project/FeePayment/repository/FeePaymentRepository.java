package com.example.project.FeePayment.repository;

import com.example.project.FeePayment.entity.FeePayment;
import com.example.project.fee.entity.Fee;
import com.example.project.household.entity.Household;
import com.example.project.FeePayment.dto.OutstandingFeeDTO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long>, JpaSpecificationExecutor<FeePayment> {
        boolean existsByHouseholdAndFeeAndBillingYearAndBillingMonth(Household household, Fee fee, Integer billingYear,
                        Integer billingMonth);

        List<FeePayment> findByHouseholdAndBillingMonthAndBillingYear(Household household, Integer billingMonth,
                        Integer billingYear);

        @Query("SELECT new com.example.project.FeePayment.dto.OutstandingFeeDTO(h.roomNumber, SUM(fp.amount)) " +
                        "FROM FeePayment fp JOIN fp.household h " +
                        "WHERE fp.paid = false " +
                        "GROUP BY h.id, h.roomNumber")
        List<OutstandingFeeDTO> findOutstandingFeesByHousehold();
}
