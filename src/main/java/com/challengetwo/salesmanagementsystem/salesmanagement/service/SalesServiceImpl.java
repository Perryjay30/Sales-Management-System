package com.challengetwo.salesmanagementsystem.salesmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.exception.SalesManagementSystemException;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.CreateSalesRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.EditSales;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.TransactionRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.response.SalesResponseDTO;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.response.TransactionResponseDTO;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Transaction;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.SalesRepository;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Transaction> transactions = createSalesRequest.getTransactions().stream()
                .map(transactionReq -> new Transaction(null, savedSale, transactionReq.getProductId(), transactionReq.getQuantity(), transactionReq.getPrice()))
                .collect(Collectors.toList());
        savedSale.setTransactions(transactions);
        salesRepository.save(savedSale);
        return new Response("A sale has been saved successfully!!");
    }

    @Override
    public SalesResponseDTO getSalesById(Long salesId) {
        return salesRepository.getSalesById(salesId).orElseThrow(
                () -> new SalesManagementSystemException("Sales isn't available!!"));
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

    @Transactional(readOnly = true)
    @Override
    public List<SalesResponseDTO> getAllSales() {
        List<Sales> salesList = salesRepository.findAll();
        return salesList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private SalesResponseDTO convertToDTO(Sales sales) {
        SalesResponseDTO salesDTO = new SalesResponseDTO();
        salesDTO.setId(sales.getId());
        salesDTO.setCreationDate(sales.getCreationDate());
        salesDTO.setModifiedDate(sales.getModifiedDate());
        salesDTO.setClientId(sales.getClientId());
        salesDTO.setSellerId(sales.getSellerId());
        salesDTO.setTotal(sales.getTotal());

        List<TransactionResponseDTO> transactionDTOs = sales.getTransactions().stream()
                .map(transaction -> {
                    TransactionResponseDTO transactionDTO = new TransactionResponseDTO();
                    transactionDTO.setId(transaction.getId());
                    transactionDTO.setProductId(transaction.getProductId());
                    transactionDTO.setQuantity(transaction.getQuantity());
                    transactionDTO.setPrice(transaction.getPrice());
                    return transactionDTO;
                })
                .collect(Collectors.toList());

        salesDTO.setTransactions(transactionDTOs);
        return salesDTO;
    }


}
