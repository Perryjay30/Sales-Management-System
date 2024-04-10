package com.challengetwo.salesmanagementsystem.productmanagement.repository;

import com.challengetwo.salesmanagementsystem.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(Long productId);
}
