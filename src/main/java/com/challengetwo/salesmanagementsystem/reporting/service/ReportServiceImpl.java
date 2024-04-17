package com.challengetwo.salesmanagementsystem.reporting.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.model.User;
import com.challengetwo.salesmanagementsystem.clientmanagement.repository.SellerRepository;
import com.challengetwo.salesmanagementsystem.clientmanagement.repository.UserRepository;
import com.challengetwo.salesmanagementsystem.exception.SalesManagementSystemException;
import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import com.challengetwo.salesmanagementsystem.productmanagement.repository.ProductRepository;
import com.challengetwo.salesmanagementsystem.reporting.dto.request.ReportRequest;
import com.challengetwo.salesmanagementsystem.reporting.dto.request.UserDTO;
import com.challengetwo.salesmanagementsystem.reporting.dto.response.*;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Transaction;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.SalesRepository;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReportServiceImpl implements ReportService {
    private final SalesRepository salesRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReportServiceImpl(SalesRepository salesRepository, SellerRepository sellerRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.salesRepository = salesRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public SalesReport generateSalesReport(ReportRequest salesReportRequest) {
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

    @Override
    public ClientReportResponse generateClientReport() {
        List<User> existingUser = userRepository.findAll();
        List<Sales> sales = salesRepository.findAllSales();
        int totalClients = existingUser.size();
        List<UserDTO> topSpendingClients = calculateTopSpendingClients(sales);
        Map<String, Integer> clientActivity = calculateClientActivity(sales);
        Map<String, Integer> clientLocationStatistics = calculateClientLocationStatistics(existingUser);
        return new ClientReportResponse(totalClients, topSpendingClients, clientActivity, clientLocationStatistics);
    }

    private List<UserDTO> calculateTopSpendingClients(List<Sales> sales) {
        Map<Long, Double> clientSpendingMap = new HashMap<>();
        // Calculate total spending for each client
        for (Sales sale : sales) {
            Long clientId = sale.getClientId();
            Double totalSpending = clientSpendingMap.getOrDefault(clientId, 0.0);
            totalSpending += sale.getTotal(); // Assuming total amount for each sale
            clientSpendingMap.put(clientId, totalSpending);
        }
        // Sort clients by spending (descending order)
        List<Map.Entry<Long, Double>> sortedClientSpendingList = new ArrayList<>(clientSpendingMap.entrySet());
        sortedClientSpendingList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        // Fetch client details and create ClientDTO objects
        List<UserDTO> topSpendingClients = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Long, Double> entry : sortedClientSpendingList) {
            if (count >= 10) break; // Consider only top 10 spending clients
            Long clientId = entry.getKey();
            Double spending = entry.getValue();
            User existingUser = userRepository.findById(clientId).orElse(null); // Fetch client details
            if (existingUser != null) {
                UserDTO userDTO = mapClientToDTO(existingUser);
                userDTO.setTotalSpending(spending);
                topSpendingClients.add(userDTO);
                count++;
            }
        }

        return topSpendingClients;
    }

    private Map<String, Integer> calculateClientActivity(List<Sales> sales) {
        Map<String, Integer> clientActivity = new HashMap<>();
        for (Sales sale : sales) {
            String monthYear = sale.getCreationDate().format(DateTimeFormatter.ofPattern("MM-yyyy"));
            clientActivity.put(monthYear, clientActivity.getOrDefault(monthYear, 0) + 1);
        }

        return clientActivity;
    }

    private Map<String, Integer> calculateClientLocationStatistics(List<User> user) {
        Map<String, Integer> clientLocationStatistics = new HashMap<>();
        for (User existingUser : user) {
            String location = existingUser.getHomeAddress(); // Assuming city is used for location
            clientLocationStatistics.put(location, clientLocationStatistics.getOrDefault(location, 0) + 1);
        }
        return clientLocationStatistics;
    }

    private UserDTO mapClientToDTO(User existingUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(existingUser.getUserId());
        userDTO.setFirstName(existingUser.getFirstName());
        userDTO.setLastName(existingUser.getLastName());
        userDTO.setHomeAddress(existingUser.getHomeAddress());
        userDTO.setEmail(existingUser.getEmail());
        userDTO.setPassword(existingUser.getPassword());
        return userDTO;
    }

    @Override
    public ProductReportResponse generateProductReport(ReportRequest productReportRequest) {
        List<Product> products = productRepository.findAll();
        InventoryStatus inventoryStatus = calculateInventoryStatus(products);
        List<Sales> sales = salesRepository.findByCreationDateBetweenFetchTransactions(productReportRequest.getStartDate(), productReportRequest.getEndDate());
        SalesReport salesPerformance = calculateSalesPerformance(sales);
        PricingAnalysis pricingAnalysis = calculatePricingAnalysis(products);
        return new ProductReportResponse(inventoryStatus, salesPerformance, pricingAnalysis);
    }

    private InventoryStatus calculateInventoryStatus(List<Product> products) {
        int totalProducts = products.size();
        Long totalAvailable = products.stream().filter(Product::isAvailable).count();
//        int totalReserved = products.stream().filter(Product::isReserved).count();
        Long totalOutOfStock = products.stream().filter(Product::isOutOfStock).count();
        return new InventoryStatus(totalProducts, totalAvailable, totalOutOfStock);
    }

    private SalesReport calculateSalesPerformance(List<Sales> sales) {
        int totalSales = sales.size();
        double totalRevenue = sales.stream().mapToDouble(Sales::getTotal).sum();
        List<Long> topSellingProduct = findTopSellingProducts(sales);
        return new SalesReport(totalSales, totalRevenue, topSellingProduct);
    }

    private PricingAnalysis calculatePricingAnalysis(List<Product> products) {
        double averagePrice = products.stream().mapToDouble(Product::getUnitPrice).average().orElse(0.0);
        double lowestPrice = products.stream().mapToDouble(Product::getUnitPrice).min().orElse(0.0);
        double highestPrice = products.stream().mapToDouble(Product::getUnitPrice).max().orElse(0.0);
        return new PricingAnalysis(averagePrice, lowestPrice, highestPrice);
    }


}
