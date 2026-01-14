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
import com.example.project.fee.entity.FeeType;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import com.example.project.integration.mail.ReceiptService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.example.project.FeePayment.dto.OutstandingFeeDTO;

@Service
public class FeePaymentServiceImpl implements FeePaymentService {

    private final FeeRepository feeRepository;
    private final HouseholdRepository householdRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final FeeCalculationService calculationService;
    private final FeePaymentMapper feePaymentMapper;
    private final ReceiptService receiptService;

    public FeePaymentServiceImpl(FeeRepository feeRepository, HouseholdRepository householdRepository,
            FeePaymentRepository feePaymentRepository, FeeCalculationService calculationService,
            FeePaymentMapper feePaymentMapper, ReceiptService receiptService) {
        this.feeRepository = feeRepository;
        this.householdRepository = householdRepository;
        this.feePaymentRepository = feePaymentRepository;
        this.calculationService = calculationService;
        this.feePaymentMapper = feePaymentMapper;
        this.receiptService = receiptService;
    }

    // Trong FeePayment entity có 2 trường được đánh dấu not null là household và
    // fee
    @Override
    public FeePaymentDTO create(FeePaymentCreateDTO dto) {
        FeePayment e = new FeePayment();
        Household household = householdRepository.findByRoomNumber(dto.getRoomNumber())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND,
                        "Hô dân có phòng số " + dto.getRoomNumber() + " không tồn tại."));
        Fee fee = feeRepository.findById(dto.getFeeId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND,
                        "Loại phí có ID " + dto.getFeeId() + " không tồn tại."));

        // Kiểm tra xem feePayment cho loại phí này đã có trong tháng và năm này chưa
        if (feePaymentRepository.existsByHouseholdAndFeeAndBillingYearAndBillingMonth(household, fee,
                dto.getBillingYear(), dto.getBillingMonth())) {
            throw new ApiException(ErrorCode.BAD_REQUEST,
                    "Khoản thu phí cho hộ dân này với loại phí, năm và tháng đã tồn tại.");
        }
        e.setHousehold(household);
        e.setFee(fee);
        e.setName(fee.getName());
        // tính usageAmount dựa trên FeeType
        BigDecimal usageAmount;
        FeeType feeType = fee.getType();
        if (feeType == FeeType.MANAGEMENT_FEE || feeType == FeeType.SERVICE_FEE) {
            usageAmount = BigDecimal.valueOf(household.getArea());
        } else if (feeType == FeeType.GUI_XE_MAY) {
            usageAmount = BigDecimal.valueOf(household.getBikeCount());
        } else if (feeType == FeeType.GUI_XE_O_TO) {
            usageAmount = BigDecimal.valueOf(household.getCarCount());
        } else if (feeType == FeeType.OPTIONAL) {
            usageAmount = null; // loại tự nguyện không có usageAmount
        } else {
            usageAmount = dto.getUsageAmount(); // loại khác thì dung usageAmount từ dto
        }
        e.setUsageAmount(usageAmount);
        if (e.getFee().getType() != FeeType.OPTIONAL) {
            if (e.getUsageAmount() == null) {
                throw new ApiException(ErrorCode.BAD_REQUEST,
                        "Phải có trường mức sử dụng cho loại phí không phải tự nguyện!");
            }
        }
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
        BigDecimal voluntaryAmount = dto.getVoluntaryAmount();
        if (e.getMandatory() == true) {
            if (voluntaryAmount != null) {
                throw new ApiException(ErrorCode.BAD_REQUEST,
                        "Không điền vào trường số tiền đóng góp nếu không phải loại tự nguyện!");
            }
        }
        if (e.getMandatory() == false) {
            if (voluntaryAmount == null || voluntaryAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ApiException(ErrorCode.BAD_REQUEST,
                        "Khoản thu phí đóng góp tự nguyện phải lớn hơn 0!");
            }
        }
        BigDecimal amount = calculationService.calculateFee(fee, usageAmount, voluntaryAmount);
        e.setAmount(amount);
        FeePayment saved = feePaymentRepository.save(e);
        return feePaymentMapper.toDTO(saved);
    }

    @Override
    public FeePaymentDTO update(Long id, FeePaymentUpdateDTO dto) {
        FeePayment e = feePaymentRepository.findById(id)
                .orElseThrow(
                        () -> new ApiException(ErrorCode.NOT_FOUND, "Phiếu thu phí có ID " + id + " không tồn tại."));
        Household household = e.getHousehold();
        Fee fee = e.getFee();
        if (dto.getRoomNumber() != null) {
            household = householdRepository.findByRoomNumber(dto.getRoomNumber())
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND,
                            "Hô dân có phòng số " + dto.getRoomNumber() + " không tồn tại."));
            e.setHousehold(household);
        }
        if (dto.getFeeId() != null) {
            fee = feeRepository.findById(dto.getFeeId())
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND,
                            "Loại phí có ID " + dto.getFeeId() + " không tồn tại."));
            e.setFee(fee);
            e.setName(fee.getName());
        }
        // tính usageAmount dựa trên FeeType
        BigDecimal usageAmount = null;
        FeeType feeType = fee.getType();
        if (feeType == FeeType.MANAGEMENT_FEE || feeType == FeeType.SERVICE_FEE) {
            usageAmount = BigDecimal.valueOf(household.getArea());
        } else if (feeType == FeeType.GUI_XE_MAY) {
            usageAmount = BigDecimal.valueOf(household.getBikeCount());
        } else if (feeType == FeeType.GUI_XE_O_TO) {
            usageAmount = BigDecimal.valueOf(household.getCarCount());
        } else if (dto.getUsageAmount() != null) {
            usageAmount = dto.getUsageAmount(); // loại khác thì dung usageAmount từ dto
        } else if (feeType == FeeType.OPTIONAL) {
            usageAmount = null; // loại tự nguyện không có usageAmount
        } else {
            usageAmount = e.getUsageAmount(); // nếu dto ko có usageAmount thì giữ nguyên
        }
        e.setUsageAmount(usageAmount);
        if (dto.getBillingMonth() != null) {
            e.setBillingMonth(dto.getBillingMonth());
        }
        if (e.getFee().getType() != FeeType.OPTIONAL) {
            if (e.getUsageAmount() == null) {
                throw new ApiException(ErrorCode.BAD_REQUEST,
                        "Phải có trường mức sử dụng cho loại phí không phải tự nguyện!");
            }
        }
        if (dto.getBillingYear() != null) {
            e.setBillingYear(dto.getBillingYear());
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
                else
                    e.setPaidDate(dto.getPaidDate());
            } else {
                e.setPaidDate(null);
            }
        }
        BigDecimal volutaryAmount = dto.getVoluntaryAmount();
        BigDecimal amount = calculationService.calculateFee(fee, usageAmount, volutaryAmount);
        e.setAmount(amount);
        FeePayment saved = feePaymentRepository.save(e);
        if (saved.getPaid() == true) {
            checkAllFeePaymentPaid(saved, saved.getHousehold());
        }
        return feePaymentMapper.toDTO(saved);
    }

    @Override
    public FeePaymentDTO findById(Long id) {
        FeePayment feePayment = feePaymentRepository.findById(id)
                .orElseThrow(
                        () -> new ApiException(ErrorCode.NOT_FOUND, "Phiếu thu phí có ID " + id + " không tồn tại."));
        return feePaymentMapper.toDTO(feePayment);
    }

    @Override
    public List<FeePaymentDTO> findAll() {
        return feePaymentRepository.findAll().stream()
                .map(feePaymentMapper::toDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (!feePaymentRepository.existsById(id)) {
            throw new ApiException(ErrorCode.NOT_FOUND, "Phiếu thu phí có ID " + id + " không tồn tại.");
        }
        feePaymentRepository.deleteById(id);
    }

    @Override
    public List<FeePaymentDTO> search(FeePaymentSearchRequest req) {
        Specification<FeePayment> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Lọc theo Room Number
            if (req.getRoomNumber() != null) {
                predicates.add(cb.equal(root.get("household").get("roomNumber"), req.getRoomNumber()));
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

    public void checkAllFeePaymentPaid(FeePayment payment, Household household) {
        // Kiểm tra tất cả khoản phí cùng tháng của hộ
        Integer month = payment.getBillingMonth();
        Integer year = payment.getBillingYear();
        boolean allPaid = feePaymentRepository.findByHouseholdAndBillingMonthAndBillingYear(household, month, year)
                .stream()
                .allMatch(fp -> fp.getPaid().equals(true));

        if (allPaid) {
            List<FeePayment> payments = feePaymentRepository.findByHouseholdAndBillingMonthAndBillingYear(household,
                    month, year)
                    .stream()
                    .filter(fp -> Boolean.TRUE.equals(fp.getMandatory()))
                    .toList();
            receiptService.sendReceipt(household, month, year, payments);
        }
    }

    @Override
    public List<OutstandingFeeDTO> getOutstandingFeesByHousehold() {
        return feePaymentRepository.findOutstandingFeesByHousehold();
    }
}
