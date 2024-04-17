package com.challengetwo.salesmanagementsystem.reporting.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InventoryStatus {
    private int totalProducts;
    private Long totalAvailable;
    private Long totalOutOfStock;
}
