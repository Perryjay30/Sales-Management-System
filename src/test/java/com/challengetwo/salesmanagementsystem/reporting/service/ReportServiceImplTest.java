package com.challengetwo.salesmanagementsystem.reporting.service;

import com.challengetwo.salesmanagementsystem.reporting.dto.request.SalesReportRequest;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.SalesReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportServiceImplTest {

    @Autowired
    private ReportService reportService;

    @Test
    void testThatSalesReportCanBeRetrieved() {
        SalesReportRequest salesReportRequest = new SalesReportRequest();
        salesReportRequest.setStartDate(LocalDate.of(2024, 4, 11));
        salesReportRequest.setEndDate(LocalDate.of(2024, 4, 11));
        SalesReport salesReport = reportService.generateSalesReport(salesReportRequest);
        assertEquals(1, salesReport.getTotalSales());
    }

}