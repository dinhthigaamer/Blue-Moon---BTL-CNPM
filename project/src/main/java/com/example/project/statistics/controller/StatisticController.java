package com.example.project.statistics.controller;

import com.example.project.statistics.service.StatisticServiceImpl;
import com.example.project.common.response.ApiResponse;
import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueInDTO;
import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueOutDTO;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryInDTO;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryOutDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryInDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryOutDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;  

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/statistics")

public class StatisticController {

    private final StatisticServiceImpl statisticService;

    public StatisticController(StatisticServiceImpl statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/monthly-revenue")
    public ApiResponse<MonthlyRevenueOutDTO> getMonthlyRevenue(
            @Valid @RequestBody MonthlyRevenueInDTO dto) {

        return ApiResponse.ok(
                statisticService.statisticMonthlyRevenue(dto));
    }

    @GetMapping("/residents")
    public ApiResponse<Long> countResidents() {
        return ApiResponse.ok(statisticService.countResidents());
    }

    @GetMapping("/fee-summary")
    public ApiResponse<FeeSummaryOutDTO> getFeeSummary(
            @Valid @RequestBody FeeSummaryInDTO dto) {

        return ApiResponse.ok(
                statisticService.statisticFeeSummary(dto)
        );
    }
    @GetMapping("/voluntary-summary")
    public ApiResponse<VoluntarySummaryOutDTO> getVoluntarySummary(
            @Valid @RequestBody VoluntarySummaryInDTO dto) {

        return ApiResponse.ok(
                statisticService.statisticVoluntary(dto)
        );
    }
}

