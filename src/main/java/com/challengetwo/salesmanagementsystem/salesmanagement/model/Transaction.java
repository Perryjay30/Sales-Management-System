package com.challengetwo.salesmanagementsystem.salesmanagement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@RequiredArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", referencedColumnName = "id")
    @ToString.Exclude
    private Sales sales;
    @Column(name = "product_id")
    private Long productId;
    private int quantity;
    private double price;

    public Transaction(Long id, Sales sales, Long productId, int quantity, double price) {
        this.id = id;
        this.sales = sales;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

//    @Override
//    public String toString() {
//        return "Transaction{" +
//                "id=" + id +
//                ", productId=" + productId +
//                ", quantity=" + quantity +
//                ", price=" + price +
//                '}';
//    }
}
