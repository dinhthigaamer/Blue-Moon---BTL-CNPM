package com.example.project.statistics.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.fee.entity.Fee;
import com.example.project.FeePayment.dto.FeePaymentDTO;
import com.example.project.FeePayment.dto.FeePaymentSearchRequest;
import com.example.project.FeePayment.service.FeePaymentService;
import com.example.project.fee.repository.FeeRepository;
import com.example.project.resident.repository.ResidentRepository;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryOutDTO;
import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueOutDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryOutDTO;
import com.example.project.statistics.dto.ResidentAndHouseholdCountDTO;
import com.example.project.household.repository.HouseholdRepository;

@Service

public class StatisticServiceImpl implements StatisticServer {
    private final FeePaymentService feepaymentService;
    private final FeeRepository feeRepository;
    private final ResidentRepository residentRepository;
    private final HouseholdRepository householdRepository;

    public StatisticServiceImpl(FeePaymentService feepaymentService, FeeRepository feeRepository,
            ResidentRepository residentRepository, HouseholdRepository householdRepository) {
        this.feepaymentService = feepaymentService;
        this.feeRepository = feeRepository;
        this.residentRepository = residentRepository;
        this.householdRepository = householdRepository;
    }

    public BigDecimal sumField(List<FeePaymentDTO> payments, Function<FeePaymentDTO, BigDecimal> mapper) {
        return payments.stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public FeeSummaryOutDTO statisticFeeSummary(Long feeId, Integer year, Integer month) {
        // lấy và tính toán các trường cần thiết để trả về
        FeePaymentSearchRequest searchRequest = new FeePaymentSearchRequest();
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(
                        () -> new ApiException(ErrorCode.NOT_FOUND, "Phiếu thu phí có ID " + feeId + " không tồn tại"));
        String name = fee.getName();
        searchRequest.setFeeId(feeId);
        searchRequest.setBillingYear(year);
        searchRequest.setBillingMonth(month);
        searchRequest.setPaid(true);
        List<FeePaymentDTO> payments = feepaymentService.search(searchRequest);
        BigDecimal total = sumField(payments, FeePaymentDTO::getAmount);

        // thiết lập dto trả về
        FeeSummaryOutDTO result = new FeeSummaryOutDTO();
        result.setTotal(total);
        result.setFee_id(feeId);
        result.setName(name);
        result.setYear(year);
        result.setMonth(month);
        return result;
    }

    public MonthlyRevenueOutDTO statisticMonthlyRevenue(Integer year, Integer month) {
        // lấy và tính toán các trường cần thiết cho dto trả về
        FeePaymentSearchRequest searchRequest = new FeePaymentSearchRequest();
        searchRequest.setBillingYear(year);
        searchRequest.setBillingMonth(month);
        searchRequest.setPaid(true);
        List<FeePaymentDTO> mandatoryPayments = feepaymentService.search(searchRequest);
        BigDecimal total = sumField(mandatoryPayments, FeePaymentDTO::getAmount);
        // thiết lập dto trả về
        MonthlyRevenueOutDTO result = new MonthlyRevenueOutDTO();
        result.setTotal(total);
        result.setYear(year);
        result.setMonth(month);
        return result;
    }

    public VoluntarySummaryOutDTO statisticVoluntary(Integer year, Integer month) {
        // lấy và tính toán các trường cần thiết cho dto trả về
        FeePaymentSearchRequest searchRequest = new FeePaymentSearchRequest();
        searchRequest.setBillingYear(year);
        searchRequest.setBillingMonth(month);
        searchRequest.setMandatory(false);
        searchRequest.setPaid(true);
        List<FeePaymentDTO> mandatoryPayments = feepaymentService.search(searchRequest);
        BigDecimal total = sumField(mandatoryPayments, FeePaymentDTO::getAmount);
        // thiết lập dto trả về
        VoluntarySummaryOutDTO result = new VoluntarySummaryOutDTO();
        result.setTotal(total);
        result.setYear(year);
        result.setMonth(month);
        return result;
    }

    public ResidentAndHouseholdCountDTO countResidents() {
        Long residentCount = residentRepository.count();
        Long householdCount = householdRepository.countByIsVacantFalse();
        return new ResidentAndHouseholdCountDTO(residentCount, householdCount);
    }

}
