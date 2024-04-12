package com.challengetwo.salesmanagementsystem.reporting.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.model.Seller;
import com.challengetwo.salesmanagementsystem.clientmanagement.repository.SellerRepository;
import com.challengetwo.salesmanagementsystem.exception.SalesManagementSystemException;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import com.challengetwo.salesmanagementsystem.productmanagement.repository.ProductRepository;
import com.challengetwo.salesmanagementsystem.reporting.dto.request.SalesReportRequest;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.SalesReport;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Transaction;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.SalesRepository;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReportServiceImpl implements ReportService {
    private final SalesRepository salesRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    public ReportServiceImpl(SalesRepository salesRepository, SellerRepository sellerRepository, ProductRepository productRepository) {
        this.salesRepository = salesRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }


    @Override
    public SalesReport generateSalesReport(SalesReportRequest salesReportRequest) {
        List<Sales> existingSales = salesRepository.findByCreationDateBetweenFetchTransactions(salesReportRequest.getStartDate(), salesReportRequest.getEndDate());
        if(existingSales.isEmpty()) throw new SalesManagementSystemException("No sales within these dates");
        int totalSales = existingSales.size();
        double totalRevenue = existingSales.stream().mapToDouble(Sales::getTotal).sum();
        List<Long> topSellingProducts = findTopSellingProducts(existingSales);
        List<Long> topPerformingSellers = findTopPerformingSellers(existingSales);
        return new SalesReport(totalSales, totalRevenue, topPerformingSellers, topSellingProducts);
    }

    private List<Long> findTopSellingProducts(List<Sales> salesList) {
        // Initialize a map to store product sales quantity or revenue
        Map<Long, Double> productSalesMap = new HashMap<>();
        // Iterate over each sale and update the sales quantity or revenue for each product
        for (Sales sale : salesList) {
            for (Transaction transaction : sale.getTransactions()) {
                Long product = transaction.getProductId();
                double saleAmount = transaction.getQuantity() * transaction.getPrice();
                productSalesMap.put(product, productSalesMap.getOrDefault(product, 0.0) + saleAmount);
            }
        }

        // Sort the products based on sales quantity or revenue in descending order
        return productSalesMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .limit(5) // Limit to the top 5 products
                .collect(Collectors.toList());
    }


    private List<Long> findTopPerformingSellers(List<Sales> salesList) {
        // Initialize a map to store seller total sales or revenue
        Map<Long, Double> sellerSalesMap = new HashMap<>();
        // Iterate over each sale and update the total sales or revenue for each seller
        for (Sales sale : salesList) {
            Long seller = sale.getSellerId();
            double saleAmount = sale.getTotal();
            sellerSalesMap.put(seller, sellerSalesMap.getOrDefault(seller, 0.0) + saleAmount);
        }

        // Sort the sellers based on total sales or revenue in descending order
        return sellerSalesMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .limit(5) // Limit to the top 5 sellers
                .collect(Collectors.toList());
    }

}
