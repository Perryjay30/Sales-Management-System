package com.challengetwo.salesmanagementsystem.salesmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    private Sales sales;

    @Column(name = "product_id")
    private Long productId;

    private int quantity;
    private double price;
}
