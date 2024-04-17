package com.challengetwo.salesmanagementsystem.reporting.service;

import com.challengetwo.salesmanagementsystem.reporting.dto.request.ReportRequest;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.ClientReportResponse;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.ProductReportResponse;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.SalesReport;

public interface ReportService {
    SalesReport generateSalesReport(ReportRequest salesReportRequest);
    ClientReportResponse generateClientReport();
    ProductReportResponse generateProductReport(ReportRequest productReportRequest);
}
