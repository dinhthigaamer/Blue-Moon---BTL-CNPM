package com.example.project.fee.service;
//import com.example.project.fee.dto.FeeCreateDTO;
import com.example.project.fee.dto.FeeDTO;
import com.example.project.fee.entity.Fee;
import com.example.project.fee.entity.FeeType;
import com.example.project.fee.mapper.FeeMapper;
import com.example.project.fee.repository.FeeRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;

import org.springframework.stereotype.Service;
import java.util.List;


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
    public FeeDTO findById(Long id){
        Fee fee = repo.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Phí có id "+id+" không tồn tại"));
        return mapper.toDTO(fee);
    }

    @Override
    public FeeDTO create(FeeDTO dto) {
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public FeeDTO update(String type, FeeDTO dto) {
        FeeType feeType;
        try {
            feeType = FeeType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.NOT_FOUND, "Loại phí :\"" + type + "\" không hợp lệ");
        }
        Fee fee = repo.findByType(feeType)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, "Phí loại \"" + type + "\" chưa được tạo"));
        // Chỉ cập nhật nếu giá trị từ DTO khác nul
        if (dto.getName() != null) {
            fee.setName(dto.getName());
        }
        if( dto.getType() != null) {
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
        return mapper.toDTO(repo.save(fee));
    }

    @Override
    public FeeDTO update(Long id, FeeDTO dto) {
        Fee fee = repo.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND,"Phí có id "+id+" không tồn tại"));
        // Chỉ cập nhật nếu giá trị từ DTO khác null
        if(dto.getName() != null){
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
        if(dto.getNote() != null){
            fee.setNote(dto.getNote());
        }
        return mapper.toDTO(repo.save(fee));
    }


    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
//     public void delete(String type) {
//     FeeType feeType;
//     try {
//         feeType = FeeType.valueOf(type);
//     } catch (IllegalArgumentException e) {
//         throw new ApiException(ErrorCode.NOT_FOUND);
//     }

//     Fee fee = repo.findByType(feeType)
//             .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));

//     repo.delete(fee);
// }
}

