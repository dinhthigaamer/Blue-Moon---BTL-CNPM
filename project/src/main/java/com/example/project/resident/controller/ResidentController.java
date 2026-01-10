package com.example.project.resident.controller;

import com.example.project.common.response.ApiResponse;
import com.example.project.resident.dto.ResidentCreateDTO;
import com.example.project.resident.dto.ResidentDTO;
import com.example.project.resident.dto.ResidentUpdateDTO;
import com.example.project.resident.service.ResidentService;
import com.example.project.resident.entity.Gender;
import com.example.project.resident.entity.ResidenceStatus;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @GetMapping
    public ApiResponse<List<ResidentDTO>> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long householdId,
            @RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String cccd,
            @RequestParam(required = false) LocalDate dateOfBirth,
            @RequestParam(required = false) String religion,
            @RequestParam(required = false) String ethnicity,
            @RequestParam(required = false) String occupation,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) ResidenceStatus residenceStatus,
            @RequestParam(required = false) Integer carCount,
            @RequestParam(required = false) Integer bikeCount
    ) {
        Gender parsedGender = gender != null ? Gender.fromValue(gender) : null;
        return ApiResponse.ok(residentService.findAll(
                id,
                householdId,
                roomNumber,
                fullName,
                phone,
                email,
                cccd,
                dateOfBirth,
                religion,
                ethnicity,
                occupation,
                parsedGender,
                residenceStatus,
                carCount,
                bikeCount
        ));
    }

    @GetMapping("/{id}")
    public ApiResponse<ResidentDTO> getById(@PathVariable Long id) {
        return ApiResponse.ok(residentService.findById(id));
    }

    @PostMapping
    public ApiResponse<ResidentDTO> create(@Valid @RequestBody ResidentCreateDTO dto) {
        return ApiResponse.ok(residentService.create(dto), "Cư dân được tạo thành công.");
    }

    @PutMapping("/{id}")
    public ApiResponse<ResidentDTO> update(
                @PathVariable Long id,
                @Valid @RequestBody ResidentUpdateDTO dto
    ) {
        return ApiResponse.ok(residentService.update(id, dto), "Cư dân được cập nhật thành công.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        residentService.delete(id);
        return ApiResponse.ok(null, "Cư dân được xóa thành công.");
    }
}
