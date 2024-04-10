package com.challengetwo.salesmanagementsystem.clientmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;
    private Integer itemTotal;
    private String deliveryAddress;
    private double total;
    private LocalDateTime localDateTime;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
}