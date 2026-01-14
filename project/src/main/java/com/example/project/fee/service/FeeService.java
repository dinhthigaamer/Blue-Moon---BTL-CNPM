package com.example.project.fee.service;

import com.example.project.fee.dto.FeeCreateDTO;
import com.example.project.fee.dto.FeeDTO;
import java.util.List;

public interface FeeService {
    List<FeeDTO> findAll();

    // tìm theo id khá vô nghĩa, phải tìm theo type
    FeeDTO findById(Long id);

    // theo suy luận hiện tại thì mỗi type chỉ có một khoản phí nên tìm theo type là
    // hợp lý
    // nhưng sẽ phải đặt trường type là unique trong db
    List<FeeDTO> findByType(String type);

    FeeDTO create(FeeCreateDTO dto);

    FeeDTO update(Long id, FeeCreateDTO dto);

    void delete(Long id);
}
