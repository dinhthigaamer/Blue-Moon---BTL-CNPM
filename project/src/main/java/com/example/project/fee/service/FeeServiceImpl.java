package com.example.project.fee.service;

import com.example.project.fee.dto.FeeCreateDTO;
import com.example.project.fee.dto.FeeDTO;
import com.example.project.fee.entity.Fee;
import com.example.project.fee.entity.FeeType;
import com.example.project.fee.mapper.FeeMapper;
import com.example.project.fee.repository.FeeRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;

import org.springframework.stereotype.Service;
import java.util.List;
import java.math.BigDecimal;

@Service

public class FeeServiceImpl implements FeeService {

    private final FeeRepository repo;
    private final FeeMapper mapper;

    public FeeServiceImpl(FeeRepository repo, FeeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<FeeDTO> findAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<FeeDTO> findByType(String type) {
        FeeType feeType;
        try {
            feeType = FeeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.NOT_FOUND, "Loại phí :\"" + type + "\" không hợp lệ");
        }
        List<FeeDTO> fees = repo.findByType(feeType).stream().map(mapper::toDTO).toList();
        return fees;
    }

    @Override
    public FeeDTO findById(Long id) {
        Fee fee = repo.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Phí có id " + id + " không tồn tại"));
        return mapper.toDTO(fee);
    }

    @Override
    public FeeDTO create(FeeCreateDTO dto) {
        Fee fee = mapper.toEntity(dto);
        checkFeeConstrained(fee);
        return mapper.toDTO(repo.save(fee));
    }

    @Override
    public FeeDTO update(Long id, FeeCreateDTO dto) {
        Fee fee = repo.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Phí có id " + id + " không tồn tại"));
        // Chỉ cập nhật nếu giá trị từ DTO khác null
        if (dto.getName() != null) {
            fee.setName(dto.getName());
        }
        if (dto.getType() != null) {
            fee.setType(dto.getType());
        }
        if (dto.getDefaultAmount() != null) {
            fee.setDefaultAmount(dto.getDefaultAmount());
        }
        if (dto.getPricePerUnit() != null) {
            fee.setPricePerUnit(dto.getPricePerUnit());
        }
        if (dto.getNote() != null) {
            fee.setNote(dto.getNote());
        }
        checkFeeConstrained(fee);
        return mapper.toDTO(repo.save(fee));
    }

    @Override
    public void delete(Long id) {
        if (repo.existsById(id) == false)
            throw new ApiException(ErrorCode.NOT_FOUND, "Loại phí có :\"" + id + "\" không tồn tại.");
        repo.deleteById(id);
    }

    public void checkFeeConstrained(Fee fee) {
        List<FeeType> requiredPricePerUnitTypes = List.of(FeeType.MANAGEMENT_FEE, FeeType.SERVICE_FEE,
                FeeType.GUI_XE_O_TO, FeeType.GUI_XE_MAY, FeeType.ELECTRICITY, FeeType.WATER);
        if (requiredPricePerUnitTypes.contains(fee.getType())) {
            if (fee.getPricePerUnit() == null) {
                throw new ApiException(ErrorCode.BAD_REQUEST,
                        "Đơn giá không được rỗng cho loại phí " + fee.getType());
            }
            if (fee.getDefaultAmount() != null) {
                throw new ApiException(ErrorCode.BAD_REQUEST,
                        "Loại phí " + fee.getType() + " không được có trường số tiền cố định");
            }
            if (fee.getPricePerUnit().compareTo(BigDecimal.ZERO) < 0) {
                throw new ApiException(ErrorCode.BAD_REQUEST,
                        "Đơn giá phải lớn hơn hoặc bằng 0 cho loại phí " + fee.getType());
            }
        }
        if (fee.getType() == FeeType.OPTIONAL && (fee.getPricePerUnit() != null
                || fee.getDefaultAmount() != null)) {
            throw new ApiException(ErrorCode.BAD_REQUEST,
                    "Loại phí " + fee.getType() +
                            " không được có đơn giá hoặc số tiền cố định");
        }
    }
    // public void delete(String type) {
    // FeeType feeType;
    // try {
    // feeType = FeeType.valueOf(type);
    // } catch (IllegalArgumentException e) {
    // throw new ApiException(ErrorCode.NOT_FOUND);
    // }

    // Fee fee = repo.findByType(feeType)
    // .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

    // repo.delete(fee);
    // }
}
