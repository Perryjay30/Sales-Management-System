package com.challengetwo.salesmanagementsystem.productmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productName;
    //    @Enumerated(EnumType.STRING)
    private ProductCategory category;
    private double unitPrice;
    private int productQuantity;
    private boolean isAvailable;

}
