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

import java.time.LocalDate;
import java.time.LocalTime;
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
            // Fetch sales data for the specified date range
        List<Sales> existingSales = salesRepository.findByCreationDateBetween(salesReportRequest.getStartDate(), salesReportRequest.getEndDate());
        if(existingSales.isEmpty()) throw new SalesManagementSystemException("No sales within these dates");
        // Calculate total number of sales
        int totalSales = existingSales.size();
        // Calculate total revenue
        double totalRevenue = existingSales.stream()
                .mapToDouble(Sales::getTotal)
                .sum();
        // Determine top-selling products
        List<Product> topSellingProducts = findTopSellingProducts(existingSales);
        // Determine top-performing sellers
        List<Seller> topPerformingSellers = findTopPerformingSellers(existingSales);
        return new SalesReport(totalSales, totalRevenue, topPerformingSellers, topSellingProducts);
    }

    private List<Product> findTopSellingProducts(List<Sales> salesList) {
        // Initialize a map to store product sales quantity or revenue
        Map<Product, Double> productSalesMap = new HashMap<>();
        // Iterate over each sale and update the sales quantity or revenue for each product
        for (Sales sale : salesList) {
            for (Transaction transaction : sale.getTransactions()) {
                Product product = productRepository.findProductById(transaction.getProductId()).orElseThrow(() -> new SalesManagementSystemException("Product not found"));
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


    private List<Seller> findTopPerformingSellers(List<Sales> salesList) {
        // Initialize a map to store seller total sales or revenue
        Map<Seller, Double> sellerSalesMap = new HashMap<>();
        // Iterate over each sale and update the total sales or revenue for each seller
        for (Sales sale : salesList) {
            Seller seller = sellerRepository.findSellerBySellerId(sale.getSellerId()).orElseThrow(() -> new SalesManagementSystemException("Seller not found"));
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
