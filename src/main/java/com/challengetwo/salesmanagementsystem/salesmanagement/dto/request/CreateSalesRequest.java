package com.challengetwo.salesmanagementsystem.salesmanagement.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateSalesRequest {
    private Long clientId;
    private Long sellerId;
    private List<TransactionRequest> transactions;
}
