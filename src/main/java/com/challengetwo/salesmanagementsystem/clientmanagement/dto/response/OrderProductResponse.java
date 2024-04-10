package com.challengetwo.salesmanagementsystem.clientmanagement.dto.response;

import lombok.Data;

@Data
public class OrderProductResponse {
    private int id;
    private int statusCode;
    private String message;
}
