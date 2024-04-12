package com.challengetwo.salesmanagementsystem.reporting.dto.response;

import com.challengetwo.salesmanagementsystem.clientmanagement.model.Seller;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SalesReport {
    private int totalSales;
    private double totalRevenue;
    private List<Long> topPerformingSellers;
    private List<Long> topSellingProduct;
}
