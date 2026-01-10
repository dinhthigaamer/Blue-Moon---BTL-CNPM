package com.example.project.household.controller;

import com.example.project.household.service.impl.HouseholdServiceImpl;
import com.example.project.common.response.ApiResponse;
import com.example.project.household.dto.HouseholdCreateDTO;
import com.example.project.household.dto.HouseholdDTO;
import com.example.project.household.dto.HouseholdUpdateDTO;
import io.swagger.v3.oas.annotations.Parameter;
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
     * Query: id, roomNumber, ownerName, area, residentCount, carCount, bikeCount, isVacant
     */
    @GetMapping
    public ApiResponse<List<HouseholdDTO>> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String roomNumber,
            @RequestParam(required = false) String ownerName,
            @Parameter(description = "Diện tích căn hộ (m2)")
            @RequestParam(required = false) Double area,
            @RequestParam(required = false) Integer residentCount,
            @RequestParam(required = false) Integer carCount,
            @RequestParam(required = false) Integer bikeCount,
            @RequestParam(required = false) Boolean isVacant
    ) {
        return ApiResponse.ok(
                householdService.findAll(
                        id,
                        roomNumber,
                        ownerName,
                        area,
                        residentCount,
                        carCount,
                        bikeCount,
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
     * GET /api/households/by-room/{roomNumber}
     */
    @GetMapping("/by-room/{roomNumber}")
    public ApiResponse<HouseholdDTO> getByRoomNumber(@PathVariable String roomNumber) {
        return ApiResponse.ok(
                householdService.findByRoomNumber(roomNumber)
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
     * PUT /api/households/by-room/{roomNumber}
     */
    @PutMapping("/by-room/{roomNumber}")
    public ApiResponse<HouseholdDTO> updateByRoomNumber(
            @PathVariable String roomNumber,
            @RequestBody HouseholdUpdateDTO dto
    ) {
        return ApiResponse.ok(
                householdService.updateByRoomNumber(roomNumber, dto), "Hộ dân được cập nhật thành công"
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

    /**
     * DELETE /api/households/by-room/{roomNumber}
     */
    @DeleteMapping("/by-room/{roomNumber}")
    public ApiResponse<Void> deleteByRoomNumber(@PathVariable String roomNumber) {
        householdService.deleteByRoomNumber(roomNumber);
        return ApiResponse.ok(null, "Hộ dân được xoá thành công");
    }
}
