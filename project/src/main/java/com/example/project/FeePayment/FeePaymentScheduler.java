package com.example.project.FeePayment;

import com.example.project.FeePayment.entity.FeePayment;
import com.example.project.FeePayment.repository.FeePaymentRepository;
import com.example.project.FeePayment.service.FeeCalculationService;
import com.example.project.fee.entity.Fee;
import com.example.project.fee.entity.FeeType;
import com.example.project.fee.repository.FeeRepository;
import com.example.project.household.entity.Household;
import com.example.project.household.repository.HouseholdRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class FeePaymentScheduler {

    private final HouseholdRepository householdRepository;
    private final FeeRepository feeRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final FeeCalculationService calculationService;

    public FeePaymentScheduler(HouseholdRepository householdRepository, FeeRepository feeRepository,
            FeePaymentRepository feePaymentRepository, FeeCalculationService calculationService) {
        this.householdRepository = householdRepository;
        this.feeRepository = feeRepository;
        this.feePaymentRepository = feePaymentRepository;
        this.calculationService = calculationService;
    }

    @Scheduled(cron = "0 0 0 1 * ?") // Chạy vào lúc 00:00 ngày đầu tiên của mỗi tháng
    // @Scheduled(cron = "0 0/1 * * * ?") // Chạy mỗi phút để test
    public void createMonthlyFeePayments() {
        System.out.println("Scheduler started at: " + LocalDate.now() + " " + java.time.LocalTime.now());
        List<Household> households = householdRepository.findAll();
        System.out.println("Found " + households.size() + " households");
        List<FeeType> fixedFeeTypes = Arrays.asList(FeeType.MANAGEMENT_FEE, FeeType.SERVICE_FEE, FeeType.GUI_XE_MAY,
                FeeType.GUI_XE_O_TO);
        List<Fee> fixedFees = feeRepository.findAll().stream()
                .filter(fee -> fixedFeeTypes.contains(fee.getType()))
                .toList();
        System.out.println("Found " + fixedFees.size() + " fixed fees");

        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();
        System.out.println("Current month/year: " + currentMonth + "/" + currentYear);

        for (Household household : households) {
            for (Fee fee : fixedFees) {
                // Kiểm tra nếu khoản phí đã tồn tại tháng này
                boolean exists = feePaymentRepository.existsByHouseholdAndFeeAndBillingYearAndBillingMonth(household,
                        fee, currentYear, currentMonth);
                if (!exists) {
                    System.out.println("Creating FeePayment for household " + household.getRoomNumber() + " and fee "
                            + fee.getName());
                    FeePayment payment = new FeePayment();
                    payment.setHousehold(household);
                    payment.setFee(fee);
                    payment.setName(fee.getName());

                    // Tính usageAmount dựa trên FeeType
                    BigDecimal usageAmount;
                    FeeType feeType = fee.getType();
                    if (feeType == FeeType.MANAGEMENT_FEE || feeType == FeeType.SERVICE_FEE) {
                        usageAmount = BigDecimal.valueOf(household.getArea());
                    } else if (feeType == FeeType.GUI_XE_MAY) {
                        usageAmount = BigDecimal.valueOf(household.getCarCount());
                    } else if (feeType == FeeType.GUI_XE_O_TO) {
                        usageAmount = BigDecimal.valueOf(household.getBikeCount());
                    } else {
                        usageAmount = BigDecimal.ONE; // Fallback
                    }
                    if (usageAmount.compareTo(BigDecimal.ZERO) <= 0) {
                        continue;
                    }
                    payment.setUsageAmount(usageAmount);
                    payment.setBillingYear(currentYear);
                    payment.setBillingMonth(currentMonth);
                    payment.setStartDate(now.withDayOfMonth(1));
                    payment.setDueDate(now.withDayOfMonth(now.lengthOfMonth()));
                    payment.setMandatory(true);
                    payment.setPaid(false);

                    BigDecimal amount = calculationService.calculateFee(fee, usageAmount, null);
                    payment.setAmount(amount);

                    if (amount.compareTo(BigDecimal.ZERO) > 0) {
                        feePaymentRepository.save(payment);
                        System.out.println("Saved FeePayment with amount: " + amount);
                    } else {
                        System.out.println("Amount is zero or negative for household " + household.getRoomNumber()
                                + " and fee " + fee.getName() + ", skipping save");
                    }
                }
            }
        }
        System.out.println("Scheduler finished");
    }
}
