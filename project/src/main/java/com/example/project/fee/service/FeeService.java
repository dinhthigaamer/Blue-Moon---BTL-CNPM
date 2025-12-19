package com.example.project.fee.service;
//import com.example.project.fee.dto.FeeCreateDTO;
import com.example.project.fee.dto.FeeDTO;  
import java.util.List;

public interface FeeService {
    List<FeeDTO> findAll();
    //tìm theo id khá vô nghĩa, phải tìm theo type
    FeeDTO findById(Long id);
    FeeDTO findByType(String type);
    FeeDTO create(FeeDTO dto);
    FeeDTO update(String type, FeeDTO dto);
    FeeDTO update(Long id, FeeDTO dto);
    void delete(String type);
}
