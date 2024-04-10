package com.challengetwo.salesmanagementsystem.clientmanagement.dto.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private int productId;
    private int quantity;
}
