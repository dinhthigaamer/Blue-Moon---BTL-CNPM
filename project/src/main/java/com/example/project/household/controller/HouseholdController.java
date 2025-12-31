package com.example.project.household.controller;

import com.example.project.household.service.impl.HouseholdServiceImpl;
import com.example.project.common.response.ApiResponse;
import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdServiceImpl householdService;

    public HouseholdController(HouseholdServiceImpl householdService) {
        this.householdService = householdService;
    }

    /**
     * GET /api/households
     * Query: id, roomNumber, ownerName, residentCount, vehicleCount, isVacant
     */
    @GetMapping
    public ApiResponse<List<HouseholdDTO>> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Integer roomNumber,
            @RequestParam(required = false) String ownerName,
            @RequestParam(required = false) Integer residentCount,
            @RequestParam(required = false) Integer vehicleCount,
            @RequestParam(required = false) Boolean isVacant
    ) {
        return ApiResponse.ok(
                householdService.findAll(
                        id,
                        roomNumber,
                        ownerName,
                        residentCount,
                        vehicleCount,
                        isVacant
                )
        );
    }

    /**
     * GET /api/households/{id}
     */
    @GetMapping("/{id}")
    public ApiResponse<HouseholdDTO> getById(@PathVariable Long id) {
        return ApiResponse.ok(
                householdService.findById(id)
        );
    }

    /**
     * POST /api/households
     */
    @PostMapping
    public ApiResponse<HouseholdDTO> create(
            @RequestBody HouseholdCreateDTO dto
    ) {
        return ApiResponse.ok(
                householdService.create(dto), "Hộ dân được tạo thành công"
        );
    }

    /**
     * PUT /api/households/{id}
     */
    @PutMapping("/{id}")
    public ApiResponse<HouseholdDTO> update(
            @PathVariable Long id,
            @RequestBody HouseholdUpdateDTO dto
    ) {
        return ApiResponse.ok(
                householdService.update(id, dto), "Hộ dân được cập nhật thành công"
        );
    }

    /**
     * DELETE /api/households/{id}
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        householdService.delete(id);
        return ApiResponse.ok(null, "Hộ dân được xoá thành công");
    }
}
