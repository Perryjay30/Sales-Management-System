package com.challengetwo.salesmanagementsystem.salesmanagement.repository;

import com.challengetwo.salesmanagementsystem.salesmanagement.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public interface SalesRepository extends JpaRepository<Sales, Long> {
    Optional<Sales> findSalesById(Long salesId);
    @Query("SELECT DISTINCT s FROM Sales s LEFT JOIN FETCH s.transactions WHERE s.creationDate BETWEEN :startDate AND :endDate")
    List<Sales> findByCreationDateBetweenFetchTransactions(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s FROM Sales s LEFT JOIN FETCH s.transactions")
    List<Sales> findAllSales();



}
