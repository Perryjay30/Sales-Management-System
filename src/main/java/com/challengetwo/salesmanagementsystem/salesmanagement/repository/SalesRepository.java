package com.challengetwo.salesmanagementsystem.salesmanagement.repository;

import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long> {

    Optional<Sales> findSalesById(Long salesId);
}
