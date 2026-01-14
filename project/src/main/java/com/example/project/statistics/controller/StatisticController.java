package com.example.project.statistics.controller;

import com.example.project.statistics.service.StatisticServiceImpl;
import com.example.project.common.response.ApiResponse;
import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueOutDTO;

import com.example.project.statistics.dto.FeeSummary.FeeSummaryOutDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryOutDTO;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.project.statistics.dto.ResidentAndHouseholdCountDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@Validated
@RequestMapping("/api/statistics")

public class StatisticController {

    private final StatisticServiceImpl statisticService;

    public StatisticController(StatisticServiceImpl statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/monthly-revenue")
    public ApiResponse<MonthlyRevenueOutDTO> getMonthlyRevenue(
            @RequestParam(required = false) @PositiveOrZero(message = "Năm thu phải >= 0") Integer year,
            @RequestParam(required = false) @Min(value = 1, message = "Tháng thu phải từ 1 đến 12") @Max(value = 12, message = "Tháng thu phải từ 1 đến 12") Integer month) {

        return ApiResponse.ok(
                statisticService.statisticMonthlyRevenue(year, month));
    }

    @GetMapping("/residents")
    public ApiResponse<ResidentAndHouseholdCountDTO> countResidents() {
        return ApiResponse.ok(statisticService.countResidents());
    }

    @GetMapping("/fee-summary")
    public ApiResponse<FeeSummaryOutDTO> getFeeSummary(
            @RequestParam(required = false) @PositiveOrZero(message = "Năm thu phải >= 0") Integer year,
            @RequestParam(required = false) @Min(value = 1, message = "Tháng thu phải từ 1 đến 12") @Max(value = 12, message = "Tháng thu phải từ 1 đến 12") Integer month,
            @RequestParam(required = false) Long feeId) {

        return ApiResponse.ok(
                statisticService.statisticFeeSummary(feeId, year, month));
    }

    @GetMapping("/voluntary-summary")
    public ApiResponse<VoluntarySummaryOutDTO> getVoluntarySummary(
            @RequestParam(required = false) @PositiveOrZero(message = "Năm thu phải >= 0") Integer year,
            @RequestParam(required = false) @Min(value = 1, message = "Tháng thu phải từ 1 đến 12") @Max(value = 12, message = "Tháng thu phải từ 1 đến 12") Integer month) {

        return ApiResponse.ok(
                statisticService.statisticVoluntary(year, month));
    }
}
