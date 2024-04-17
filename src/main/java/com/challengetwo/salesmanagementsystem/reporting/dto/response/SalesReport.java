package com.challengetwo.salesmanagementsystem.reporting.dto.response;

import com.challengetwo.salesmanagementsystem.clientmanagement.model.Seller;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SalesReport {
    private int totalSales;
    private double totalRevenue;
    private List<Long> topPerformingSellers;
    private List<Long> topSellingProduct;

    public SalesReport(int totalSales, double totalRevenue, List<Long> topSellingProduct) {
        this.totalSales = totalSales;
        this.totalRevenue = totalRevenue;
        this.topSellingProduct = topSellingProduct;
    }
}
