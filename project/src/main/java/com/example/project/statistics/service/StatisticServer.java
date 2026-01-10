package com.example.project.statistics.service;

import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueInDTO;
import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueOutDTO;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryOutDTO;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryInDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryInDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryOutDTO;
import com.example.project.statistics.dto.ResidentAndHouseholdCountDTO;
public interface StatisticServer {
    FeeSummaryOutDTO statisticFeeSummary(FeeSummaryInDTO dto);
    MonthlyRevenueOutDTO statisticMonthlyRevenue(MonthlyRevenueInDTO dto);
    VoluntarySummaryOutDTO statisticVoluntary(VoluntarySummaryInDTO dto);
    ResidentAndHouseholdCountDTO countResidents();
}
