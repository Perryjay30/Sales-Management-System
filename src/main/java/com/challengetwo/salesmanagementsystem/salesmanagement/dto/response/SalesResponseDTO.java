package com.challengetwo.salesmanagementsystem.salesmanagement.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SalesResponseDTO {
    private Long id;
    private LocalDate creationDate;
    private LocalDate modifiedDate;
    private Long clientId;
    private Long sellerId;
    private double total;
    private List<TransactionResponseDTO> transactions;
}
