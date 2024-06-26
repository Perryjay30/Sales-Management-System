package com.challengetwo.salesmanagementsystem.reporting.service;

import com.challengetwo.salesmanagementsystem.reporting.dto.request.ReportRequest;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.ClientReportResponse;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.ProductReportResponse;
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
        ReportRequest salesReportRequest = new ReportRequest();
        salesReportRequest.setStartDate(LocalDate.of(2024, 4, 11));
        salesReportRequest.setEndDate(LocalDate.of(2024, 4, 11));
        SalesReport salesReport = reportService.generateSalesReport(salesReportRequest);
        assertEquals(2, salesReport.getTotalSales());
        assertEquals(229000.00, salesReport.getTotalRevenue());
    }

    @Test
    void testThatClientReportCanBeRetrieved() {
        ClientReportResponse clientReportResponse = reportService.generateClientReport();
        assertEquals(4, clientReportResponse.getTotalClients());
    }

    @Test
    void testThatProductReportCanBeGenerated() {
        ReportRequest productReportRequest = new ReportRequest();
        productReportRequest.setStartDate(LocalDate.of(2024, 4,11));
        productReportRequest.setEndDate(LocalDate.of(2024, 4, 17));
        ProductReportResponse productReportResponse = reportService.generateProductReport(productReportRequest);
        assertEquals(5, productReportResponse.getInventoryStatus().getTotalProducts());
        assertEquals(4L, productReportResponse.getInventoryStatus().getTotalAvailable());
        assertEquals(1L, productReportResponse.getInventoryStatus().getTotalOutOfStock());
        assertEquals(3, productReportResponse.getSalesPerformance().getTotalSales());
        assertEquals(329000.00, productReportResponse.getSalesPerformance().getTotalRevenue());
        assertEquals(233800.00, productReportResponse.getPricingAnalysis().getAveragePrice());
        assertEquals(560000.00, productReportResponse.getPricingAnalysis().getHighestPrice());
        assertEquals(100000.00, productReportResponse.getPricingAnalysis().getLowestPrice());
    }

}