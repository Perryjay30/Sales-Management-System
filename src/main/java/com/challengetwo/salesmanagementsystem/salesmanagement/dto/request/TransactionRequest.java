package com.challengetwo.salesmanagementsystem.salesmanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Long productId;
    private int quantity;
    private double price;
}
