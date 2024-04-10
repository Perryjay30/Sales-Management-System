package com.challengetwo.salesmanagementsystem.productmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddProductRequest {
    @NotBlank(message = "This field is required")
    private String productName;
    @NotNull(message = "This field is required")
    private Double unitPrice;
    @NotNull(message = "This field is required")
    private String category;
    @NotNull(message = "This field is required")
    private int productQuantity;
}
