package com.challengetwo.salesmanagementsystem.salesmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.exception.SalesManagementSystemException;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.CreateSalesRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.EditSales;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.TransactionRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Transaction;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.SalesRepository;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;
    private final TransactionRepository transactionRepository;

    public SalesServiceImpl(SalesRepository salesRepository, TransactionRepository transactionRepository) {
        this.salesRepository = salesRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Response createSales(CreateSalesRequest createSalesRequest) {
        Sales savedSale = new Sales();
        savedSale.setClientId(createSalesRequest.getClientId());
        savedSale.setSellerId(createSalesRequest.getSellerId());
        savedSale.setCreationDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        salesRepository.save(savedSale);
        List<Transaction> transactions = getTransactionsForASale(createSalesRequest, savedSale);
        savedSale.setTransactions(transactions);
        salesRepository.save(savedSale);
        return new Response("A sale has been saved successfully!!");
    }

    private List<Transaction> getTransactionsForASale(CreateSalesRequest createSalesRequest, Sales savedSale) {
        List<TransactionRequest> transactionRequests = createSalesRequest.getTransactions();
        List<Transaction> transactions = new ArrayList<>();
        for (TransactionRequest transactionReq : transactionRequests) {
            Transaction transaction = new Transaction();
            transaction.setProductId(transactionReq.getProductId());
            transaction.setQuantity(transactionReq.getQuantity());
            transaction.setPrice(transactionReq.getPrice());
            transactions.add(transaction);
        }
        transactionRepository.saveAll(transactions);
        return transactions;
    }

    @Override
    public Sales getSales(Long salesId) {
        return salesRepository.findSalesById(salesId)
                .orElseThrow(() -> new SalesManagementSystemException("Sales not found"));
    }

    @Override
    public Response editQuantityAndPricesOfSale(Long salesId, EditSales editSalesRequest) {
        Sales existingSale = salesRepository.findSalesById(salesId).orElseThrow();
        Transaction existingTransaction = transactionRepository.findTransactionById(editSalesRequest.getTransactionId())
                .orElseThrow(() -> new SalesManagementSystemException("Transaction is not available!!"));
        existingSale.setModifiedDate(LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        List<TransactionRequest> transactionRequests = editSalesRequest.getTransactions();
        List<Transaction> transactions = new ArrayList<>();
        for(TransactionRequest transReq : transactionRequests) {
            existingTransaction.setProductId(transReq.getProductId() != 0 ? transReq.getProductId() : existingTransaction.getProductId());
            existingTransaction.setPrice(transReq.getPrice() != 0.00 ? transReq.getPrice() : existingTransaction.getPrice());
            existingTransaction.setQuantity(transReq.getQuantity() != 0 ? transReq.getQuantity() : existingTransaction.getQuantity());
            transactions.add(existingTransaction);
        }
        transactionRepository.saveAll(transactions);
        existingSale.setTransactions(transactions);
        salesRepository.save(existingSale);
        return new Response("Quantity and price updated successfully");
    }

    @Override
    public List<Sales> getAllSales() {
        return salesRepository.findAllSales();
    }


}
