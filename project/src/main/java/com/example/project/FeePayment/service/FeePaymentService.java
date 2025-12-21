package com.example.project.FeePayment.service;

import com.example.project.FeePayment.dto.FeePaymentDTO;
import com.example.project.FeePayment.dto.FeePaymentCreateDTO;
import com.example.project.FeePayment.dto.FeePaymentSearchRequest;
import com.example.project.FeePayment.dto.FeePaymentUpdateDTO;

import java.util.List;

public interface FeePaymentService {
    FeePaymentDTO create(FeePaymentCreateDTO dto);
    FeePaymentDTO update(Long id, FeePaymentUpdateDTO dto);
    List<FeePaymentDTO> findAll();
    FeePaymentDTO findById(Long id);
    void delete(Long id);
    List<FeePaymentDTO> search(FeePaymentSearchRequest req);
}

