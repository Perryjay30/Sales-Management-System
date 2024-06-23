package com.challengetwo.salesmanagementsystem.salesmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.CreateSalesRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.EditSales;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.response.SalesResponseDTO;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;

import java.util.List;

public interface SalesService {

    Response createSales(CreateSalesRequest createSalesRequest);
    SalesResponseDTO getSalesById(Long salesId);
    Response editQuantityAndPricesOfSale(Long salesId, EditSales editSalesRequest);
    List<SalesResponseDTO> getAllSales();
}
