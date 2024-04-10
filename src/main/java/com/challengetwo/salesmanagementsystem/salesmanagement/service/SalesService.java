package com.challengetwo.salesmanagementsystem.salesmanagement.service;

import com.challengetwo.salesmanagementsystem.clientmanagement.dto.response.Response;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.CreateSalesRequest;
import com.challengetwo.salesmanagementsystem.salesmanagement.dto.request.EditSales;
import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;

public interface SalesService {

    Response createSales(CreateSalesRequest createSalesRequest);
    Sales getSales(Long salesId);
    Response editQuantityAndPricesOfSale(Long salesId, EditSales editSalesRequest);
}
