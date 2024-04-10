package com.challengetwo.salesmanagementsystem.clientmanagement.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class PaymentResponse {
    private HttpStatus statusCode;
    private String message;
//    private RecipientData data;
}
