package com.challengetwo.salesmanagementsystem.reporting.service;

import com.challengetwo.salesmanagementsystem.reporting.dto.request.SalesReportRequest;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.SalesReport;

import java.time.LocalDate;

public interface ReportService {
    SalesReport generateSalesReport(SalesReportRequest salesReportRequest);
}
