package com.example.project.household.controller;

import com.example.project.common.response.ApiResponse;
import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;
import com.example.project.household.service.HouseholdService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    /**
     * GET /api/households
     * Query: roomNumber, isVacant
     */
    @GetMapping
    public ApiResponse<List<HouseholdDTO>> getAll(
            @RequestParam(required = false) Integer roomNumber,
            @RequestParam(required = false) Boolean isVacant
    ) {
        return ApiResponse.ok(
                householdService.findAll(roomNumber, isVacant)
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
                householdService.create(dto)
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
                householdService.update(id, dto)
        );
    }

    /**
     * DELETE /api/households/{id}
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        householdService.delete(id);
        return ApiResponse.ok(null);
    }
}
