package com.challengetwo.salesmanagementsystem.salesmanagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "seller_id")
    private Long sellerId;

    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @Transient
    private double total;

    public double getTotal() {
        return transactions.stream().mapToDouble(Transaction::getPrice).sum();
    }
}
