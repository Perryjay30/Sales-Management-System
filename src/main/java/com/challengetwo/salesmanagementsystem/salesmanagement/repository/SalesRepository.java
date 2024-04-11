package com.challengetwo.salesmanagementsystem.salesmanagement.repository;

import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SalesRepository extends JpaRepository<Sales, Long> {
    Optional<Sales> findSalesById(Long salesId);
    List<Sales> findByCreationDateBetween(LocalDate startDate, LocalDate endDate);


}
