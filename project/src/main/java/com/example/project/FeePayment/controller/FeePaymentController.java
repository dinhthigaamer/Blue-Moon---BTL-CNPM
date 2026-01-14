package com.example.project.FeePayment.controller;

import com.example.project.FeePayment.dto.FeePaymentUpdateDTO;
import com.example.project.FeePayment.dto.FeePaymentCreateDTO;
import com.example.project.FeePayment.dto.FeePaymentDTO;
import com.example.project.FeePayment.dto.FeePaymentSearchRequest;
import com.example.project.common.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import com.example.project.FeePayment.dto.OutstandingFeeDTO;
import com.example.project.FeePayment.service.FeePaymentService;

@RestController
@RequestMapping("/api/fee-payments")
public class FeePaymentController {
    private final FeePaymentService feePaymentService;

    public FeePaymentController(FeePaymentService feePaymentService) {
        this.feePaymentService = feePaymentService;
    }

    @PostMapping
    public ApiResponse<FeePaymentDTO> createFeePayment(
            @Valid @RequestBody FeePaymentCreateDTO dto) {
        FeePaymentDTO result = feePaymentService.create(dto);
        return ApiResponse.ok(result, "Tạo mới phiếu thu phí thành công");
    }

    @PutMapping("/{id}")
    public ApiResponse<FeePaymentDTO> updateFeePayment(
            @PathVariable Long id,
            @Valid @RequestBody FeePaymentUpdateDTO dto) {
        return ApiResponse.ok(
                feePaymentService.update(id, dto), "Cập nhật phiếu thu phí thành công");
    }

    @GetMapping("/")
    public ApiResponse<List<FeePaymentDTO>> getAll() {
        return ApiResponse.ok(
                feePaymentService.findAll());
    }

    @GetMapping("/search")
    public ApiResponse<List<FeePaymentDTO>> searchFeePayments(
            @RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) Long feeId,
            @RequestParam(required = false) Integer billingYear,
            @RequestParam(required = false) Integer billingMonth,
            @RequestParam(required = false) LocalDate startFrom,
            @RequestParam(required = false) LocalDate startTo,
            @RequestParam(required = false) LocalDate dueFrom,
            @RequestParam(required = false) LocalDate dueTo,
            @RequestParam(required = false) Boolean mandatory,
            @RequestParam(required = false) Boolean paid) {
        FeePaymentSearchRequest req = new FeePaymentSearchRequest();
        req.setRoomNumber(roomNumber);
        req.setFeeId(feeId);
        req.setBillingYear(billingYear);
        req.setBillingMonth(billingMonth);
        req.setStartFrom(startFrom);
        req.setStartTo(startTo);
        req.setDueFrom(dueFrom);
        req.setDueTo(dueTo);
        req.setMandatory(mandatory);
        req.setPaid(paid);
        List<FeePaymentDTO> results = feePaymentService.search(req);
        return ApiResponse.ok(results);
    }

    @GetMapping("/search/{id}")
    public ApiResponse<FeePaymentDTO> getById(@PathVariable Long id) {
        return ApiResponse.ok(
                feePaymentService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFeePayment(@PathVariable Long id) {
        feePaymentService.delete(id);
        return ApiResponse.ok(null, "Phiếu thu phí đã được xoá thành công");
    }

    @GetMapping("/outstanding")
    public ApiResponse<List<OutstandingFeeDTO>> getOutstandingFeesByHousehold() {
        List<OutstandingFeeDTO> result = feePaymentService.getOutstandingFeesByHousehold();
        return ApiResponse.ok(result);
    }
}
