package com.example.project.fee.controller;
import com.example.project.common.response.ApiResponse;
import com.example.project.fee.dto.FeeDTO;
import com.example.project.fee.service.FeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class FeeController {

    private final FeeService feeService;

    @GetMapping
    public ApiResponse<List<FeeDTO>> getAllFees() {
        return ApiResponse.ok(feeService.findAll());
    }

    @GetMapping("/id/{id}")
    public ApiResponse<FeeDTO> getFeeById(@PathVariable Long id) {
        return ApiResponse.ok(feeService.findById(id));
    }

    @GetMapping("/type/{type}")
    public ApiResponse<FeeDTO> getFeeByType(@PathVariable String type) {
        return ApiResponse.ok(feeService.findByType(type));
    }

    @PostMapping
    public ApiResponse<FeeDTO> createFee(@RequestBody FeeDTO dto) {
        return ApiResponse.ok(feeService.create(dto), "Fee created successfully");
    }

    @PutMapping("/id/{id}")
    public ApiResponse<FeeDTO> updateFee(
            @PathVariable Long id,
            @RequestBody FeeDTO dto) {
        return ApiResponse.ok(feeService.update(id, dto), "Fee updated successfully");
    }

    @PutMapping("/type/{type}")
    public ApiResponse<FeeDTO> updateFeeByType(
            @PathVariable String type,
            @RequestBody FeeDTO dto) {
        return ApiResponse.ok(feeService.update(type, dto), "Fee updated successfully");
    }

    @DeleteMapping("/{type}")
    public ApiResponse<Void> deleteFee(@PathVariable String type) {
        feeService.delete(type);
        return ApiResponse.ok(null, "Fee deleted successfully");
    }
}
