package com.challengetwo.salesmanagementsystem.salesmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {
    private Long productId;
    private int quantity;
    private double price;
}
