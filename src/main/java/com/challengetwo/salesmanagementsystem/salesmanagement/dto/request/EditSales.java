package com.challengetwo.salesmanagementsystem.salesmanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EditSales {
    private Long transactionId;
    private List<TransactionRequest> transactions;
}
