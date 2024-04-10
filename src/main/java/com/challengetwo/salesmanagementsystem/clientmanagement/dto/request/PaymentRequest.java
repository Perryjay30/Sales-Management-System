package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private String name;
    private String description;
    private double amount;
}
