package com.example.project.FeePayment.service;

import com.example.project.FeePayment.entity.FeePayment;
import com.example.project.FeePayment.mapper.FeePaymentMapper;
import com.example.project.FeePayment.dto.FeePaymentCreateDTO;
import com.example.project.FeePayment.dto.FeePaymentDTO;
import com.example.project.FeePayment.dto.FeePaymentSearchRequest;
import com.example.project.FeePayment.dto.FeePaymentUpdateDTO;
import com.example.project.FeePayment.repository.FeePaymentRepository;
import com.example.project.fee.entity.Fee;
import com.example.project.fee.repository.FeeRepository;
import com.example.project.household.entity.Household;
import com.example.project.household.repository.HouseholdRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

@Service
@RequiredArgsConstructor
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeeRepository feeRepository;
    private final HouseholdRepository householdRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final FeeCalculationService calculationService;
    private final FeePaymentMapper feePaymentMapper;

    //Trong FeePayment entity có 2 trường được đánh dấu not null là household và fee, nên thực hiện bắt lỗi và báo ra terminal khi 
    //hai trường này là null trong dto truyền vào để tạo FeePayment
    @Override
    public FeePaymentDTO create(FeePaymentCreateDTO dto) {
        FeePayment e = new FeePayment();
        Household household = householdRepository.findById(dto.getHouseholdId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hô dân có ID " + dto.getHouseholdId() + " không tồn tại"));
        Fee fee = feeRepository.findById(dto.getFeeId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Loại phí có ID " + dto.getFeeId() + " không tồn tại"));
        e.setHousehold(household);
        e.setFee(fee);
        e.setName(dto.getName());
        e.setUsageAmount(dto.getUsageAmount());
        e.setBillingYear(dto.getBillingYear());
        e.setBillingMonth(dto.getBillingMonth());
        e.setStartDate(dto.getStartDate());
        e.setDueDate(dto.getDueDate());
        e.setMandatory(dto.getMandatory());
        if (Boolean.TRUE.equals(dto.getMandatory())) {
            e.setPaid(false);
        } else {
            e.setPaid(true);
            e.setPaidDate(LocalDate.now());
        }
        BigDecimal usageAmount = dto.getUsageAmount();
        BigDecimal volutaryAmount = dto.getVoluntaryAmount();
        BigDecimal amount = calculationService.calculateFee(fee, household, usageAmount, volutaryAmount);
        e.setAmount(amount);
        return feePaymentMapper.toDTO(feePaymentRepository.save(e));
    }

    @Override
    public FeePaymentDTO update(Long id, FeePaymentUpdateDTO dto) {
        FeePayment e = feePaymentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Phiếu thu phí có ID " + id + " không tồn tại"));
        Household household = householdRepository.findById(dto.getHouseholdId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Hô dân có ID " + dto.getHouseholdId() + " không tồn tại"));
        Fee fee = feeRepository.findById(dto.getFeeId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Loại phí có ID " + dto.getFeeId() + " không tồn tại"));
        if (dto.getName() != null) {
            e.setName(dto.getName());
        }
        if (dto.getUsageAmount() != null) {
            e.setUsageAmount(dto.getUsageAmount());
        }
        if (dto.getBillingMonth() != null) {
            e.setBillingMonth(dto.getBillingMonth());
        }
        if (dto.getStartDate() != null) {
            e.setStartDate(dto.getStartDate());
        }
        if (dto.getDueDate() != null) {
            e.setDueDate(dto.getDueDate());
        }
        if (dto.getMandatory() != null) {
            e.setMandatory(dto.getMandatory());
        }
        if (dto.getPaid() != null) {
            e.setPaid(dto.getPaid());
            if (dto.getPaid()) {
                    if (dto.getPaidDate() == null) 
                        e.setPaidDate(LocalDate.now());
                    else e.setPaidDate(dto.getPaidDate());
            } else {
                e.setPaidDate(null);
            }
        }
        BigDecimal usageAmount = (dto.getUsageAmount() != null ? dto.getUsageAmount() : e.getUsageAmount());
        BigDecimal volutaryAmount = dto.getVoluntaryAmount();
        BigDecimal amount = calculationService.calculateFee(fee, household, usageAmount, volutaryAmount);
        e.setAmount(amount);
        return feePaymentMapper.toDTO(feePaymentRepository.save(e));
    }

    @Override
    public FeePaymentDTO findById(Long id) {
        FeePayment feePayment = feePaymentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Phiếu thu phí có ID " + id + " không tồn tại"));
        return feePaymentMapper.toDTO(feePayment);
    }

    @Override
    public List<FeePaymentDTO> findAll(){
        return feePaymentRepository.findAll().stream()
                .map(feePaymentMapper::toDTO)
                .toList();
    }
    @Override
    public void delete(Long id) {
        if (!feePaymentRepository.existsById(id)) {
            throw new ApiException(ErrorCode.NOT_FOUND, "Phiếu thu phí có ID " + id + " không tồn tại");
        }
        feePaymentRepository.deleteById(id);
    }

    @Override
    public List<FeePaymentDTO> search(FeePaymentSearchRequest req) {
        Specification<FeePayment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Lọc theo Household ID
            if (req.getHouseholdId() != null) {
                predicates.add(cb.equal(root.get("household").get("id"), req.getHouseholdId()));
            }

            // 2. Lọc theo Fee ID
            if (req.getFeeId() != null) {
                predicates.add(cb.equal(root.get("fee").get("id"), req.getFeeId()));
            }
            // 2. Lọc theo Năm thu (billingYear - kiểu Integer)
            if (req.getBillingYear() != null) {
                predicates.add(cb.equal(root.get("billingYear"), req.getBillingYear()));
            }

            // 3. Lọc theo Tháng thu (billingMonth - kiểu Integer)
            if (req.getBillingMonth() != null) {
                predicates.add(cb.equal(root.get("billingMonth"), req.getBillingMonth()));
            }

            // 4. Lọc theo khoảng ngày bắt đầu (startDate BETWEEN startFrom AND startTo)
            if (req.getStartFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), req.getStartFrom()));
            }
            if (req.getStartTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), req.getStartTo()));
            }

            // 5. Lọc theo khoảng hạn nộp (dueDate BETWEEN dueFrom AND dueTo)
            if (req.getDueFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dueDate"), req.getDueFrom()));
            }
            if (req.getDueTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("dueDate"), req.getDueTo()));
            }

            // 6. Lọc theo tính bắt buộc
            if (req.getMandatory() != null) {
                predicates.add(cb.equal(root.get("mandatory"), req.getMandatory()));
            }

            // 7. Lọc theo trạng thái đã nộp hay chưa
            if (req.getPaid() != null) {
                predicates.add(cb.equal(root.get("paid"), req.getPaid()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // Thực hiện truy vấn dưới Database
        return feePaymentRepository.findAll(spec).stream()
                .map(feePaymentMapper::toDTO)
                .toList();
    }
}

