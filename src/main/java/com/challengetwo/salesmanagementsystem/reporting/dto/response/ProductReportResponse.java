package com.challengetwo.salesmanagementsystem.reporting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductReportResponse {
    private InventoryStatus inventoryStatus;
    private SalesReport salesPerformance;
    private PricingAnalysis pricingAnalysis;
}
