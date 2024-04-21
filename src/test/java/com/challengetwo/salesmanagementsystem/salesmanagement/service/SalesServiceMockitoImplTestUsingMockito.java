package com.challengetwo.salesmanagementsystem.salesmanagement.service;
import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.CreateSalesRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.EditSales;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.TransactionRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Transaction;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.SalesRepository;
import com.challengetwo.salesmanagementsystem.salesmanagement.repository.TransactionRepository;
import com.challengetwo.salesmanagementsystem.salesmanagement.service.SalesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SalesServiceMockitoImplTestUsingMockito {
    @Mock
    private SalesRepository salesRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @InjectMocks
    private SalesServiceImpl salesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testThatASaleAndItsTransactionCanBeSaved() {
        // Mocking dependencies
        when(salesRepository.save(any())).thenReturn(new Sales());
        when(transactionRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        // Creating test data
        CreateSalesRequest createSalesRequest = new CreateSalesRequest();
        createSalesRequest.setClientId(3L);
        createSalesRequest.setSellerId(2L);
        List<TransactionRequest> transactionRequestList = new ArrayList<>();
        transactionRequestList.add(new TransactionRequest(102L, 4, 17000.00));
        createSalesRequest.setTransactions(transactionRequestList);

        // Calling the method under test
        Response response = salesService.createSales(createSalesRequest);

        // Verifying the result
        assertEquals("A sale has been saved successfully!!", response.getMessage());
    }

    @Test
    void testThatSalesCanBeRetrievedFromTheDatabase() {
        Long salesId = 7L;
        Sales theExistingSale = new Sales(7L, 2L, 3L);
        when(salesRepository.findSalesById(salesId)).thenReturn(Optional.of(theExistingSale));

        // Calling the method under test
        Sales existingSale = salesService.getSales(salesId);

        // Verifying the result
        assertNotNull(existingSale);
    }

    @Test
    void testThatQuantityAndPriceInASaleCanBeUpdated() {
        // Mocking dependencies
        Long salesId = 7L;
        Sales theExistingSale = new Sales(7L, 2L, 3L);
        when(salesRepository.findById(salesId)).thenReturn(java.util.Optional.of(theExistingSale));
        when(transactionRepository.findTransactionById(2L)).thenReturn(java.util.Optional.of(new Transaction()));

        // Calling the method under test
        EditSales editSalesRequest = new EditSales();
        editSalesRequest.setTransactionId(2L);
        List<TransactionRequest> transactionRequestList = new ArrayList<>();
        transactionRequestList.add(new TransactionRequest(152L, 3, 16700000.00));
        editSalesRequest.setTransactions(transactionRequestList);
        Response response = salesService.editQuantityAndPricesOfSale(salesId, editSalesRequest);

        // Verifying the result
        assertEquals("Quantity and price updated successfully", response.getMessage());
    }
}

