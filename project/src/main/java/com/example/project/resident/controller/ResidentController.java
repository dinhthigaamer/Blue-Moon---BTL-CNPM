package com.example.project.resident.controller;

import com.example.project.common.response.ApiResponse;
import com.example.project.resident.dto.ResidentCreateDTO;
import com.example.project.resident.dto.ResidentDTO;
import com.example.project.resident.dto.ResidentUpdateDTO;
import com.example.project.resident.service.ResidentService;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) Long householdId,
            @RequestParam(required = false) Integer roomNumber
    ) {
        return ApiResponse.ok(residentService.findAll(householdId, roomNumber));
    }

    @GetMapping("/{id}")
    public ApiResponse<ResidentDTO> getById(@PathVariable Long id) {
        return ApiResponse.ok(residentService.findById(id));
    }

    @PostMapping
    public ApiResponse<ResidentDTO> create(@RequestBody ResidentCreateDTO dto) {
        return ApiResponse.ok(residentService.create(dto));
    }

    @PutMapping("/{id}")
    public ApiResponse<ResidentDTO> update(
                @PathVariable Long id,
                @RequestBody ResidentUpdateDTO dto
    ) {
        return ApiResponse.ok(residentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        residentService.delete(id);
        return ApiResponse.ok(null);
    }
}
