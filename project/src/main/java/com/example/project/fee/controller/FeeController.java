package com.example.project.fee.controller;

import com.example.project.common.response.ApiResponse;
import com.example.project.fee.dto.FeeDTO;
import com.example.project.fee.dto.FeeCreateDTO;
import com.example.project.fee.service.FeeService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @GetMapping
    public ApiResponse<List<FeeDTO>> getAllFees() {
        return ApiResponse.ok(feeService.findAll());
    }

    @GetMapping("/id/{id}")
    public ApiResponse<FeeDTO> getFeeById(@PathVariable Long id) {
        return ApiResponse.ok(feeService.findById(id));
    }

    @GetMapping("/type/{type}")
    public ApiResponse<List<FeeDTO>> getFeeByType(@PathVariable String type) {
        return ApiResponse.ok(feeService.findByType(type));
    }

    @PostMapping
    public ApiResponse<FeeDTO> createFee(@Valid @RequestBody FeeCreateDTO dto) {
        return ApiResponse.ok(feeService.create(dto), "Phí loại" + dto.getType() + "được tạo thành công");
    }

    @PutMapping("/id/{id}")
    public ApiResponse<FeeDTO> updateFee(
            @PathVariable Long id,
            @RequestBody FeeCreateDTO dto) {
        return ApiResponse.ok(feeService.update(id, dto), "Phí có id " + id + "được cập nhật thành công");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFee(@PathVariable Long id) {
        feeService.delete(id);
        return ApiResponse.ok(null, "Phí loại \"" + id + "\" được xóa thành công");
    }
}
