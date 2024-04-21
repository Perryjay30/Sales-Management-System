package com.challengetwo.salesmanagementsystem.salesmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.CreateSalesRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.EditSales;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.TransactionRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SalesServiceImplTest {

    @Autowired
    private SalesService salesService;

    @Test
    void testThatASaleAndItsTransactionCanBeSaved() {
        CreateSalesRequest createSalesRequest = new CreateSalesRequest();
        createSalesRequest.setClientId(3L);
        createSalesRequest.setSellerId(2L);
        List<TransactionRequest> transactionRequestList = new ArrayList<>();
        transactionRequestList.add(new TransactionRequest(102L, 4, 17000.00));
        createSalesRequest.setTransactions(transactionRequestList);
        Response response = salesService.createSales(createSalesRequest);
        assertEquals("A sale has been saved successfully!!", response.getMessage());
    }

    @Test
    void testThatSalesCanBeRetrievedFromTheDatabase() {
        Sales existingSale = salesService.getSales(8L);
        assertNotNull(existingSale);
    }

    @Test
    void testThatQuantityAndPriceInASaleCanBeUpdated() {
        EditSales editSalesRequest = new EditSales();
        editSalesRequest.setTransactionId(2L);
        List<TransactionRequest> transactionRequestList = new ArrayList<>();
        transactionRequestList.add(new TransactionRequest(152L, 3, 16700000.00));
        editSalesRequest.setTransactions(transactionRequestList);
        Response response = salesService.editQuantityAndPricesOfSale(2L, editSalesRequest);
        assertEquals("Quantity and price updated successfully", response.getMessage());
    }

}