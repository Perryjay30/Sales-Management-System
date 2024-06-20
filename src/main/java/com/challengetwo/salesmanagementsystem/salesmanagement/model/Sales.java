package com.challengetwo.salesmanagementsystem.salesmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "seller_id")
    private Long sellerId;
    @OneToMany(mappedBy = "sales", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Transaction> transactions;

    @Transient
    private double total;

    public Sales(Long id, Long clientId, Long sellerId) {
        this.id = id;
        this.clientId = clientId;
        this.sellerId = sellerId;
    }

    public double getTotal() {
        return transactions.stream().mapToDouble(Transaction::getPrice).sum();
    }
}
