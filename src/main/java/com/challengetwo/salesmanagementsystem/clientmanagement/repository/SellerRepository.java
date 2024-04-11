package com.challengetwo.salesmanagementsystem.clientmanagement.repository;

import com.challengetwo.salesmanagementsystem.clientmanagement.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findSellerBySellerId(Long sellerId);
}
