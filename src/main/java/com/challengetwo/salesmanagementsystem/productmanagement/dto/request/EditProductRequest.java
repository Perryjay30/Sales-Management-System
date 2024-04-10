package com.challengetwo.salesmanagementsystem.productmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditProductRequest {
    private String productName;
    private Double unitPrice;
    private String category;
    private int productQuantity;
}
