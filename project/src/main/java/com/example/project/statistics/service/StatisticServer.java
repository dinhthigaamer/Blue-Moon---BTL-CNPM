package com.example.project.statistics.service;

import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueOutDTO;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryOutDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryOutDTO;
import com.example.project.statistics.dto.ResidentAndHouseholdCountDTO;

public interface StatisticServer {
    FeeSummaryOutDTO statisticFeeSummary(Long feeId, Integer year, Integer month);

    MonthlyRevenueOutDTO statisticMonthlyRevenue(Integer year, Integer month);

    VoluntarySummaryOutDTO statisticVoluntary(Integer year, Integer month);

    ResidentAndHouseholdCountDTO countResidents();
}
