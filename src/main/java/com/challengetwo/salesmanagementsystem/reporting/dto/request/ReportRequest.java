package com.challengetwo.salesmanagementsystem.reporting.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
