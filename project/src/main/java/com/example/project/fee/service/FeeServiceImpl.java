package com.example.project.fee.service;
//import com.example.project.fee.dto.FeeCreateDTO;
import com.example.project.fee.dto.FeeDTO;
import com.example.project.fee.entity.Fee;
import com.example.project.fee.mapper.FeeMapper;
import com.example.project.fee.repository.FeeRepository;
import com.example.project.common.exception.ApiException;
import com.example.project.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final FeeRepository repo;
    private final FeeMapper mapper;

    @Override
    public List<FeeDTO> findAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public FeeDTO findByType(String type) {
        Fee fee = repo.findByType(type)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
        return mapper.toDTO(fee);
    }
    @Override
    public FeeDTO findById(Long id){
        Fee fee = repo.findById(id).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
        return mapper.toDTO(fee);
    }

    @Override
    public FeeDTO create(FeeDTO dto) {
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public FeeDTO update(String type, FeeDTO dto) {
        Fee fee = repo.findByType(type)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
        fee.setName(dto.getName());
        //type không cần thiết phải cập nhật
        //fee.setType(dto.getType());
        fee.setDefaultAmount(dto.getDefaultAmount());
        fee.setPricePerUnit(dto.getPricePerUnit());
        return mapper.toDTO(repo.save(fee));
    }

    @Override
    public FeeDTO update(Long id, FeeDTO dto) {
        Fee fee = repo.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
        fee.setName(dto.getName());
        //type không cần thiết phải cập nhật
        //fee.setType(dto.getType());
        fee.setDefaultAmount(dto.getDefaultAmount());
        fee.setPricePerUnit(dto.getPricePerUnit());
        return mapper.toDTO(repo.save(fee));
    }


    @Override
    public void delete(String type) {
         Fee fee = repo.findByType(type)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
         Long id = fee.getId();
        repo.deleteById(id);
    }
}

