package com.challengetwo.salesmanagementsystem.salesmanagement.dto.response;

import lombok.Data;

@Data
public class TransactionResponseDTO {
    private Long id;
    private Long productId;
    private int quantity;
    private double price;
}

