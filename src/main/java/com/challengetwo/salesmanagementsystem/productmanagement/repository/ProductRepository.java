package com.challengetwo.salesmanagementsystem.productmanagement.repository;

import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long productId);
    @Modifying
    @Query("UPDATE Product p SET p.isAvailable = CASE WHEN p.productQuantity > 0 THEN true ELSE false END")
    void updateAvailabilityBasedOnQuantity();
}
