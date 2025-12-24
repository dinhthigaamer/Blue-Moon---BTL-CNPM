package com.example.project.statistics.service;

import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueInDTO;
import com.example.project.statistics.dto.MonthlyRevenue.MonthlyRevenueOutDTO;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryOutDTO;
import com.example.project.statistics.dto.FeeSummary.FeeSummaryInDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryInDTO;
import com.example.project.statistics.dto.VoluntarySummary.VoluntarySummaryOutDTO;
public interface StatisticServer {
    FeeSummaryOutDTO statisticFeeSummary(FeeSummaryInDTO dto);
    MonthlyRevenueOutDTO statisticMonthlyRevenue(MonthlyRevenueInDTO dto);
    VoluntarySummaryOutDTO statisticVoluntary(VoluntarySummaryInDTO dto);
    Long countResidents();
}
